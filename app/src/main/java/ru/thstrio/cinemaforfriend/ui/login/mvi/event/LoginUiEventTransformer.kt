package ru.thstrio.cinemaforfriend.ui.login.mvi.event

import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginActorFeature
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginFeature

class LoginUiEventTransformer : (LoginUIEvent) -> LoginFeature.Wish? {
    override fun invoke(event: LoginUIEvent): LoginFeature.Wish? {
        if (event !is SimpleLoginUIEvent) return null
        return when (event) {
            SimpleLoginUIEvent.SelectSingIn -> LoginFeature.Wish.SelectSingIn
            SimpleLoginUIEvent.SelectSingUp -> LoginFeature.Wish.SelectSingUp
        }
    }
}

class LoginUiEventTransformer2 : (LoginUIEvent) -> LoginActorFeature.Wish? {
    override fun invoke(event: LoginUIEvent): LoginActorFeature.Wish? {
        if (event !is ActorLoginUIEvent) return null
        return when (event) {
             ActorLoginUIEvent.CreateUserEmail -> LoginActorFeature.Wish.CreateEmailUser
            ActorLoginUIEvent.LoginByGoogle -> LoginActorFeature.Wish.LoginByGoogle
        }
    }
}