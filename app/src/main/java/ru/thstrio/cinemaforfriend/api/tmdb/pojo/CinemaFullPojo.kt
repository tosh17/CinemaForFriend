package ru.thstrio.cinemaforfriend.api.tmdb.pojo

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class CinemaFullPojo : CinemaPojo(),Parcelable {

    @SerializedName("genres")
    @Expose
    var genres: List<GenrePojo> = listOf()


}