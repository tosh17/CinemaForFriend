package ru.thstrio.cinemaforfriend.ui.login.mvi.feature

import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ReducerFeature

class LoginFeature : ReducerFeature<LoginFeature.Wish, LoginFeature.State, Nothing>(
    initialState = State(),
    reducer = ReducerImpl()
) {
    // Define your immutable state as a Kotlin data class
    data class State(
        var isLogin: Boolean = false,
        val isModeSignIn: Boolean = true
    )

    // Define the ways it could be affected
    sealed class Wish {
        object SelectSingIn : Wish()
        object SelectSingUp : Wish()
        object LoginUser : Wish()
    }

    // Define your reducer
    class ReducerImpl : Reducer<State, Wish> {
        override fun invoke(state: State, wish: Wish): State =
            when (wish) {
                Wish.SelectSingIn -> state.copy(
                    isModeSignIn = true
                )
                is Wish.SelectSingUp -> state.copy(
                    isModeSignIn = false
                )
                is Wish.LoginUser -> state.copy(
                    isLogin = true
                )
            }
    }
}