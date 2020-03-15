package ru.thstrio.cinemaforfriend.api.tmdb.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaFullPojo
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.GenrePojo
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.ListCinemaPojo

val myApi: String = "0f53864b9f15b7159c4ace2185d26f63"
interface TmdbApi {


    @GET("search/movie")
    fun fingCinema(
        @Query("api_key") api: String=myApi,
        @Query("language") language: String="ru",
        @Query("query") cinema: String,
        @Query("page") page: Int=1

    ): Observable<ListCinemaPojo>

    @GET("movie/{id}}")
    fun getInfoCinema(   @Path("id") id :Long,
        @Query("api_key") api: String=myApi,
        @Query("language") language: String="ru"

    ): Observable<CinemaFullPojo>

    @GET("genre/movie/list")
    fun getGengeres(
        @Query("api_key") api: String=myApi,
        @Query("language") language: String="ru"
    ): Observable<List<GenrePojo>>
}