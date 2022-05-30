package com.yikyaktranslate.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yikyaktranslate.presentation.viewmodel.TranslateViewModel
import com.yikyaktranslate.repository.YikyakRepository
import com.yikyaktranslate.repository.service.TranslationService
import com.yikyaktranslate.util.Constants
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val repositoryModule = module {
    single { provideMoshi() }
    single { provideRetrofit(get()) }
    single { provideTranslationService(get()) }
    single { YikyakRepository(get()) }
}

val viewModelModule = module {
    viewModel { TranslateViewModel(get()) }
}

fun provideMoshi(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

fun provideRetrofit(moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

fun provideTranslationService(retrofit: Retrofit): TranslationService {
    return retrofit.create(TranslationService::class.java)
}