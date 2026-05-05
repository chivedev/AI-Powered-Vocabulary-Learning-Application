package com.example.dictionary_inz.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary_inz.data.repository.WordRepository
import com.example.dictionary_inz.data.local.WordEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ItemDetailsUiState(
    val word: WordEntity? = null
)

class ItemDetailsViewModel(
    private val wordRepository: WordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemDetailsUiState())
    val uiState: StateFlow<ItemDetailsUiState> = _uiState.asStateFlow()

    fun loadWordById(wordId: Int) {
        viewModelScope.launch {
            val word = wordRepository.getWordById(wordId)
            _uiState.update { currentState ->
                currentState.copy(word = word)
            }
        }
    }

    fun deleteWord(wordId: Int) {
        viewModelScope.launch {
            wordRepository.deleteWord(wordId)
        }
    }
}




