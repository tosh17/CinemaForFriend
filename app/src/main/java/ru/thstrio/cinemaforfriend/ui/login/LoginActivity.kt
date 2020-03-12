package ru.thstrio.cinemaforfriend.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.get
import ru.thstrio.cinemaforfriend.MainActivity
import ru.thstrio.cinemaforfriend.R
import ru.thstrio.cinemaforfriend.api.tmdb.pojo.ListCinemaPojo
import ru.thstrio.cinemaforfriend.api.tmdb.service.TmdbServise
import ru.thstrio.cinemaforfriend.firebase.auth.FAuth
import ru.thstrio.cinemaforfriend.ui.login.mvi.event.ActorLoginUIEvent.*
import ru.thstrio.cinemaforfriend.ui.login.mvi.event.LoginUIEvent
import ru.thstrio.cinemaforfriend.ui.login.mvi.event.SimpleLoginUIEvent.*
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginActorFeature
import ru.thstrio.cinemaforfriend.ui.login.mvi.feature.LoginFeature
import ru.thstrio.cinemaforfriend.ui.login.mvi.news.LoginNewsListener
import ru.thstrio.cinemaforfriend.ui.login.mvi.viewmodel.LoginViewModel
import ru.thstrio.cinemaforfriend.ui.util.getSelectColor
import ru.thstrio.cinemaforfriend.ui.util.toVisibility
import java.util.concurrent.TimeUnit

class LoginActivity : ObservableSourceActivity<LoginUIEvent>(),
    Consumer<LoginViewModel> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val feature = get<LoginFeature>()

        LoginActiviteBinding(this, feature, LoginActorFeature(null), LoginNewsListener(this)).setup(
            this
        )
        setContentView(R.layout.activity_login)
        setupView()
        test()

    }

    private fun test() {
        val serv = TmdbServise.instance.createTmdbApi().fingCinema(cinema = "паразиты",page=2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                Log.d("Retrofit  pp", list.totalResults.toString()) },
                { error -> Log.d("Retrofit", error.message) })


    }

    private fun setupView() {
        login_btn_sign_in.setOnClickListener { onNext(SelectSingIn) }
        login_btn_sign_up.setOnClickListener { onNext(SelectSingUp) }

        login_enter.setOnClickListener { onNext(CreateUserEmail) }
        login_btn_google.setOnClickListener { loginByGoogle() }
        login_btn_fb.setOnClickListener { //todo verify phone number
        }
    }

    override fun accept(model: LoginViewModel?) {
        model?.let {
            if (model.isLoginComplete) {
                loginComplite()
                return
            }
            if (login_btn_sign_in.tag != model.isModeSignIn) {
                login_btn_sign_in.tag = model.isModeSignIn
                changeMode(model.isModeSignIn)

            }
        }
    }

    private fun changeMode(modeSignIn: Boolean) {
        login_btn_sign_in.setTextColor(getTextColor(modeSignIn))
        login_btn_sign_up.setTextColor(getTextColor(!modeSignIn))
        login_text_input_layout_password2.toVisibility(!modeSignIn)
    }

    private fun getTextColor(isActive: Boolean) = getSelectColor(
        isActive, R.color.link_active, R.color.link_notactive
    )

    fun loginComplite() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private val RC_SIGN_IN = 10001
    fun loginByGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account?.let { account ->
                    onNext(LoginByGoogle(account.idToken))
                }
            } catch (e: ApiException) {
                //todo onnext error google login
            }
        }
    }

}


