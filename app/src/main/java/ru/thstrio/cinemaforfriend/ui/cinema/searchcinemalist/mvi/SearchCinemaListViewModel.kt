package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.thstrio.cinemaforfriend.api.cache.Cache
import ru.thstrio.cinemaforfriend.navigation.NavRouter
import ru.thstrio.cinemaforfriend.navigation.Screens
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListAction.*

class SearchCinemaListViewModel(var state: SearchCinemaListState = SearchCinemaListState()) :
    ViewModel(), KoinComponent {
    val mviIntent: BehaviorSubject<SearchCinemaListState>

    init {
        Log.d("TExt", this.toString().split(" ")[0] + "  CreateModel")
        mviIntent = BehaviorSubject.create()
    }

    val reducer = SearchCinemaListReducer()

    val mviAction: PublishSubject<SearchCinemaListAction> = PublishSubject.create()
    val actionsDispose = mviAction.filter { action -> action !is NoAction }.subscribe { action ->
        state = reducer(state, action)
        mviIntent.onNext(state)
    }
    private val cache: Cache by inject()
    private val router: NavRouter by inject()
    fun subscribe() = mviIntent
    fun doAction(event: SearchCinemaListEvent) {
        when (event) {
            is SearchCinemaListEvent.EnterSearchText ->
                if (event.text != state.searchText)
                    mviAction.onNext(EnterSearchText(event.text))
            SearchCinemaListEvent.LoadFirstPage -> {
                cache
                    .findCinemaByName(name = state.searchText)
                    .subscribeOn(Schedulers.io())
                    .subscribe { (list, page) -> mviAction.onNext(LoadInitPage(list, page)) }
            }
            SearchCinemaListEvent.LoadNextPage -> {
                cache
                    .findCinemaByName(name = state.searchText, page = state.currentPage + 1)
                    .subscribeOn(Schedulers.io())
                    .subscribe { (list, page) -> mviAction.onNext(LoadNextPage(list, page)) }

            }
            is SearchCinemaListEvent.ClickCinemaItem ->
                router.goTo(Screens.InfoCinema,event.id)
            is SearchCinemaListEvent.SaveCurrentPosition -> {
                mviAction.onNext(SaveCurrentPosition(event.position))
            }
        }

    }

    fun saveState(outState: Bundle) {
        outState.putParcelable("state", state)
    }

    fun loadState(savedInstanceState: Bundle?) {
        val loadState: SearchCinemaListState? = savedInstanceState?.getParcelable("state")
        loadState?.let { state = it }
        mviIntent.onNext(state)
    }
}