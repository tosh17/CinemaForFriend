package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi

import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo

sealed class SearchCinemaListAction {
    data class EnterSearchText(val text: String) : SearchCinemaListAction()
    data class LoadInitPage(val list: List<CinemaPojo>, val page: Int) : SearchCinemaListAction()
    data class LoadNextPage(val list: List<CinemaPojo>, val page: Int) : SearchCinemaListAction()
    data class SaveCurrentPosition(val position: Int) : SearchCinemaListAction()
    object NoAction : SearchCinemaListAction()
}