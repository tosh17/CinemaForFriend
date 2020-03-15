package ru.thstrio.cinemaforfriend.ui.cinema.info.mvi

import androidx.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent
import ru.thstrio.cinemaforfriend.mvi.model.MviModel

class InfoCinemaViewModal: MviModel<InfoCinemaState, InfoCinemaUiEvent>(
    state=InfoCinemaState(),
    mviIntent= BehaviorSubject.create()
 ), KoinComponent {
    val reducer = InfoCinemaReducer()
    val dataModel= InfoCinemaDataModel()
    val mviNews: BehaviorSubject<InfoCinemaEffectAction.InfoCinemaNews> = BehaviorSubject.create()


    override fun subscribe(): BehaviorSubject<InfoCinemaState> {
      return   mviIntent
    }

    override fun onNextUiEvent(eventUI: InfoCinemaUiEvent) {
        dataModel(state, eventUI).subscribe { next: InfoCinemaEffectAction ->
            when (next) {
                is InfoCinemaEffectAction.InfoCinemaEffect -> mviIntent.onNext(reducer(state,next))
                is InfoCinemaEffectAction.InfoCinemaNews -> mviNews.onNext(next)
            }
        }
    }

////https://www.flaticon.com/packs/user-interface-54
}