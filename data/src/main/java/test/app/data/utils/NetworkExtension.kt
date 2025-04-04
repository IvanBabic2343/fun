import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import test.app.data.utils.ApiErrorHandler
import test.app.domain.utils.ResponseWrapper
import java.io.InterruptedIOException
import java.net.ConnectException

inline fun <T : Any> safeApiCallWithRetry(
    maxRetries: Int = 3,
    delayMillis: Long = 500L,
    crossinline apiCall: suspend () -> T
): Flow<ResponseWrapper<T>> = flow {
    repeat(maxRetries) { attempt ->
        try {
            emit(ResponseWrapper.Success(apiCall()))
            return@flow
        } catch (e: Exception) {
            val isError = e is ConnectException || e is InterruptedIOException
            val isLastAttempt = attempt == maxRetries - 1

            if (!isError || isLastAttempt) {
                emit(ResponseWrapper.Error(ApiErrorHandler.getErrorMessage(e)))
                return@flow
            } else {
                delay(delayMillis)
            }
        }
    }
}.flowOn(Dispatchers.IO)
