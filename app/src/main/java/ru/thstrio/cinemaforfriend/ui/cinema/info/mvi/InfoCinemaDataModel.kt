package ru.thstrio.cinemaforfriend.ui.cinema.info.mvi

import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.thstrio.cinemaforfriend.api.cache.Cache
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaFullPojo
import ru.thstrio.cinemaforfriend.mvi.model.MviDataModel


class InfoCinemaDataModel :
    MviDataModel<InfoCinemaState, InfoCinemaUiEvent, InfoCinemaEffectAction>()
    , KoinComponent {
    private val cache: Cache by inject()
    override fun invoke(
        state: InfoCinemaState,
        event: InfoCinemaUiEvent
    ): Observable<InfoCinemaEffectAction> = when (event) {
        is InfoCinemaUiEvent.LoadInfoCinema -> loadCinema(event.id).map {
            InfoCinemaEffectAction.InfoCinemaEffect.LoadInfoCinema(it)
        }
    }

    private fun loadCinema(id: Long): Observable<CinemaFullPojo> = cache.loadCinema(id)


}