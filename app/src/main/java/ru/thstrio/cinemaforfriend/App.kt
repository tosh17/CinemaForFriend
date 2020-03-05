package ru.thstrio.cinemaforfriend

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.thstrio.cinemaforfriend.di.coin.appModule


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        koin()

    }

    private fun koin() {
        startKoin{
            androidContext(this@App)
            modules(listOf(appModule))
        }
    }
}