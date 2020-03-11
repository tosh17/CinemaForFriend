package ru.thstrio.cinemaforfriend.firebase.auth

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.*

class FAuth : IAuth {
     val mAuth = FirebaseAuth.getInstance()
     val currentUser
        get() = mAuth.currentUser

   override fun createUserFromEmail(email: String, password: String): Observable<Boolean> {
        return Observable.create<Boolean> {emmiter->
            Log.d("TOSHAUTH", "Observable.create ")
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if(task.isSuccessful) emmiter.onNext(task.isSuccessful)
                    else emmiter.onError(Throwable(task.exception?.message))
                    emmiter.onComplete()
                }
        }

        fun getInfo() =
            currentUser?.displayName




    }
}