package com.picpay.desafio.android.presentation.utils

import android.view.View

object ViewExtensions {
    @JvmStatic
    fun Boolean.toVisibility(isInvisible: Boolean = false): Int {
        return when {
            this -> {
                View.VISIBLE
            }
            isInvisible -> {
                View.INVISIBLE
            }
            else -> {
                View.GONE
            }
        }
    }
}