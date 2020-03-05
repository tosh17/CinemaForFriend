package ru.thstrio.cinemaforfriend.ui.login

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.using
import ru.thstrio.cinemaforfriend.ui.login.mvi.event.LoginUiEventTransformer
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginFeature
import ru.thstrio.cinemaforfriend.ui.login.mvi.viewmodel.LoginViewModelTransformer

class LoginActiviteBinding(view:LoginActivity, private val feature:LoginFeature):AndroidBindings<LoginActivity>(view){
    override fun setup(view: LoginActivity) {
        binder.bind(feature to view using LoginViewModelTransformer())
        binder.bind(view to feature using LoginUiEventTransformer())
    }

}