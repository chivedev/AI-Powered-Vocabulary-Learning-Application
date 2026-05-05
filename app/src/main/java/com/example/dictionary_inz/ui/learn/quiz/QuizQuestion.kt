package com.example.dictionary_inz.ui.learn.quiz

import com.example.dictionary_inz.data.local.WordEntity

enum class QuizDifficulty {
    EASY, HARD
}

data class QuizQuestion(
    val wordEntity: WordEntity,
    val difficulty: QuizDifficulty,
    val originalWords: List<String>,
    val missingIndex: Int?,
    val displayedSentence: String,
    val isMastered: Boolean = false
)