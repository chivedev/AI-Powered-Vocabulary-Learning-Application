package com.example.dictionary_inz.ui.learn.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary_inz.R
import com.example.dictionary_inz.data.local.WordEntity
import com.example.dictionary_inz.data.repository.WordRepository
import com.example.dictionary_inz.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val quizQuestion: QuizQuestion? = null,
    val userInput: String = "",
    val feedback: UiText? = null,
    val isAnswerCorrect: Boolean? = null,
    val sessionQuestions: List<QuizQuestion> = emptyList(),
    val incorrectQuestions: List<QuizQuestion> = emptyList(),
    val initialSessionSize: Int = 0,
    val correctAnswers: Int = 0,
    val sessionFinished: Boolean = false,
    val showCorrectAnswer: Boolean = false,
    val selectedDifficulty: QuizDifficulty? = null
)

class QuizViewModel(
    private val wordRepository: WordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState

    fun selectDifficulty(difficulty: QuizDifficulty) {
        _uiState.update { currentState ->
            currentState.copy(selectedDifficulty = difficulty)
        }
        startQuiz(difficulty)
    }

    private fun startQuiz(difficulty: QuizDifficulty) {
        viewModelScope.launch {
            val words = wordRepository.getAllWords().firstOrNull().orEmpty()
            val questions = words.map { generateQuizQuestion(it, difficulty) }
                .shuffled()
                .toMutableList()

            _uiState.update { currentState ->
                currentState.copy(
                    sessionQuestions = questions,
                    initialSessionSize = questions.size,
                    incorrectQuestions = emptyList(),
                    sessionFinished = false,
                    correctAnswers = 0,
                    quizQuestion = questions.firstOrNull(),
                    userInput = "",
                    feedback = null,
                    isAnswerCorrect = null,
                    showCorrectAnswer = false
                )
            }
        }
    }

    private fun generateQuizQuestion(
        wordEntity: WordEntity,
        difficulty: QuizDifficulty
    ): QuizQuestion {
        val originalSentence = wordEntity.englishSentence
        val words = originalSentence.split(" ")
        return when (difficulty) {
            QuizDifficulty.HARD -> QuizQuestion(
                wordEntity = wordEntity,
                difficulty = difficulty,
                originalWords = words,
                missingIndex = null,
                displayedSentence = originalSentence
            )
            QuizDifficulty.EASY -> {
                val eligibleIndices = words.indices.filter { index ->
                    words[index].filter { it.isLetter() }.length >= 4
                }
                val missingIndex = if (eligibleIndices.isNotEmpty()) eligibleIndices.random() else null
                val displayedWords = words.mapIndexed { index, word ->
                    if (index == missingIndex) "____" else word
                }
                QuizQuestion(
                    wordEntity = wordEntity,
                    difficulty = difficulty,
                    originalWords = words,
                    missingIndex = missingIndex,
                    displayedSentence = displayedWords.joinToString(" ")
                )
            }
        }
    }

    fun startIncorrectSession() {
        viewModelScope.launch {
            val incorrectList = uiState.value.incorrectQuestions.toMutableList()
            if (incorrectList.isNotEmpty()) {
                _uiState.update { currentState ->
                    currentState.copy(
                        sessionQuestions = incorrectList,
                        initialSessionSize = incorrectList.size,
                        incorrectQuestions = emptyList(),
                        sessionFinished = false,
                        correctAnswers = 0,
                        quizQuestion = incorrectList.firstOrNull(),
                        userInput = "",
                        feedback = null,
                        isAnswerCorrect = null,
                        showCorrectAnswer = false
                    )
                }
            }
        }
    }

    fun updateUserInput(input: String) {
        _uiState.update { currentState ->
            currentState.copy(userInput = input)
        }
    }

    private fun normalize(text: String): String {
        return text.lowercase().replace(Regex("[,.!?-]"), "").trim()
    }

    fun checkAnswer() {
        val state = uiState.value
        val question = state.quizQuestion ?: return
        val answer = state.userInput.trim()

        if (answer.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    feedback = UiText.StringResource(R.string.feedback_empty_answer),
                    isAnswerCorrect = false
                )
            }
            return
        }

        val isCorrect = when (question.difficulty) {
            QuizDifficulty.HARD -> normalize(answer) == normalize(question.wordEntity.englishSentence)
            QuizDifficulty.EASY -> {
                question.missingIndex != null && normalize(answer) == normalize(question.originalWords[question.missingIndex])
            }
        }

        if (isCorrect) {
            if (question.difficulty == QuizDifficulty.EASY) {
                val updatedSentence = question.originalWords.joinToString(" ")
                _uiState.update { currentState ->
                    currentState.copy(
                        quizQuestion = currentState.quizQuestion?.copy(displayedSentence = updatedSentence),
                        feedback = UiText.StringResource(R.string.feedback_correct),
                        isAnswerCorrect = true
                    )
                }
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        feedback = UiText.StringResource(R.string.feedback_correct),
                        isAnswerCorrect = true
                    )
                }
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    feedback = UiText.StringResource(R.string.feedback_incorrect),
                    isAnswerCorrect = false
                )
            }
        }
    }

    fun repeatQuestion() {
        val state = uiState.value
        val questions = state.sessionQuestions.toMutableList()
        if (questions.isNotEmpty()) {
            val question = questions.removeAt(0)
            val incorrect = state.incorrectQuestions.toMutableList()
            incorrect.add(question)
            _uiState.update { currentState ->
                currentState.copy(
                    sessionQuestions = questions,
                    incorrectQuestions = incorrect,
                    userInput = "",
                    feedback = UiText.StringResource(R.string.feedback_question_repeated),
                    isAnswerCorrect = null
                )
            }
        }
        loadNextQuestion()
    }

    fun markQuestionAsMastered() {
        val state = uiState.value
        val questions = state.sessionQuestions.toMutableList()
        if (questions.isNotEmpty()) {
            questions.removeAt(0)
            _uiState.update { currentState ->
                currentState.copy(
                    correctAnswers = currentState.correctAnswers + 1,
                    sessionQuestions = questions,
                    feedback = UiText.StringResource(R.string.feedback_question_mastered),
                    isAnswerCorrect = null
                )
            }
        }
        loadNextQuestion()
    }

    fun tryAgain() {
        _uiState.update { currentState ->
            currentState.copy(
                userInput = "",
                feedback = null,
                isAnswerCorrect = null
            )
        }
    }

    fun skipQuestion() {
        val state = uiState.value
        val questions = state.sessionQuestions.toMutableList()
        if (questions.isEmpty()) return
        val question = questions.removeAt(0)
        val incorrect = state.incorrectQuestions.toMutableList()
        if (!question.isMastered) {
            incorrect.add(question)
        }
        _uiState.update { currentState ->
            currentState.copy(
                sessionQuestions = questions,
                incorrectQuestions = incorrect,
                showCorrectAnswer = true,
                isAnswerCorrect = false,
                feedback = UiText.StringResource(R.string.feedback_empty)
            )
        }
    }

    fun confirmSkip() {
        _uiState.update { currentState ->
            currentState.copy(showCorrectAnswer = false)
        }
        loadNextQuestion()
    }

    private fun loadNextQuestion(noQuestionReset: Boolean = false) {
        val state = uiState.value
        if (state.sessionQuestions.isNotEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    quizQuestion = currentState.sessionQuestions.firstOrNull(),
                    userInput = if (noQuestionReset) currentState.userInput else "",
                    feedback = null,
                    isAnswerCorrect = null
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    quizQuestion = null,
                    sessionFinished = true
                )
            }
        }
    }
}