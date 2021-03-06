package ru.thstrio.cinemaforfriend.ui.login.mvi.event

interface LoginUIEvent
sealed class SimpleLoginUIEvent : LoginUIEvent {
    object SelectSingIn : SimpleLoginUIEvent()
    object SelectSingUp : SimpleLoginUIEvent()
}

sealed class ActorLoginUIEvent : LoginUIEvent {
    object CreateUserEmail : ActorLoginUIEvent()
    data class LoginByGoogle(val token: String?) : ActorLoginUIEvent()
}
