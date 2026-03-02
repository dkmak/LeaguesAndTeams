package com.core.common.ui

import com.core.domain.error.SportsError
import java.io.IOException

fun Throwable.toUserMessage(): String =
    when (this) {
        is SportsError.Network ->
            "Please check your internet connection and try again."
        is SportsError.EmptyResult -> {
            "No results were found."
        }
        else ->
            "An unexpected error occurred."
    }