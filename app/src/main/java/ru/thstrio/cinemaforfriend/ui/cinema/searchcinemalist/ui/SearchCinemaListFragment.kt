package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.search_cinema_list_fragment.view.*
import ru.thstrio.cinemaforfriend.R
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListEvent
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListState
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListViewModel
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.util.SearchCinemaDifUtil
import java.util.concurrent.TimeUnit

class SearchCinemaListFragment : Fragment() {
    val model by activityViewModels<SearchCinemaListViewModel>()
    var pagedList: PagedList<CinemaPojo>? = null
    var mIntent: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.loadState(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        mIntent = model.subscribe().observeOn(AndroidSchedulers.mainThread())
            .subscribe { state -> onUpdateState(state) }
    }

    private fun log(s: String) {
        Log.d("TExt", this.toString().split(" ")[0] + "  " + s)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("onCreateView")
        var view = inflater.inflate(R.layout.search_cinema_list_fragment, container, false)
        initView(view)
        return view
    }

    lateinit var disposableSearch: Disposable
    private fun initView(view: View) {
        log("initView")
        disposableSearch = RxTextView.textChanges(view.search_cinema_list_search)
            .map { it.toString() }.debounce(1, TimeUnit.SECONDS)
            .subscribe({ text ->
                log("send text $text")
                model.doAction(SearchCinemaListEvent.EnterSearchText(text))
            }, { error ->
                //todo error
                Log.e("TExt", error.message)
            })

    }

    private fun rwNotSetAdapter() =
        requireView().search_cinema_list_rw.adapter == null

    fun initRw(view: View, position: Int = 0) {
        pagedList = createPageList(position)
        val adapter =
            SearchCinemaListAdapter(
                SearchCinemaDifUtil(),model
            )
        view.run {
            search_cinema_list_rw.layoutManager = LinearLayoutManager(context)
            search_cinema_list_rw.adapter = adapter
            adapter.submitList(pagedList)
        }
    }


    private var sourceSubject = PublishSubject.create<List<CinemaPojo>>()
    private fun createPageList(position: Int): PagedList<CinemaPojo> {
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
                model.doAction(SearchCinemaListEvent.LoadNextPage)
            }

            override fun loadInitial(
                params: LoadInitialParams,
                callback: LoadInitialCallback<CinemaPojo>
            ) {

                subsr = sourceSubject.subscribe { data ->
                    callback.onResult(data, position)
                    subsr?.dispose()
                }
                model.doAction(SearchCinemaListEvent.LoadFirstPage)
            }

        }

        val mainTreead = Handler(Looper.getMainLooper())
        return PagedList.Builder(source, config)
            .setFetchExecutor { mainTreead.post(it) }
            .setNotifyExecutor { mainTreead.post(it) }
            .setInitialKey(position)
            .build()
    }


    private fun onUpdateState(state: SearchCinemaListState) {
        log("onUpdateState")
        if (requireView().search_cinema_list_search.text.toString().isEmpty() && state.searchText.isNotEmpty())
            requireView().search_cinema_list_search.setText(state.searchText)
        if (state.searchText.isNotEmpty() && state.currentPage < 0) initRw(
            requireView(),
            state.currentPosition
        )
        if (state.currentPage > 0 && state.listCinema.isNotEmpty()) {
            if (pagedList == null || rwNotSetAdapter()) initRw(requireView(), state.currentPosition)
            val delta = state.listCinema.toList().minus(pagedList!!)
            sourceSubject.onNext(delta)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        model.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        disposableSearch.dispose()
        model.doAction(
            SearchCinemaListEvent.SaveCurrentPosition(getListCurrentPosition())
        )
        mIntent?.dispose()
        super.onPause()
    }

    fun getListCurrentPosition(): Int {
        val layoutManager = view?.search_cinema_list_rw?.layoutManager
        return if(layoutManager !=null)( layoutManager as LinearLayoutManager).findLastVisibleItemPosition() else 0
    }

}