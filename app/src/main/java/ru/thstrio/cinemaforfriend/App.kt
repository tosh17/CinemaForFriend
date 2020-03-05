package ru.thstrio.cinemaforfriend

import android.app.Application
import org.koin.android.java.KoinAndroidApplication.create
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level
import org.koin.core.context.*


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        koin()

    }

    private fun koin() {
        val koinApplication: KoinApplication = create(this, Level.INFO).modules(appModule)
        startKoin(GlobalContext(), koinApplication)
    }
}