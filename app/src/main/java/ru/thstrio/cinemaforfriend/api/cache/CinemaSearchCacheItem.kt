package ru.thstrio.cinemaforfriend.api.cache

import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaPojo
import kotlin.math.max

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

    fun getToPage(): Pair<List<CinemaPojo>, Int> {

        var list = listOf<CinemaPojo>()
        var page = cache.keys.max()
        for(i in 1..page!!){
            cache[i]?.let{
                list+=it
            }
        }
        return list to page
    }

}