package ru.thstrio.cinemaforfriend.api.cache

import android.util.Log
import android.util.LongSparseArray
import androidx.core.util.contains
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaFullPojo
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.ListCinemaPojo
import ru.thstrio.cinemaforfriend.api.tmdb.service.TmdbServise

class Cache {
    val api = TmdbServise.instance.createTmdbApi()
    private val cinemaSearchCache = HashMap<String, CinemaSearchCacheItem>()
    private val cinemaFullCache = LongSparseArray<CinemaFullPojo>()

    fun findCinemaByName(name: String, page: Int = 1): Observable<Pair<List<CinemaPojo>, Int>> {
        Log.d("TExt", "Start search $name  page $page")
        val item = cinemaSearchCache.getOrPut(name, { CinemaSearchCacheItem() })
        return if (item.totalPage == -1 || !item.isPageLoad(page)) {
            api.fingCinema(cinema = name, page = page)
                .subscribeOn(Schedulers.io())
                .map { result ->
                    item.run {
                        totalPage = result.totalPage
                        totalResult = result.totalResults
                        cached(result.page, result.results)
                    }
                    Log.d("TExt", "Start search $name  page $page find ${result.totalResults}")
                    result.results to page
                }
        } else {

            Observable.just(item.getToPage()).subscribeOn(Schedulers.io())
        }
    }

    fun loadCinema(id: Long): Observable<CinemaFullPojo> =
        if (cinemaFullCache.contains(id))
            Observable.just(cinemaFullCache[id])
        else {
            api.getInfoCinema(id=id)
                .subscribeOn(Schedulers.io())
                .map { cinema ->
                cinemaFullCache.put(id,cinema)
                cinema
            }
        }


}