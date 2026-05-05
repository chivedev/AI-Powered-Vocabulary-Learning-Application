package com.example.dictionary_inz.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val englishWord: String,
    val polishTranslation: String,
    val englishSentence: String,
    val polishSentence: String
)