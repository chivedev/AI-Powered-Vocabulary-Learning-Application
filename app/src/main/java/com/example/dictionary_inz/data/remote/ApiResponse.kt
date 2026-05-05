package com.example.dictionary_inz.data.remote

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("id") val id: String,
    @SerializedName("content") val content: List<Content>
)

data class Content(
    @SerializedName("input") val input: TranslationInput
)

data class TranslationInput(
    @SerializedName("polish_translation") val polishTranslation: String,
    @SerializedName("english_translation") val englishTranslation: String,
    @SerializedName("english_sentence") val englishSentence: String,
    @SerializedName("polish_sentence") val polishSentence: String
)