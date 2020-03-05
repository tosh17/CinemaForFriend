package ru.thstrio.cinemaforfriend.ui.login.mvi.event

sealed class LoginUIEvent {
    object SelectSingIn : LoginUIEvent()
    object SelectSingUp : LoginUIEvent()
   // data class EnterLogin(val text: String) : LoginUIEvent()
  //  object Enter : LoginUIEvent()
   // object EnterGoogle : LoginUIEvent()
   // object EnterFacebook : LoginUIEvent()
}