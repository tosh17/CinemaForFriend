package ru.thstrio.cinemaforfriend.mvi.model

import androidx.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.thstrio.cinemaforfriend.mvi.entity.*
import ru.thstrio.cinemaforfriend.ui.cinema.info.mvi.InfoCinemaReducer

abstract class MviModel<S : MviState, E : MviUiEvent, N : MviNews>(
    var state: S,
    val mviIntent: BehaviorSubject<S>,
    val mviNewsIntent: PublishSubject<N>
) : ViewModel() {

    abstract fun subscribe(): Pair<BehaviorSubject<S>,PublishSubject<N>>
    abstract fun onNextUiEvent(eventUI: E)

}