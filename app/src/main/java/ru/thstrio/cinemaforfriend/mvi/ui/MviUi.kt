package ru.thstrio.cinemaforfriend.mvi.ui

import ru.thstrio.cinemaforfriend.mvi.entity.MviNews
import ru.thstrio.cinemaforfriend.mvi.entity.MviState
import ru.thstrio.cinemaforfriend.mvi.entity.MviUiEvent

interface MviUi<S: MviState,E: MviUiEvent,N:MviNews> {
    fun onUpdateUi(state: S)
    fun onNextEvent(eventUI:E)
    fun onNextNews(news: N)
}