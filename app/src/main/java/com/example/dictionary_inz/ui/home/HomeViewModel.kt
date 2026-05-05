package com.example.dictionary_inz.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary_inz.data.repository.WordRepository
import com.example.dictionary_inz.data.local.WordEntity
import com.example.dictionary_inz.data.repository.WordRepositoryError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class HomeUiState(
    val searchQuery: String = "",
    val wordList: List<WordEntity> = emptyList(),
    val errorMessage: WordRepositoryError? = null
)

class HomeViewModel(
    private val wordRepository: WordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            wordRepository.getAllWords().collect { words ->
                _uiState.update { currentState ->
                    currentState.copy(wordList = words)
                }
            }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { currentState ->
            currentState.copy(searchQuery = newQuery)
        }
    }

    fun performSearch(onWordFound: (Int) -> Unit) {
        viewModelScope.launch {
            val query = _uiState.value.searchQuery.trim()
            if (query.isNotEmpty()) {
                wordRepository.getWord(query)
                    .onSuccess {
                        onWordFound(it.id)
                        _uiState.update { currentState ->
                            currentState.copy(searchQuery = "", errorMessage = null)
                        }
                    }
                    .onFailure { error ->
                        val repositoryError = when (error) {
                            is WordRepositoryError.WordNotFound -> {
                                _uiState.update { currentState ->
                                    currentState.copy(searchQuery = "")
                                }
                                error
                            }
                            is WordRepositoryError.SingleWordOnlyError -> {
                                _uiState.update { currentState ->
                                    currentState.copy(searchQuery = "")
                                }
                                error
                            }
                            is WordRepositoryError -> error
                            is IOException -> WordRepositoryError.NoInternet
                            else -> WordRepositoryError.UnknownError
                        }
                        _uiState.update { currentState ->
                            currentState.copy(errorMessage = repositoryError)
                        }
                    }
            }
        }
    }

    fun clearErrorMessage() {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = null)
        }
    }
}