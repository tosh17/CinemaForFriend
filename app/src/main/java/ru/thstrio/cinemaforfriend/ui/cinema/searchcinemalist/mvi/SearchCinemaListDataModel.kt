package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.thstrio.cinemaforfriend.api.cache.Cache
import ru.thstrio.cinemaforfriend.mvi.model.MviDataModel
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListActionEffect.*
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListActionEffect.SearchCinemaListEffect.*

class SearchCinemaListDataModel :
    MviDataModel<SearchCinemaListState, SearchCinemaListEvent, SearchCinemaListActionEffect>(),
    KoinComponent {

    private val cache: Cache by inject()

    override fun invoke(
        state: SearchCinemaListState,
        event: SearchCinemaListEvent
    ): Observable<SearchCinemaListActionEffect> = when (event) {
        is SearchCinemaListEvent.EnterSearchText -> Observable.just(
            EnterSearchText(event.text)
        )
        SearchCinemaListEvent.LoadFirstPage -> loadInitList(state.searchText)
        SearchCinemaListEvent.LoadNextPage -> loadNextList(state.searchText,state.currentPage+1)
        is SearchCinemaListEvent.SaveCurrentPosition -> Observable.just(
            SaveCurrentPosition(event.position)
        )
        is SearchCinemaListEvent.ClickCinemaItem -> Observable.just(
            SearchCinemaListNews.OpenCinema(
                event.id
            )
        )
    }.doOnError { error -> SearchCinemaListNews.ShowError(error.message.orEmpty()) }
        .map { it as SearchCinemaListActionEffect }


    private fun loadList(search: String,page:Int=1) =
        cache
            .findCinemaByName(name = search,page = page)
            .subscribeOn(Schedulers.io())

    private fun loadInitList(search: String) = loadList(search)
        .map { (list, page) ->
            LoadInitPage(list, page)
        }

    private fun loadNextList(search: String,page : Int) = loadList(search,page=page)
        .map { (list, page) ->
            LoadNextPage(list, page)
        }

}