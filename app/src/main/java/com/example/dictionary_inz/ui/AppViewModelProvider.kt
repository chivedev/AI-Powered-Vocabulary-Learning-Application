package com.example.dictionary_inz.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dictionary_inz.data.AppDataContainer
import com.example.dictionary_inz.ui.details.ItemDetailsViewModel
import com.example.dictionary_inz.ui.home.HomeViewModel
import com.example.dictionary_inz.ui.learn.LearnSelectionViewModel
import com.example.dictionary_inz.ui.learn.flashcards.FlashcardViewModel
import com.example.dictionary_inz.ui.learn.quiz.QuizViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(wordRepository = AppDataContainer.wordRepository)
        }
        initializer {
            ItemDetailsViewModel(wordRepository = AppDataContainer.wordRepository)
        }
        initializer {
            QuizViewModel(wordRepository = AppDataContainer.wordRepository)
        }
        initializer {
            FlashcardViewModel(wordRepository = AppDataContainer.wordRepository)
        }
        initializer {
            LearnSelectionViewModel(wordRepository = AppDataContainer.wordRepository)
        }
    }
}