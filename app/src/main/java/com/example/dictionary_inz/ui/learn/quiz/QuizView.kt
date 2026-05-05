package com.example.dictionary_inz.ui.learn.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dictionary_inz.R
import com.example.dictionary_inz.ui.AppViewModelProvider
import com.example.dictionary_inz.ui.home.components.DictionaryTopAppBar
import com.example.dictionary_inz.ui.learn.quiz.components.QuizActionRowDefault
import com.example.dictionary_inz.ui.learn.quiz.components.QuizActionRowResult
import com.example.dictionary_inz.ui.learn.quiz.components.QuizAnswerDialog
import com.example.dictionary_inz.ui.learn.quiz.components.QuizModeSelectionScreen
import com.example.dictionary_inz.ui.learn.quiz.components.QuizQuestionCard
import com.example.dictionary_inz.ui.learn.quiz.components.SummaryScreen

@Composable
fun QuizView(
    onBackClick: () -> Unit,
    onReturnHome: () -> Unit
) {
    val viewModel: QuizViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val textFieldFocusRequester = remember { FocusRequester() }

    if (uiState.sessionFinished) {
        SummaryScreen(
            correctAnswers = uiState.correctAnswers,
            totalQuestions = uiState.initialSessionSize,
            onRepeatIncorrect = {
                viewModel.startIncorrectSession()
                focusManager.clearFocus()
            },
            onFinish = onReturnHome
        )
        return
    }

    if (uiState.selectedDifficulty == null) {
        QuizModeSelectionScreen(onSelectMode = { difficulty ->
            viewModel.selectDifficulty(difficulty)
            focusManager.clearFocus()
        })
        return
    }

    uiState.quizQuestion?.let { question ->
        if (uiState.showCorrectAnswer) {
            QuizAnswerDialog(
                quizQuestion = question,
                userAnswer = uiState.userInput,
                onConfirm = {
                    viewModel.confirmSkip()
                    focusManager.clearFocus()
                }
            )
        }
    }

    Scaffold(
        topBar = {
            DictionaryTopAppBar(
                title = stringResource(R.string.quiz_title),
                showBackButton = true,
                onBackClick = {
                    onBackClick()
                    focusManager.clearFocus()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onReturnHome()
                focusManager.clearFocus()
            }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.quiz_return_home)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    R.string.quiz_remaining_questions,
                    uiState.sessionQuestions.size
                ),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
            uiState.quizQuestion?.let { question ->
                QuizQuestionCard(
                    quizQuestion = question,
                    isAnswerCorrect = uiState.isAnswerCorrect
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = uiState.userInput,
                onValueChange = { viewModel.updateUserInput(it) },
                placeholder = { Text(stringResource(R.string.quiz_input_placeholder)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(textFieldFocusRequester)
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (uiState.isAnswerCorrect == null) {
                QuizActionRowDefault(
                    onCheckAnswer = {
                        viewModel.checkAnswer()
                        focusManager.clearFocus()
                    },
                    onSkipQuestion = {
                        viewModel.skipQuestion()
                        focusManager.clearFocus()
                    }
                )
            } else {
                when (uiState.isAnswerCorrect) {
                    true -> {
                        QuizActionRowResult(
                            isAnswerCorrect = true,
                            onRepeat = {
                                viewModel.repeatQuestion()
                                focusManager.clearFocus()
                            },
                            onProceed = {
                                viewModel.markQuestionAsMastered()
                                focusManager.clearFocus()
                            }
                        )
                    }
                    false -> {
                        QuizActionRowResult(
                            isAnswerCorrect = false,
                            onRepeat = {
                                viewModel.tryAgain()
                                textFieldFocusRequester.requestFocus()
                            },
                            onProceed = {
                                viewModel.skipQuestion()
                                focusManager.clearFocus()
                            }
                        )
                    }
                    else -> { /* brak akcji */ }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            uiState.feedback?.let {
                Text(
                    text = it.asString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}