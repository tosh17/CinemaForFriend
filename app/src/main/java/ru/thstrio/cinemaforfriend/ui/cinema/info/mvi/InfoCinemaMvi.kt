package ru.thstrio.cinemaforfriend.ui.cinema.info.mvi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.CinemaFullPojo
import ru.thstrio.cinemaforfriend.mvi.entity.*


@Parcelize
data class InfoCinemaState(
    var idCinema: Long = 0,
    var isLoading: Boolean = false,
    var cinema: CinemaFullPojo? = null
) : Parcelable,
    MviState

sealed class InfoCinemaUiEvent :
    MviUiEvent {
    data class LoadInfoCinema(val id: Long) : InfoCinemaUiEvent()
}

sealed class InfoCinemaEffectAction : MviAction {
    sealed class InfoCinemaEffect : InfoCinemaEffectAction(), MviEffect {
        data class LoadInfoCinema(val cinema: CinemaFullPojo) : InfoCinemaEffect()
    }

    sealed class InfoCinemaNews : InfoCinemaEffectAction(), MviNews {
        data class showEroor(val cinema: CinemaFullPojo) : InfoCinemaNews()
    }
}

class InfoCinemaReducer : MviReducer<InfoCinemaState, InfoCinemaEffectAction.InfoCinemaEffect>() {
    override fun invoke(
        state: InfoCinemaState,
        effect: InfoCinemaEffectAction.InfoCinemaEffect
    ): InfoCinemaState =
        when (effect) {
            is InfoCinemaEffectAction.InfoCinemaEffect.LoadInfoCinema -> state.copy(
                cinema = effect.cinema,
                isLoading = true
            )
        }
}


