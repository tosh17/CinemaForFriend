package ru.thstrio.cinemaforfriend.ui.util

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getMyColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)
fun Context.getSelectColor(isFirst: Boolean, @ColorRes id1: Int, @ColorRes id2: Int) =
    ContextCompat.getColor(this, if (isFirst) id1 else id2)

