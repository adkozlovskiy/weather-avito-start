package com.kozlovskiy.avitoweather.common

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {
        inline fun <T> catch(f: () -> T): Result<T> {
            return try {
                Success(f())
            } catch (ex: Exception) {
                Error(ex)
            }
        }
    }
}