package ru.thstrio.cinemaforfriend.ui.login.mvi.viewmodel

import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginFeature

class LoginViewModelTransformer  : (LoginFeature.State) -> LoginViewModel {
    override fun invoke(state: LoginFeature.State): LoginViewModel {
        return LoginViewModel(isModeSignIn = state.isModeSignIn)
    }
}