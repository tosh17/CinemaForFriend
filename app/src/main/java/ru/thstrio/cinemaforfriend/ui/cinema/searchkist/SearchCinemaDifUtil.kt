package ru.thstrio.cinemaforfriend.ui.cinema.searchkist

import androidx.recyclerview.widget.DiffUtil
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo

class SearchCinemaDifUtil : DiffUtil.ItemCallback<CinemaPojo>() {
    override fun areItemsTheSame(oldItem: CinemaPojo, newItem: CinemaPojo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CinemaPojo, newItem: CinemaPojo): Boolean {
        return oldItem.id == newItem.id
    }

}