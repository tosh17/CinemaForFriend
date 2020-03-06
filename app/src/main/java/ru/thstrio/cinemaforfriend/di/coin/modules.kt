package ru.thstrio.cinemaforfriend.di.coin

import org.koin.dsl.module
import ru.thstrio.cinemaforfriend.navigation.NavRouter
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginFeature

val appModule = module {
    single { LoginFeature() }

}
val navigationModule = module {
    single { NavRouter() }
}