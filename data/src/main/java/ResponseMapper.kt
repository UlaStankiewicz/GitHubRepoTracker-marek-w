package pl.mwaszczuk.githubrepotracker.network

import pl.mwaszczuk.githubrepotracker.domain.base.ApiException
import retrofit2.Response

class ResponseMapper {

    fun <T> map(response: Response<T>): T {
        val responseBody = response.body()
        val errorBody = response.errorBody()
        if (errorBody != null) {
            // I would probably send a non-fatal event to crashlytics here
        }
        return when {
            response.isSuccessful && responseBody != null -> responseBody
            response.code() in (500..599) -> {
                throw ApiException.ServiceUnavailableException()
            }
            response.code() == 404 -> {
                throw ApiException.NotFound()
            }
            else -> {
                throw ApiException.UnexpectedException()
            }
        }
    }
}
