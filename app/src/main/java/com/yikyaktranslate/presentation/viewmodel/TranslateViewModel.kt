package com.yikyaktranslate.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yikyaktranslate.model.Language
import com.yikyaktranslate.repository.YikyakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * View model class used on translation page
 */
class TranslateViewModel(private val repository: YikyakRepository) : ViewModel() {

    // Code for the source language that we are translating from; currently hardcoded to English
    /* we already have source language, so this won't be necessary anymore. */
//    private val sourceLanguageCode: String = application.getString(R.string.source_language_code)

    // List of Languages that we get from the back end
    private val languages: MutableStateFlow<List<Language>> by lazy {
        MutableStateFlow<List<Language>>(listOf()).also {
            loadLanguages()
        }
    }

    // List of names of languages to display to user
    val languagesToDisplay = languages.map { it.map { language ->  language.name } }.asLiveData()

    // Index within languages/languagesToDisplay that the user has selected
    val sourceLanguageIndex = mutableStateOf(0)
    val targetLanguageIndex = mutableStateOf(0)

    // Text that the user has input to be translated
    private val _textToTranslate = MutableStateFlow(TextFieldValue(""))
    val textToTranslate = _textToTranslate.asLiveData()

    private val _translatedText = MutableStateFlow("")
    val translatedText = _translatedText.asLiveData()

    /**
     * Loads the languages from our service
     */
    private fun loadLanguages() {
        viewModelScope.launch {
            try {
                val result = repository.getLanguages()
                languages.value = result
            } catch (e: Exception) {
                Log.e(javaClass.name, e.toString())
                languages.value = emptyList()
            }
        }
    }

    /**
     * Updates the data when there's new text from the user
     *
     * @param newText TextFieldValue that contains user input we want to keep track of
     */
    fun onInputTextChange(newText: TextFieldValue) {
        _textToTranslate.value = newText
    }

    /**
     * Updates the selected target language when the user selects a new language
     *
     * @param newLanguageIndex Represents the index for the chosen language in the list of languages
     */
    fun onTargetLanguageChange(newLanguageIndex: Int) {
        targetLanguageIndex.value = newLanguageIndex
    }

    /**
     * Updates the selected source language when the user selects a new language
     *
     * @param newLanguageIndex Represents the index for the chosen language in the list of languages
     */
    fun onSourceLanguageChange(newLanguageIndex: Int) {
        sourceLanguageIndex.value = newLanguageIndex
    }

    fun onTranslateText() {
        if (languages.value.isEmpty()) return

        viewModelScope.launch {
            try {
                val result = repository.getTranslation(
                    query = _textToTranslate.value.text,
                    source = languages.value[sourceLanguageIndex.value].code,
                    target = languages.value[targetLanguageIndex.value].code
                )
                _translatedText.value = result.translatedText
            } catch (e: Exception) {
                Log.e(javaClass.name, e.toString())
                _translatedText.value = ""
            }
        }
    }

}