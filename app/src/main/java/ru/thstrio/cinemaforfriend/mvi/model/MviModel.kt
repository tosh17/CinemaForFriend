package ru.thstrio.cinemaforfriend.mvi.model

import androidx.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import ru.thstrio.cinemaforfriend.mvi.entity.*
import ru.thstrio.cinemaforfriend.ui.cinema.info.mvi.InfoCinemaReducer

abstract class MviModel<S : MviState, E : MviUiEvent>(
    val state: S,
    val mviIntent: BehaviorSubject<S>
) : ViewModel() {

    abstract fun subscribe(): BehaviorSubject<S>
    abstract fun onNextUiEvent(eventUI: E)

}