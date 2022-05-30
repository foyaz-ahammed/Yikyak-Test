package com.yikyaktranslate.repository.service

import com.yikyaktranslate.model.Language
import com.yikyaktranslate.model.TranslateResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * API service used for translation
 */
interface TranslationService {
    @GET("/languages")
    suspend fun getLanguages() : List<Language>

    @POST("/translate")
    suspend fun getTranslation(
        @Query("q") query: String,
        @Query("source") source: String,
        @Query("target") target: String,
        @Query("format") format: String = "text",
        @Query("api_key") apiKey: String = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
    ): TranslateResponse
}