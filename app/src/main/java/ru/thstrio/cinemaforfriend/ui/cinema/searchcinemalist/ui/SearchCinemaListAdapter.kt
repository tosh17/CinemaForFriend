package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.thstrio.cinemaforfriend.R
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListEvent
import ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi.SearchCinemaListViewModel

import java.lang.ref.WeakReference


class SearchCinemaListAdapter(
    diffCallback: DiffUtil.ItemCallback<CinemaPojo>,
    val model: SearchCinemaListViewModel
) :
    PagedListAdapter<CinemaPojo, SearchCinemaListHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCinemaListHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_cinema_list_item, parent, false)
        return SearchCinemaListHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: SearchCinemaListHolder, position: Int) {
        val item = getItem(position)
        item?.let { item ->
            holder.run {
                setTitle(item.title)
                if (item.posterPath != null) {
                    setLogo(item.posterPath!!)
                } else {
                    setLogoError()
                }
                setDescription(item.overview)
                setRate("${item.voteAverage}/${item.voteCount}")
            }
            holder.view.setOnClickListener {
                model.onNextUiEvent((SearchCinemaListEvent.ClickCinemaItem(item.id)))
            }
            holder.setNumber(position)

        }
    }
}

