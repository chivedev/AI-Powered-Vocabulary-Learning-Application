package com.example.dictionary_inz.data.repository

import com.example.dictionary_inz.data.local.WordDao
import com.example.dictionary_inz.data.local.WordEntity
import com.example.dictionary_inz.data.remote.AnthropicApiService
import com.example.dictionary_inz.data.remote.ApiRequest
import com.example.dictionary_inz.data.remote.Message
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class WordRepositoryImpl(
    private val wordDao: WordDao,
    private val apiService: AnthropicApiService
) : WordRepository {

    override suspend fun getWord(word: String): Result<WordEntity> {
        val localWord = wordDao.getEnglishWord(word)
        if (localWord != null) {
            return Result.success(localWord)
        }

        return try {
            val apiResponse = apiService.translateWord(
                ApiRequest(
                    messages = listOf(
                        Message(
                            role = "user",
                            content = """
                            You are a native speaker of Polish and English.
                            You will help your students by giving them correct answers
                            and translating English and Polish words in both directions.
                            Translate the word: $word and provide sample sentences in both languages.
                            User input must be a single word!!
                            If the input: "$word" contains more than one word in both English and Polish,
                            set the following fields to "<ONLYONE>:
                            - polish_translation
                            - english_translation
                            If the word: $word is nonsense or cannot be recognized (e.g., "foigje"),
                            set the following fields to "<UNKNOWN>":
                            - polish_translation
                            - english_translation
                        """.trimIndent()
                        )
                    )
                )
            )

            val responseContent = apiResponse.content.firstOrNull() ?: return Result.failure(WordRepositoryError.UnknownError)

            val responseInput = responseContent.input

            if (responseInput.englishTranslation == "<UNKNOWN>" || responseInput.polishTranslation == "<UNKNOWN>") {
                return Result.failure(WordRepositoryError.WordNotFound(word))
            }

            if (responseInput.englishTranslation == "<ONLYONE>" || responseInput.polishTranslation == "<ONLYONE>") {
                return Result.failure(WordRepositoryError.SingleWordOnlyError)
            }

            val newWordEntity = WordEntity(
                englishWord = responseInput.englishTranslation, //ochrona przed literówką
                polishTranslation = responseInput.polishTranslation,
                englishSentence = responseInput.englishSentence,
                polishSentence = responseInput.polishSentence
            )

            val insertedId = wordDao.insertWord(newWordEntity)
            val finalWord = newWordEntity.copy(id = insertedId.toInt())

            return Result.success(finalWord)

        } catch (e: IOException) {
            Result.failure(WordRepositoryError.NoInternet)
        } catch (e: HttpException) {
            Result.failure(WordRepositoryError.ServerError)
        } catch (e: Exception) {
            Result.failure(WordRepositoryError.UnknownError)
        }
    }

    override suspend fun getWordById(wordId: Int): WordEntity? {
        return wordDao.getWordById(wordId)
    }

    override suspend fun deleteWord(wordId: Int) {
        wordDao.deleteWord(wordId)
    }

    override fun getAllWords(): Flow<List<WordEntity>> {
        return wordDao.getAllWords()
    }
}

