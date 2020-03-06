package ru.thstrio.cinemaforfriend.navigation.screen

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.thstrio.cinemaforfriend.ui.lenta.LentaFragment

class LentaScreen  : SupportAppScreen() {
    override fun getScreenKey(): String {
        return "LentfScreen"
    }

    override fun getFragment(): Fragment? {
        return LentaFragment()
    }
}