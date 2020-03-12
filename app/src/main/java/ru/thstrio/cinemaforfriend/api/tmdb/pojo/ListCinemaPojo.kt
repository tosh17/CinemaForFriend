package ru.thstrio.cinemaforfriend.api.tmdb.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListCinemaPojo {
    @SerializedName("page")
    @Expose
    var page: Int = 0

    @SerializedName("total_pages")
    @Expose
    var totalPage: Int = 0

    @SerializedName("total_results")
    @Expose
    var totalResults: Int = 0

    @SerializedName("results")
    @Expose
    lateinit var results: List<CinemaPojo>
}