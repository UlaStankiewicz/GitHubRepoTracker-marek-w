package pl.mwaszczuk.githubrepotracker.domain.base

sealed class ApiException : Throwable() {
    abstract override val message: String

    class ServiceUnavailableException : ApiException() {
        override val message: String = "We're sorry, but the service is currently unavailable. Please try again later."
    }

    class UnexpectedException : ApiException() {
        override val message: String = "Oops, something went wrong. Please try again later."
    }
}
