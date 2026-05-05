package com.example.dictionary_inz.data

import android.content.Context
import com.example.dictionary_inz.BuildConfig
import com.example.dictionary_inz.data.local.WordsDatabase
import com.example.dictionary_inz.data.remote.AnthropicApiService
import com.example.dictionary_inz.data.repository.WordRepository
import com.example.dictionary_inz.data.repository.WordRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppDataContainer {

    private lateinit var database: WordsDatabase

    private val retrofit: Retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        Retrofit.Builder()
            .baseUrl("https://api.anthropic.com")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("x-api-key", BuildConfig.API_KEY)
                            .addHeader("anthropic-version", "2023-06-01")
                            .addHeader("content-type", "application/json")
                            .build()
                        chain.proceed(request)
                    }
                    .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService: AnthropicApiService by lazy {
        retrofit.create(AnthropicApiService::class.java)
    }

    val wordRepository: WordRepository by lazy {
        WordRepositoryImpl(
            wordDao = database.wordDao(),
            apiService = apiService
        )
    }

    fun initializeDatabase(context: Context) {
        database = WordsDatabase.getDatabase(context)
    }
}