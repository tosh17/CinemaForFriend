package ru.thstrio.cinemaforfriend.ui.login.mvi.feature

import android.os.Parcelable
import com.badoo.mvicore.feature.ActorReducerFeature
import com.badoo.mvicore.element.TimeCapsule.*
import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.element.TimeCapsule

import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginActorFeature.Wish
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginActorFeature.State
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginActorFeature.News
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginActorFeature.Effect

import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.parcel.Parcelize

import org.koin.core.KoinComponent
import org.koin.core.inject


import ru.thstrio.cinemaforfriend.firebase.auth.FAuth

class LoginActorFeature(
    timeCapsule: TimeCapsule<Parcelable>? = null
) : ActorReducerFeature<Wish, Effect, State, News>(
    initialState = timeCapsule?.get(LoginActorFeature::class.java) ?: State(),
    actor = ActorImpl(),
    reducer = ReducerImpl(),
    newsPublisher = NewsPublisherImpl()
) {
    init {
        timeCapsule?.register(LoginActorFeature::class.java) {
            state.copy(
                isLoading = false
            )
        }
    }

    @Parcelize
    data class State(
        val isLoading: Boolean = false,
        val isLoginComplete: Boolean = false,
        val email: String = "tosh17@list.tu",
        val password1: String = "qwrqwr",
        val password2: String = "hfgjfgjvj42"
    ) : Parcelable

    sealed class Wish {
        object CreateEmailUser : Wish()
        object LoginByGoogle : Wish()
    }

    sealed class Effect {
        //   object LoginEmailUser : Effect()
        object CreateUserComplete : Effect()
        data class ErrorCreateUser(val throwable: Throwable) : Effect()
    }

    sealed class News {
        data class ErrorExecutingRequest(val throwable: Throwable) : News()
    }


    class ActorImpl : Actor<State, Wish, Effect>, KoinComponent {
        val auth: FAuth by inject<FAuth>()

        override fun invoke(state: State, wish: Wish): Observable<Effect> = when (wish) {
            Wish.CreateEmailUser -> createUserByEmail(state.email, state.password1)
                .map { Effect.CreateUserComplete as Effect }
                .onErrorReturn { Effect.ErrorCreateUser(it) }
            Wish.LoginByGoogle -> {
                Observable.just(Effect.ErrorCreateUser(Throwable("ntcn")))
            }
        }

        fun createUserByEmail(email: String, password: String): Observable<Boolean> {
            return auth.createUserFromEmail(email, password)
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when (effect) {
            Effect.CreateUserComplete -> state.copy(isLoginComplete = true)
            is Effect.ErrorCreateUser -> state
        }
    }

    class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(wish: Wish, effect: Effect, state: State): News? = when (effect) {
            is Effect.ErrorCreateUser -> News.ErrorExecutingRequest(effect.throwable)
            else -> null
        }
    }
}