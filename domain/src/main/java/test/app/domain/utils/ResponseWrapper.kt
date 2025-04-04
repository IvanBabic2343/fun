package test.app.domain.utils

sealed class ResponseWrapper<out T> {
    data class Success<T>(val data: T) : ResponseWrapper<T>()
    data class Error(val message: String) : ResponseWrapper<Nothing>()
}
