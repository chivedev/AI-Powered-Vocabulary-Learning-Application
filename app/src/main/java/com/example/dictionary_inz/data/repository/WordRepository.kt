package com.example.dictionary_inz.data.repository

import com.example.dictionary_inz.data.local.WordEntity
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun getWord(word: String): Result<WordEntity>
    suspend fun getWordById(wordId: Int): WordEntity?
    fun getAllWords(): Flow<List<WordEntity>>
    suspend fun deleteWord(wordId: Int)
}