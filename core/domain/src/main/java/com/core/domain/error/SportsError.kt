package com.core.domain.error

sealed class SportsError : Throwable() {
    object Network : SportsError()
    object EmptyResult : SportsError()
    data class Unknown(override val cause: Throwable) : SportsError()
}