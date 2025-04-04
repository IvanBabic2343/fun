package test.app.data.utils

import retrofit2.HttpException
import java.io.IOException

object ApiErrorHandler {
    fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is HttpException -> {
                when (exception.code()) {
                    400 -> "Bad request. Please try again."
                    401 -> "Unauthorized access. Please check your credentials."
                    403 -> "You don't have permission to view this content."
                    404 -> "Resource not found."
                    500 -> "Server error. Please try again later."
                    else -> "Something went wrong. Error code: ${exception.code()}"
                }
            }

            is IOException -> "Network error. Please check your connection."
            else -> "Unexpected error: ${exception.localizedMessage}"
        }
    }
}
