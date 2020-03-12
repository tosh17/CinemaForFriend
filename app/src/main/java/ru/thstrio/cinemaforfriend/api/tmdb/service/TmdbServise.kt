package ru.thstrio.cinemaforfriend.api.tmdb.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.thstrio.cinemaforfriend.api.tmdb.api.TmdbApi

class TmdbServise {
    companion object {
        val instance = TmdbServise()
    }

    private val mRetrofit: Retrofit
    val BASE_URL = "https://api.themoviedb.org/3/"


    init {

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun createTmdbApi() = mRetrofit.create(TmdbApi::class.java)

}