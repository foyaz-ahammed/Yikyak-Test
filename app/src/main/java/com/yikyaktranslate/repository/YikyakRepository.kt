package com.yikyaktranslate.repository

import com.yikyaktranslate.repository.service.TranslationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class used on the project
 */
class YikyakRepository(private val api: TranslationService) {
    suspend fun getLanguages() =
        withContext(Dispatchers.IO) {
            api.getLanguages()
        }

    suspend fun getTranslation(query: String, source: String, target: String ) =
        withContext(Dispatchers.IO) {
            api.getTranslation(
                query = query,
                source = source,
                target = target
            )
        }
}