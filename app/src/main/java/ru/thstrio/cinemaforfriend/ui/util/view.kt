package ru.thstrio.cinemaforfriend.ui.util

import android.view.View

fun View.toVisibility(isVisibly: Boolean) {
    this.visibility = if (isVisibly) View.VISIBLE else View.GONE
}