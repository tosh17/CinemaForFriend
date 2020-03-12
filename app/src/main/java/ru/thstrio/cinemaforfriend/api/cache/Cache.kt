package ru.thstrio.cinemaforfriend.api.cache

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.ListCinemaPojo
import ru.thstrio.cinemaforfriend.api.tmdb.service.TmdbServise

class Cache {
    val api = TmdbServise.instance.createTmdbApi()
    private val cinemaSearchCache = HashMap<String, CinemaSearchCacheItem>()

    fun findCinemaByName(name: String, page: Int): Observable<List<CinemaPojo>> {
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
                    result.results
                }
        } else {
            Observable.just(item.getPage(page)).subscribeOn(Schedulers.io())
        }
    }


}