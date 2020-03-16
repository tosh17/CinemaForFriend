package ru.thstrio.cinemaforfriend.ui.cinema.info.mvi

import androidx.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import ru.thstrio.cinemaforfriend.mvi.model.MviModel

class InfoCinemaViewModal: MviModel<InfoCinemaState, InfoCinemaUiEvent, InfoCinemaEffectAction.InfoCinemaNews>(
    state=InfoCinemaState(),
    mviIntent= BehaviorSubject.create(),
    mviNewsIntent = PublishSubject.create()
 ), KoinComponent {
    val reducer = InfoCinemaReducer()
    val dataModel= InfoCinemaDataModel()

    override fun subscribe()    =    mviIntent to mviNewsIntent


    override fun onNextUiEvent(eventUI: InfoCinemaUiEvent) {
        dataModel(state, eventUI).subscribe { next: InfoCinemaEffectAction ->
            when (next) {
                is InfoCinemaEffectAction.InfoCinemaEffect -> mviIntent.onNext(reducer(state,next))
                is InfoCinemaEffectAction.InfoCinemaNews -> mviNewsIntent.onNext(next)
            }
        }
    }

////https://www.flaticon.com/packs/user-interface-54
}