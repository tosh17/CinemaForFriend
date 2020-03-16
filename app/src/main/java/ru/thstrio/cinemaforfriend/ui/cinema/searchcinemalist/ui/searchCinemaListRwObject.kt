package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.ui

import android.os.Handler
import android.os.Looper
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo
import ru.thstrio.cinemaforfriend.mvi.ui.MviUi
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListActionEffect
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListEvent
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListState

fun createPageList(
    position: Int,
    sourceSubject: PublishSubject<List<CinemaPojo>>,
    view: MviUi<SearchCinemaListState, SearchCinemaListEvent, SearchCinemaListActionEffect.SearchCinemaListNews>
    ): PagedList<CinemaPojo> {
    val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(3)
        .build()

    val source = object : PositionalDataSource<CinemaPojo>() {
        var subsr: Disposable? = null
        override fun loadRange(
            params: LoadRangeParams,
            callback: LoadRangeCallback<CinemaPojo>
        ) {
            subsr = sourceSubject.subscribe { data ->
                callback.onResult(data)
                subsr?.dispose()
            }
            view.onNextEvent(SearchCinemaListEvent.LoadNextPage)
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<CinemaPojo>
        ) {

            subsr = sourceSubject.subscribe { data ->
                callback.onResult(data, position)
                subsr?.dispose()
            }
            view.onNextEvent(SearchCinemaListEvent.LoadFirstPage)
        }

    }

    val mainTreead = Handler(Looper.getMainLooper())
    return PagedList.Builder(source, config)
        .setFetchExecutor { mainTreead.post(it) }
        .setNotifyExecutor { mainTreead.post(it) }
          .build()
}