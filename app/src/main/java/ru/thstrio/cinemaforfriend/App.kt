package ru.thstrio.cinemaforfriend

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.thstrio.cinemaforfriend.di.coin.appModule
import ru.thstrio.cinemaforfriend.di.coin.firebaseModule
import ru.thstrio.cinemaforfriend.di.coin.navigationModule
import ru.thstrio.cinemaforfriend.di.coin.retrofitModule


class App : Application() {
    companion object {
        lateinit var instatce: App
        var counter=1
    }

    override fun onCreate() {
        super.onCreate()
        instatce=this
        koin()

    }

    private fun koin() {
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, navigationModule, firebaseModule, retrofitModule))
        }
    }


}