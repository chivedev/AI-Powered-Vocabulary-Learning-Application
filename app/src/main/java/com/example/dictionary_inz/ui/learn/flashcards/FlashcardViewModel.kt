package com.example.dictionary_inz.ui.learn.flashcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary_inz.data.local.WordEntity
import com.example.dictionary_inz.data.repository.WordRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FlashcardUiState(
    val currentWord: WordEntity? = null,
    val isFlipped: Boolean = false,
    val isDatabaseEmpty: Boolean = false
)

class FlashcardViewModel(
    private val wordRepository: WordRepository
) : ViewModel() {

    private val _wordList = mutableListOf<WordEntity>()

    private val _uiState = MutableStateFlow(FlashcardUiState())
    val uiState: StateFlow<FlashcardUiState> = _uiState

    init {
        loadWordsFromRepository()
    }

    private fun loadWordsFromRepository() {
        viewModelScope.launch {
            val allWords = wordRepository.getAllWords().firstOrNull().orEmpty()
            _wordList.clear()
            _wordList.addAll(allWords)
            _uiState.update { currentState ->
                currentState.copy(isDatabaseEmpty = allWords.isEmpty())
            }
            loadNextWord()
        }
    }

    private fun loadNextWord() {
        _uiState.update { currentState ->
            currentState.copy(currentWord = _wordList.firstOrNull(), isFlipped = false)
        }
    }
    fun onUserKnowsWord() {
        viewModelScope.launch {
            if (uiState.value.isFlipped) {
                flipCard()
                delay(400L)
            }
            _uiState.value.currentWord?.let { knownWord ->
                _wordList.remove(knownWord)
            }
            _uiState.update { currentState ->
                currentState.copy(currentWord = _wordList.firstOrNull(), isFlipped = false)
            }
        }
    }

    fun onUserWantsRepeat() {
        viewModelScope.launch {
            if (uiState.value.isFlipped) {
                flipCard()
                delay(400L)
            }
            uiState.value.currentWord?.let { word ->
                _wordList.remove(word)
                _wordList.add(word)
            }
            _uiState.update { currentState ->
                currentState.copy(currentWord = _wordList.firstOrNull(), isFlipped = false)
            }
        }
    }


    fun flipCard() {
        _uiState.update { currentState ->
            currentState.copy(isFlipped = !currentState.isFlipped)
        }
    }

    fun startNewSession() {
        loadWordsFromRepository()
    }
}