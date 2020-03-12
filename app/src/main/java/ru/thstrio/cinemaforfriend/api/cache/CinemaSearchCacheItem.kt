package ru.thstrio.cinemaforfriend.api.cache

import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo

class CinemaSearchCacheItem {


    var totalPage = -1
    var totalResult = -1
    var cache = HashMap<Int, List<CinemaPojo>>()

    fun isPageLoad(page: Int) = cache.containsKey(page)

    fun cached(page: Int, results: List<CinemaPojo>) {
        cache[page] = results
    }

    fun getPage(page: Int): List<CinemaPojo> =
        if (isPageLoad(page)) cache[page]!!
        else listOf()

}