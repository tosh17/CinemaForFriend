package ru.thstrio.cinemaforfriend.firebase.auth

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import io.reactivex.*
import java.util.concurrent.TimeUnit

class FAuth : IAuth {
    val mAuth = FirebaseAuth.getInstance()
    val currentUser
        get() = mAuth.currentUser

    override fun createUserFromEmail(email: String, password: String): Observable<Boolean> {
        return Observable.create { emmiter ->
            Log.d("TOSHAUTH", "Observable.create ")
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) emmiter.onNext(task.isSuccessful)
                    else emmiter.onError(Throwable(task.exception?.message))
                    emmiter.onComplete()
                }
        }

        fun getInfo() =
            currentUser?.displayName


    }

    fun signInWithCredential(
        credential: AuthCredential,
        emmiter: ObservableEmitter<Boolean>
    ) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) emmiter.onNext(true)
                else emmiter.onError(Throwable(task.exception?.message))
                emmiter.onComplete()
            }
    }

    override fun loginByGoogle(token: String?) =
        Observable.create<Boolean> { emmiter ->
            val credential = GoogleAuthProvider.getCredential(token, null)
            signInWithCredential(credential, emmiter)
        }

    override fun verifyPhoneNumber(phoneNumber: String) =
        Observable.create<Boolean> { emmiter ->
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                TaskExecutors.MAIN_THREAD,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        signInWithCredential(credential, emmiter)
                    }

                    override fun onVerificationFailed(exception: FirebaseException) {
                        emmiter.onError(Throwable(exception.message))
                    }

                })
        }


}