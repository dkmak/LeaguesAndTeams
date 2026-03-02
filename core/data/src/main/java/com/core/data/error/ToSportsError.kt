package com.core.data.error

import com.core.domain.error.SportsError
import okio.IOException

internal fun Throwable.toSportsError(): SportsError{
    return when (this) {
      is IOException -> SportsError.Network
      is SportsError.EmptyResult -> {this}
      else -> SportsError.Unknown(this)
    }
}