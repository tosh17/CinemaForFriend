package ru.thstrio.cinemaforfriend.firebase.auth

import io.reactivex.Observable

interface IAuth {
    fun createUserFromEmail(email: String, password: String): Observable<Boolean>
    fun loginByGoogle(token: String?): Observable<Boolean>
    fun verifyPhoneNumber(phoneNumber:String): Observable<Boolean>
}