package id.indocyber.common

sealed class AppResponse<T>(val data : (T)?) {
    companion object {
        fun <T> success(value: T): AppResponse<T> = AppResponseSuccess(value)
        fun <T> error(exception: Throwable?): AppResponse<T> = AppResponseError(exception)
        fun <T> loading() = AppResponseLoading<T>()
    }
    class AppResponseSuccess<T>(value: T) : AppResponse<T>(value)
    class AppResponseError<T>(val e: Throwable?) : AppResponse<T>(null)
    class AppResponseLoading<T> : AppResponse<T>(null)
}