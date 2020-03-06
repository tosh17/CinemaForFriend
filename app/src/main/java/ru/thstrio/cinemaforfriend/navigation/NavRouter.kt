package ru.thstrio.cinemaforfriend.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.thstrio.cinemaforfriend.navigation.screen.LentaScreen
import ru.thstrio.cinemaforfriend.navigation.screen.MeScreen

class NavRouter {
    private val cicerone = Cicerone.create()
    private val router
        get() = cicerone.router
    private val navigator
        get() = cicerone.navigatorHolder

    fun setNavigator(nav: Navigator) {
        navigator.setNavigator(nav)
    }

    fun initChain(){
        router.newRootScreen(MeScreen())
    }
    fun goTo(screen: Screens) {
        router.navigateTo(
            when (screen) {
                Screens.Me -> MeScreen()
                Screens.Lenta -> LentaScreen()
            }
        )

    }
}