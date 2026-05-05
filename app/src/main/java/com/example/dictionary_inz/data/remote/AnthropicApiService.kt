package com.example.dictionary_inz.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AnthropicApiService {
    @POST("v1/messages")
    suspend fun translateWord(@Body request: ApiRequest): ApiResponse
}