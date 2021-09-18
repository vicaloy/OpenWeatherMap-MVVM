package com.android.example.github.common

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ResultAction<out T : Any> {

    data class Success<out T : Any>(val data: T) : ResultAction<T>()
    data class Error(val exception: Exception) : ResultAction<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}