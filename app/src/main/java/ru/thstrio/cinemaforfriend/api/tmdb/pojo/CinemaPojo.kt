package ru.thstrio.cinemaforfriend.api.tmdb.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CinemaPojo {

    @SerializedName("id")
    @Expose
    var id: Long = 0

    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("original_language")
    var originalLanguage: String = ""

    @SerializedName("release_date")
    @Expose
    var releaseDate: String = ""

    @SerializedName("original_title")
    @Expose
    var originalTitle: String = ""

    @SerializedName("vote_count")
    @Expose
    var voteCount: Long = 0

    @SerializedName("vote_average")
    @Expose
    lateinit var voteAverage: String

    @SerializedName("overview")
    @Expose
    lateinit var overview: String

    @SerializedName("backdrop_path")
    @Expose
    lateinit var backdropPath: String

    @SerializedName("poster_path")
    @Expose
    lateinit var posterPath: String
}