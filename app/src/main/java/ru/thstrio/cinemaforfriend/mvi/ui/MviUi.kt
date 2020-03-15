package ru.thstrio.cinemaforfriend.mvi.ui

import ru.thstrio.cinemaforfriend.mvi.entity.MviState
import ru.thstrio.cinemaforfriend.mvi.entity.MviUiEvent

interface MviUi<S: MviState,E: MviUiEvent> {
    fun onUpdateUi(state: S)
    fun onNextEvent(eventUI:E)
}