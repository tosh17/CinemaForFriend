package ru.thstrio.cinemaforfriend.mvi.model

import io.reactivex.Observable
import ru.thstrio.cinemaforfriend.mvi.entity.MviAction
import ru.thstrio.cinemaforfriend.mvi.entity.MviState
import ru.thstrio.cinemaforfriend.mvi.entity.MviUiEvent

abstract class MviDataModel<S: MviState,E:MviUiEvent,A:MviAction>: (S, E) -> Observable<A>