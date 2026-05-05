package com.example.dictionary_inz.data.remote

import com.google.gson.annotations.SerializedName

data class ApiRequest(
    @SerializedName("model") val model: String = "claude-3-5-haiku-20241022",
    @SerializedName("max_tokens") val maxTokens: Int = 256,
    @SerializedName("tools") val tools: List<Tool> = listOf(Tool()),
    @SerializedName("tool_choice") val toolChoice: ToolChoice = ToolChoice(),
    @SerializedName("messages") val messages: List<Message>
)

data class ToolChoice(
    @SerializedName("type") val type: String = "tool",
    @SerializedName("name") val name: String = "translate_word"
)

data class Tool(
    @SerializedName("name") val name: String = "translate_word",
    @SerializedName("description") val description: String = "",
    @SerializedName("input_schema") val inputSchema: InputSchema = InputSchema()
)

data class InputSchema(
    @SerializedName("type") val type: String = "object",
    @SerializedName("properties") val properties: Properties = Properties(),
    @SerializedName("required") val required: List<String> = listOf("polish_translation", "english_translation", "english_sentence", "polish_sentence")
)

data class Properties(
    @SerializedName("polish_translation") val polishTranslation: PropertyDetails = PropertyDetails("string", "Translation of the word to Polish"),
    @SerializedName("english_translation") val englishTranslation: PropertyDetails = PropertyDetails("string", "Translation of the word to English"),
    @SerializedName("english_sentence") val englishSentence: PropertyDetails = PropertyDetails("string", "Sample sentence in English using the translated word (max 10 words)"),
    @SerializedName("polish_sentence") val polishSentence: PropertyDetails = PropertyDetails("string", "Polish translation of the english sentence")
)

data class PropertyDetails(
    @SerializedName("type") val type: String,
    @SerializedName("description") val description: String
)

data class Message(
    @SerializedName("role") val role: String,
    @SerializedName("content") val content: String
)