package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi

sealed class SearchCinemaListEvent {
    data class EnterSearchText(val text: String) : SearchCinemaListEvent()
    object LoadFirstPage: SearchCinemaListEvent()
    object LoadNextPage: SearchCinemaListEvent()
    data class SaveCurrentPosition(val position: Int): SearchCinemaListEvent()
    data class ClickCinemaItem(val id: Long): SearchCinemaListEvent()

}