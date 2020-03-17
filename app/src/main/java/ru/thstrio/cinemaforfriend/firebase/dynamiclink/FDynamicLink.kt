package ru.thstrio.cinemaforfriend.firebase.dynamiclink

import android.app.Activity
import android.content.Context
import android.content.Intent

import android.net.Uri
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import io.reactivex.Maybe


    fun FDynamicLink(intent: Intent, activity: Activity): Maybe<String> {
        return Maybe.create { emitter ->

            Firebase.dynamicLinks
                .getDynamicLink(intent)
                .addOnSuccessListener(activity) { pendingDynamicLinkData ->
                    // Get deep link from result (may be null if no link is found)
                    var deepLink: Uri? = null
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link
                        pendingDynamicLinkData.extensions
                    }
                    deepLink?.let {
                        emitter.onSuccess(deepLink.toString())
                    }
                    emitter.onComplete()
                }
                .addOnFailureListener(activity) { e -> emitter.onError(e) }
        }


    }
