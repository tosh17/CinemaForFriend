package ru.thstrio.cinemaforfriend.api.tmdb.pojo

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class GenrePojo : Parcelable {

    @SerializedName("id")
    @Expose
    var id: Long = 0

    @SerializedName("name")
    @Expose
    var name: String = ""
}