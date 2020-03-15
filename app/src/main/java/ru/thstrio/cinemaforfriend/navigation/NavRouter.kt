package ru.thstrio.cinemaforfriend.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.thstrio.cinemaforfriend.navigation.screen.*

class NavRouter {
    private val cicerone = Cicerone.create()
    private val router
        get() = cicerone.router
    private val navigator
        get() = cicerone.navigatorHolder

    fun setNavigator(nav: Navigator) {
        navigator.setNavigator(nav)
    }

    fun initChain() {
        router.newRootScreen( SearchListCinemaScreen())
       // router.newRootScreen(InfoCinemaScreen(454626L))
    }

    fun goTo(screen: Screens, data: Any? = null) {
        router.navigateTo(
            when (screen) {
                Screens.Me -> MeScreen()
                Screens.Lenta -> LentaScreen()
                Screens.SearchListCinema -> SearchListCinemaScreen()
                Screens.InfoCinema -> InfoCinemaScreen(data as Long)
            }
        )

    }

    fun removeNavigator() {
        navigator.removeNavigator()
    }
}