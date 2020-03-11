package ru.thstrio.cinemaforfriend.ui.util

import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.util.Patterns


fun isValidEmail(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

fun isValidPhoneNumber(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        PhoneNumberUtils.isGlobalPhoneNumber(target.toString())
    }
}
