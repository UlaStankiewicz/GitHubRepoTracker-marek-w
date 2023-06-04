package pl.mwaszczuk.githubrepotracker.domain.base

sealed class DomainState<out T> {
    open class Error(val errorMessage: String) : DomainState<Nothing>() {
        class ApiError(errorMessage: String) : Error(errorMessage)
    }
    class Success<T>(val data: T) : DomainState<T>()

    fun isError() = this is Error

    override fun hashCode(): Int {
        return when (this) {
            is Error.ApiError -> 1
            is Error -> 2
            is Success -> 3
        }
    }

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other.hashCode()
    }
}
