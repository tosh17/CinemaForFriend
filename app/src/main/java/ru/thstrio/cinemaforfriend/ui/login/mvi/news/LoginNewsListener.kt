package ru.thstrio.cinemaforfriend.ui.login.mvi.news

import android.content.Context
import android.widget.Toast

import io.reactivex.functions.Consumer
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginActorFeature


class LoginNewsListener(
    private val context: Context
) : Consumer<LoginActorFeature.News> {

    override fun accept(news: LoginActorFeature.News) {
        when (news) {
            is LoginActorFeature.News.ErrorExecutingRequest -> errorHappened(news.throwable)
        }
    }

    fun errorHappened(throwable: Throwable) {
        Toast.makeText(context, throwable.message, Toast.LENGTH_LONG).show()
            // Timber.w(throwable)
    }
}