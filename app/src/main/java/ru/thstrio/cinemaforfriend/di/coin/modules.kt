package ru.thstrio.cinemaforfriend.di.coin


import org.koin.dsl.module
import ru.thstrio.cinemaforfriend.api.cache.Cache
import ru.thstrio.cinemaforfriend.api.tmdb.service.TmdbServise
import ru.thstrio.cinemaforfriend.firebase.auth.FAuth
import ru.thstrio.cinemaforfriend.navigation.NavRouter
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginFeature

val appModule = module {
    single { LoginFeature() }

}
val navigationModule = module {
    single { NavRouter() }
}
val firebaseModule = module {
    single { FAuth() }
}
val retrofitModule = module {
    single { TmdbServise.instance }
    single { Cache()}
}