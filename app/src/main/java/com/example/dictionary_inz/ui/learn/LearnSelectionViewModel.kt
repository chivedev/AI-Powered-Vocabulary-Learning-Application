package com.example.dictionary_inz.ui.learn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary_inz.data.repository.WordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//class LearnSelectionViewModel(
//private val wordRepository: WordRepository
//) : ViewModel() {
//
//    private val _isDatabaseEmpty = MutableStateFlow(false)
//    val isDatabaseEmpty: StateFlow<Boolean> = _isDatabaseEmpty
//
//    init {
//        viewModelScope.launch {
//            val allWords = wordRepository.getAllWords().firstOrNull().orEmpty()
//            _isDatabaseEmpty.update {
//                allWords.isEmpty()
//            }
//        }
//    }
//}

data class LearnSelectionUiState(
    val isDatabaseEmpty: Boolean = false
)

class LearnSelectionViewModel(
    private val wordRepository: WordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LearnSelectionUiState())
    val uiState: StateFlow<LearnSelectionUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val allWords = wordRepository.getAllWords().firstOrNull().orEmpty()
            _uiState.update { state ->
                state.copy(isDatabaseEmpty = allWords.isEmpty())
            }
        }
    }
}