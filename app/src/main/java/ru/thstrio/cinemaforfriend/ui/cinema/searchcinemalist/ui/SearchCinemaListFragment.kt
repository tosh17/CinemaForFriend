package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.search_cinema_list_fragment.view.*
import org.koin.android.ext.android.get
import ru.thstrio.cinemaforfriend.R
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo
import ru.thstrio.cinemaforfriend.mvi.model.MviModel
import ru.thstrio.cinemaforfriend.mvi.ui.MviFragment
import ru.thstrio.cinemaforfriend.mvi.ui.MviUi
import ru.thstrio.cinemaforfriend.navigation.NavRouter
import ru.thstrio.cinemaforfriend.navigation.Screens
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListActionEffect
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListEvent
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListState
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListViewModel
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.util.SearchCinemaDifUtil

class SearchCinemaListFragment :
    MviFragment<SearchCinemaListState, SearchCinemaListEvent, SearchCinemaListActionEffect.SearchCinemaListNews>() {
    val model by activityViewModels<SearchCinemaListViewModel>()
    var pagedList: PagedList<CinemaPojo>? = null
    lateinit var router: NavRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = get()
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

    private fun initView(view: View) {
        log("initView")
        view.search_cinema_list_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onNextEvent(SearchCinemaListEvent.EnterSearchText(v.text.toString()))
                true
            } else false
        }
    }

    private fun rwNotSetAdapter() =
        requireView().search_cinema_list_rw.adapter == null

    fun initRw(view: View = requireView(), position: Int = 0) {
        pagedList = createPageList(
            position,
            sourceSubject,
            this as MviUi<SearchCinemaListState, SearchCinemaListEvent, SearchCinemaListActionEffect.SearchCinemaListNews>
        )
        val adapter =
            SearchCinemaListAdapter(
                SearchCinemaDifUtil(), model
            )
        view.run {
            search_cinema_list_rw.layoutManager = LinearLayoutManager(context)
            search_cinema_list_rw.adapter = adapter
            search_cinema_list_rw.setHasFixedSize(true)
            adapter.submitList(pagedList)
            if(position>0) search_cinema_list_rw.scrollToPosition(position)
        }
    }


    private var sourceSubject = PublishSubject.create<List<CinemaPojo>>()


    override fun onSaveInstanceState(outState: Bundle) {
        model.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {

        onNextEvent(
            SearchCinemaListEvent.SaveCurrentPosition(getListCurrentPosition())
        )
        log("save possition ${getListCurrentPosition()}")
        mIntent?.dispose()
        super.onPause()
    }

    fun getListCurrentPosition(): Int {
        val layoutManager = view?.search_cinema_list_rw?.layoutManager
        return if (layoutManager != null) (layoutManager as LinearLayoutManager).findLastVisibleItemPosition() else 0
    }

    override fun getModel(): MviModel<SearchCinemaListState, SearchCinemaListEvent, SearchCinemaListActionEffect.SearchCinemaListNews> {
        return model
    }


    fun addToPagedList(list: List<CinemaPojo>) {
        val delta = list.toList().minus(pagedList!!)
        sourceSubject.onNext(delta)
    }

    override fun onUpdateUi(state: SearchCinemaListState) {
        log("onUpdateState")
        log("  "+state.listCinema.mapIndexed { index, cinemaPojo ->  "[$index::${cinemaPojo.id}]"}.toString() )
        requireView().search_cinema_list_search.setText(state.searchText)
        when{
            state.isNewSearch->initRw()
            rwNotSetAdapter() && state.listCinema.isNotEmpty()->initRw(position = state.currentPosition)
            state.isLoaded ->addToPagedList(state.listCinema)
           state.currentPosition>0 -> requireView().search_cinema_list_rw.scrollToPosition(state.currentPosition)
        }
      }

    override fun onNextNews(news: SearchCinemaListActionEffect.SearchCinemaListNews) {
        val res = when (news) {
            is SearchCinemaListActionEffect.SearchCinemaListNews.OpenCinema
            -> router.goTo(Screens.InfoCinema, news.id)
            is SearchCinemaListActionEffect.SearchCinemaListNews.ShowError -> printToast(news.message)
        }
    }


}