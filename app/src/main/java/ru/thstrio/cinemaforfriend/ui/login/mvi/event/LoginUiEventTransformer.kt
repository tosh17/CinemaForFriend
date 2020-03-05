package ru.thstrio.cinemaforfriend.ui.login.mvi.event

import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginFeature

class LoginUiEventTransformer : (LoginUIEvent) -> LoginFeature.Wish {
    override fun invoke(event: LoginUIEvent): LoginFeature.Wish {
        return when (event) {
            LoginUIEvent.SelectSingIn -> LoginFeature.Wish.SelectSingIn
            LoginUIEvent.SelectSingUp -> LoginFeature.Wish.SelectSingUp
        }
    }
}