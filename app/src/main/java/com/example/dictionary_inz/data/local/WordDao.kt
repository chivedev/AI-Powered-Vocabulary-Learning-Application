package com.example.dictionary_inz.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity): Long

    @Query("SELECT * FROM words WHERE englishWord = :word LIMIT 1")
    suspend fun getEnglishWord(word: String): WordEntity?

    @Query("SELECT * FROM words WHERE id = :wordId LIMIT 1")
    suspend fun getWordById(wordId: Int): WordEntity?

    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<WordEntity>>

    @Query("DELETE FROM words WHERE id = :wordId")
    suspend fun deleteWord(wordId: Int)
}