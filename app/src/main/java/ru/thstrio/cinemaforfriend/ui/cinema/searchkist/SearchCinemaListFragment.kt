package ru.thstrio.cinemaforfriend.ui.cinema.searchkist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.search_cinema_list_fragment.view.*
import ru.thstrio.cinemaforfriend.R
import ru.thstrio.cinemaforfriend.api.cache.Cache
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo
import ru.thstrio.cinemaforfriend.ui.cinema.searchkist.mvi.event.SearchCinemaListUIEvent
import ru.thstrio.cinemaforfriend.ui.cinema.searchkist.mvi.model.SearchCinemaListViewModel
import ru.thstrio.cinemaforfriend.ui.common.ObservableSourseFragment
import kotlin.random.Random


class SearchCinemaListFragment : ObservableSourseFragment<SearchCinemaListUIEvent>(),
    Consumer<SearchCinemaListViewModel> {

    lateinit var pagedList: PagedList<CinemaPojo>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.search_cinema_list_fragment, container, false)
        initRw(view)
        return view
    }

    fun initRw(view: View) {
        pagedList = createPageList()
        val adapter = SearchCinemaListAdapter(SearchCinemaDifUtil())

        view.run {
            search_cinema_list_rw.layoutManager = LinearLayoutManager(context)
            search_cinema_list_rw.adapter = adapter
            adapter.submitList(pagedList)
        }
    }


    private var sourceSubject = PublishSubject.create<List<CinemaPojo>>()
    private fun createPageList(): PagedList<CinemaPojo> {
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

                loadPL(params.startPosition, params.startPosition + params.loadSize)

            }

            override fun loadInitial(
                params: LoadInitialParams,
                callback: LoadInitialCallback<CinemaPojo>
            ) {

                subsr = sourceSubject.subscribe { data ->
                    callback.onResult(data, 0)
                    subsr?.dispose()
                }
                loadPL(params.requestedStartPosition, params.requestedLoadSize)
            }

        }

        val mainTreead = Handler(Looper.getMainLooper())
        return PagedList.Builder(source, config)
            .setFetchExecutor { mainTreead.post(it) }
            .setNotifyExecutor { mainTreead.post(it) }

            .build()
    }

    val cache = Cache()
    var page = 1
    val listCinema= mutableListOf<CinemaPojo>()
    private fun loadPL(start: Int, finish: Int) {
        Log.d("RW ---- ", "$start to $finish")
        if(start==0) page=1
        val list = cache.findCinemaByName("сердце", page++)
        list.subscribe { r ->
            listCinema.addAll(r)
            val delta= listCinema.toList().minus(pagedList)
            sourceSubject.onNext(delta) }



    }

    override fun accept(model: SearchCinemaListViewModel?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}