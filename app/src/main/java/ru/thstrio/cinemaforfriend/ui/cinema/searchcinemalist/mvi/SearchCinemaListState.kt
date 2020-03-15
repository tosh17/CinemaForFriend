package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo


@Parcelize
data class SearchCinemaListState(
    val searchText: String = "",
    val currentPosition: Int = 0,
    val currentPage: Int = -1,
    val listCinema: List<CinemaPojo> = listOf()
) : Parcelable