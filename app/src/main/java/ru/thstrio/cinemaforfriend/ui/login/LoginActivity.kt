package ru.thstrio.cinemaforfriend.ui.login

import android.os.Bundle
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_login.*
import ru.thstrio.cinemaforfriend.R
import ru.thstrio.cinemaforfriend.ui.login.mvi.event.LoginUIEvent
import ru.thstrio.cinemaforfriend.ui.login.mvi.event.LoginUIEvent.*
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginFeature
import ru.thstrio.cinemaforfriend.ui.login.mvi.viewmodel.LoginViewModel
import ru.thstrio.cinemaforfriend.ui.util.getMyColor
import ru.thstrio.cinemaforfriend.ui.util.getSelectColor

class LoginActivity : ObservableSourceActivity<LoginUIEvent>(),
    Consumer<LoginViewModel> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActiviteBinding(this, LoginFeature()).setup(this)
        setContentView(R.layout.activity_login)
        setupView()
    }

    private fun setupView() {
        login_btn_sign_in.setOnClickListener { onNext(SelectSingIn) }
        login_btn_sign_up.setOnClickListener { onNext(SelectSingUp) }
    }

    override fun accept(model: LoginViewModel?) {
        model?.let {
            if (login_btn_sign_in.tag != model.isModeSignIn) {
                login_btn_sign_in.tag == model.isModeSignIn
                changeMode(model.isModeSignIn)
            }
        }
    }

    private fun changeMode(modeSignIn: Boolean) {
        login_btn_sign_in.setTextColor(
            getSelectColor(
                modeSignIn,
                R.color.link_active,
                R.color.link_notactive
            )
        )
        login_btn_sign_up.setTextColor(
            getSelectColor(
                !modeSignIn,
                R.color.link_active,
                R.color.link_notactive
            )
        )
    }
}
