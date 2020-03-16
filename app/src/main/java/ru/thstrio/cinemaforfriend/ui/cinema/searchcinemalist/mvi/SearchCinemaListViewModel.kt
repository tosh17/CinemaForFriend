package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi

import android.annotation.SuppressLint
import android.os.Bundle
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import ru.thstrio.cinemaforfriend.mvi.model.MviModel

class SearchCinemaListViewModel() :
    MviModel<SearchCinemaListState, SearchCinemaListEvent, SearchCinemaListActionEffect.SearchCinemaListNews>(
        state = SearchCinemaListState(),
        mviIntent = BehaviorSubject.create(),
        mviNewsIntent = PublishSubject.create()
    ), KoinComponent {

    val reducer = SearchCinemaListReducer()
    val dataModel = SearchCinemaListDataModel()


    fun saveState(outState: Bundle) {
        outState.putParcelable("state", state)
    }

    @SuppressLint("CheckResult")
    override fun onNextUiEvent(eventUI: SearchCinemaListEvent) {
        dataModel(state, eventUI)
            .onErrorReturn { error ->
                SearchCinemaListActionEffect.SearchCinemaListNews.ShowError(
                    error.message.orEmpty()
                )
            }
            .subscribe { next: SearchCinemaListActionEffect ->
                when (next) {
                    is SearchCinemaListActionEffect.SearchCinemaListEffect -> {
                        state = reducer(state, next)
                        mviIntent.onNext(state)
                    }
                    is SearchCinemaListActionEffect.SearchCinemaListNews -> mviNewsIntent.onNext(
                        next
                    )
                }
            }
    }

    override fun subscribe() = mviIntent to mviNewsIntent
}