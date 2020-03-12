package ru.thstrio.cinemaforfriend.api.tmdb.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.ListCinemaPojo

interface TmdbApi {

    @GET("search/movie")
    fun fingCinema(
        @Query("api_key") api: String="0f53864b9f15b7159c4ace2185d26f63",
        @Query("language") language: String="ru",
        @Query("query") cinema: String,
        @Query("page") page: Int=1

    ): Observable<ListCinemaPojo>


}