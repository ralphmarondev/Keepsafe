package com.ralphmarondev.keepsafe.core.domain.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()

    data class Error(
        val message: String? = null,
        val throwable: Throwable? = null
    ) : Result<Nothing>()

    val isSuccess: Boolean get() = this is Success<*>
    val isError: Boolean get() = this is Error
}