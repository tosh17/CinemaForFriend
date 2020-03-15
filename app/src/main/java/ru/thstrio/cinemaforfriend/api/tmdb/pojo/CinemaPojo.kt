package ru.thstrio.cinemaforfriend.api.tmdb.pojo

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class CinemaPojo:Parcelable {

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
    var posterPath: String?=""
}