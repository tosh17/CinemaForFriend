package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo
import ru.thstrio.cinemaforfriend.mvi.entity.*

@Parcelize
data class SearchCinemaListState(
    var isNewSearch: Boolean = false,
    val isLoaded: Boolean = false,
    val searchText: String = "",
    val currentPosition: Int = 0,
    val currentPage: Int = -1,
    val listCinema: List<CinemaPojo> = listOf()
) : Parcelable, MviState


sealed class SearchCinemaListEvent : MviUiEvent {
    data class EnterSearchText(val text: String) : SearchCinemaListEvent()
    object LoadFirstPage : SearchCinemaListEvent()
    object LoadNextPage : SearchCinemaListEvent()
    data class SaveCurrentPosition(val position: Int) : SearchCinemaListEvent()
    data class ClickCinemaItem(val id: Long) : SearchCinemaListEvent()

}

sealed class SearchCinemaListActionEffect : MviAction {
    sealed class SearchCinemaListEffect : SearchCinemaListActionEffect(),
        MviEffect {
        data class EnterSearchText(val text: String) : SearchCinemaListEffect()
        data class LoadInitPage(val list: List<CinemaPojo>, val page: Int) :
            SearchCinemaListEffect()

        data class LoadNextPage(val list: List<CinemaPojo>, val page: Int) :
            SearchCinemaListEffect()

        data class SaveCurrentPosition(val position: Int) : SearchCinemaListEffect()
        object NoAction : SearchCinemaListEffect()
    }

    sealed class SearchCinemaListNews : SearchCinemaListActionEffect(), MviNews {
        data class OpenCinema(val id: Long) : SearchCinemaListNews()
        data class ShowError(val message: String) : SearchCinemaListNews()
    }
}


class SearchCinemaListReducer :
    MviReducer<SearchCinemaListState, SearchCinemaListActionEffect.SearchCinemaListEffect>() {
    override fun invoke(
        state: SearchCinemaListState,
        action: SearchCinemaListActionEffect.SearchCinemaListEffect
    ): SearchCinemaListState = when (action) {
        is SearchCinemaListActionEffect.SearchCinemaListEffect.EnterSearchText -> state.copy(
            isNewSearch = true,
            searchText = action.text,
            currentPage = -1,
            listCinema = listOf(),
            currentPosition = 0, isLoaded = false
        )
        is SearchCinemaListActionEffect.SearchCinemaListEffect.LoadInitPage -> state.copy(
            isNewSearch = false,
            isLoaded = true,
            listCinema = action.list,
            currentPage = action.page
        )
        is SearchCinemaListActionEffect.SearchCinemaListEffect.LoadNextPage -> state.copy(
            isNewSearch = false,
            isLoaded = true,
            listCinema = state.listCinema.plus(action.list),
            currentPage = action.page
        )
        SearchCinemaListActionEffect.SearchCinemaListEffect.NoAction -> state
        is SearchCinemaListActionEffect.SearchCinemaListEffect.SaveCurrentPosition -> state.copy(
            isNewSearch = false,
            isLoaded = false,
            currentPosition = action.position
        )
    }
}