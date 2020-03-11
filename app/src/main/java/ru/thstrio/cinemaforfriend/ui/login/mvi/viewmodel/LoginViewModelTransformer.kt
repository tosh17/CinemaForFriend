package ru.thstrio.cinemaforfriend.ui.login.mvi.viewmodel

import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginActorFeature
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginFeature

class LoginViewModelTransformer :
        (Pair<LoginFeature.State, LoginActorFeature.State>) -> LoginViewModel {
    override fun invoke(pair: Pair<LoginFeature.State, LoginActorFeature.State>): LoginViewModel {
        val (state1, state2) = pair
        return LoginViewModel(isModeSignIn = state1.isModeSignIn,isLoginComplete = state2.isLoginComplete)
    }
}