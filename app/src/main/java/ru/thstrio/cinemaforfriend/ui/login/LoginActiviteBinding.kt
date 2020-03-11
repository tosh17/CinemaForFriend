package ru.thstrio.cinemaforfriend.ui.login

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.named
import com.badoo.mvicore.binder.using
import ru.thstrio.cinemaforfriend.ui.login.mvi.event.LoginUiEventTransformer
import ru.thstrio.cinemaforfriend.ui.login.mvi.event.LoginUiEventTransformer2
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginActorFeature
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginFeature
import ru.thstrio.cinemaforfriend.ui.login.mvi.news.LoginNewsListener
import ru.thstrio.cinemaforfriend.ui.login.mvi.viewmodel.LoginViewModelTransformer

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.BiFunction

class LoginActiviteBinding(view:LoginActivity,
                           private val feature:LoginFeature,
                           private val feature2: LoginActorFeature,
                          // private val analyticsTracker: FakeAnalyticsTracker,
                           private val newsListener: LoginNewsListener
):AndroidBindings<LoginActivity>(view){
    override fun setup(view: LoginActivity) {
        binder.bind(combineLatest(feature, feature2)  to view using LoginViewModelTransformer() named "LoginActivity.ViewModels")
        binder.bind(view to feature using LoginUiEventTransformer())

        binder.bind(view to feature2 using LoginUiEventTransformer2())
      //  binder.bind(view to analyticsTracker named "LoginActivity.Analytics")
        binder.bind(feature2.news to newsListener named "LoginActivity.News")
    }

}
fun <T1, T2> combineLatest(o1: ObservableSource<T1>, o2: ObservableSource<T2>): ObservableSource<Pair<T1, T2>> =
    Observable.combineLatest(
        o1,
        o2,
        BiFunction<T1, T2, Pair<T1, T2>> { t1, t2 ->
            t1 to t2
        }
    )