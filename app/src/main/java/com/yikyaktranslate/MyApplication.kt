package com.yikyaktranslate

import android.app.Application
import com.yikyaktranslate.modules.repositoryModule
import com.yikyaktranslate.modules.viewModelModule
import org.koin.core.context.startKoin

/**
 * Application class used on the project
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(repositoryModule, viewModelModule)
        }
    }
}