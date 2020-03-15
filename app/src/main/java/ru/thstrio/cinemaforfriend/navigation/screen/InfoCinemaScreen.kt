package ru.thstrio.cinemaforfriend.navigation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.thstrio.cinemaforfriend.ui.cinema.info.IfoCinemaFragment
import ru.thstrio.cinemaforfriend.ui.cinema.info.IfoCinemaFragment.Companion.argDataName


class InfoCinemaScreen(val data: Long) : SupportAppScreen() {
    override fun getScreenKey(): String {
        return "IfoCinemaFragmentScreen"
    }

    override fun getFragment(): Fragment? {
        val fragment=IfoCinemaFragment()
        val args = Bundle()
        args.putLong(argDataName, data )
        fragment.arguments = args
        return fragment
    }
}