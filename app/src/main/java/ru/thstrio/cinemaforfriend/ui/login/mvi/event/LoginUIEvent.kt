package ru.thstrio.cinemaforfriend.ui.login.mvi.event

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface LoginUIEvent
sealed class SimpleLoginUIEvent : LoginUIEvent {
    object SelectSingIn : SimpleLoginUIEvent()
    object SelectSingUp : SimpleLoginUIEvent()
}

sealed class ActorLoginUIEvent : LoginUIEvent {
    object CreateUserEmail : ActorLoginUIEvent()
    data class LoginByGoogle(val token: String?) : ActorLoginUIEvent()
}
