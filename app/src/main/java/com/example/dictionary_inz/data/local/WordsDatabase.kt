package com.example.dictionary_inz.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities= [WordEntity::class], version=2, exportSchema = false)
abstract class WordsDatabase: RoomDatabase(){
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile private var INSTANCE: WordsDatabase? = null

        fun getDatabase(context: Context): WordsDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WordsDatabase::class.java,
                    "words_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}