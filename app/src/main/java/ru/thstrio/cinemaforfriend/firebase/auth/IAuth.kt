package ru.thstrio.cinemaforfriend.firebase.auth

import io.reactivex.Observable

interface IAuth {
    fun createUserFromEmail(email: String, password: String): Observable<Boolean>
}