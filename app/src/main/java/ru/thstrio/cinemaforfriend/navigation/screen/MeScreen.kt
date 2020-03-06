package ru.thstrio.cinemaforfriend.navigation.screen

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.thstrio.cinemaforfriend.ui.me.MeFragment

class MeScreen : SupportAppScreen() {
    override fun getScreenKey(): String {
        return "MeScreen"
    }

    override fun getFragment(): Fragment? {
        return MeFragment()
    }
}