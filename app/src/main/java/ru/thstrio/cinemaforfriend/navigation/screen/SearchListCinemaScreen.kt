package ru.thstrio.cinemaforfriend.navigation.screen

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.ui.SearchCinemaListFragment


class SearchListCinemaScreen : SupportAppScreen() {
    override fun getScreenKey(): String {
        return "SearchListCinemaScreen"
    }

    override fun getFragment(): Fragment? {
        return SearchCinemaListFragment()
    }
}