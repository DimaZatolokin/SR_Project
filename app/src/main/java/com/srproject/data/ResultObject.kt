package com.srproject.data

sealed class ResultObject<out T> {
    class Success<T>(val data: T): ResultObject<T>()
    class Error(val cause: GeneralCauseError) : ResultObject<Nothing>()
}

abstract class GeneralCauseError(message: String) : Throwable(message)

class EmptyError(): GeneralCauseError("")