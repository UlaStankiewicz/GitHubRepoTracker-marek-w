package pl.mwaszczuk.githubrepotracker.domain.base

import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRepository {

    suspend fun <T> domainStateOf(block: suspend () -> T): DomainState<T> {
        return try {
            DomainState.Success(block())
        } catch (e: Throwable) {
            handleError(e)
        }
    }

    private fun handleError(throwable: Throwable): DomainState.Error {
        return when (throwable) {
            is UnknownHostException -> DomainState.Error.ApiError(
                errorMessage = "Please check your internet connection"
            )
            is SocketTimeoutException -> DomainState.Error.ApiError(
                errorMessage = "Looks like the server is taking to long to respond"
            )
            is ApiException -> DomainState.Error.ApiError(
                errorMessage = throwable.message
            )
            else -> DomainState.Error(
                errorMessage = "Oops, something went wrong. Please try again later."
            )
        }
    }
}
