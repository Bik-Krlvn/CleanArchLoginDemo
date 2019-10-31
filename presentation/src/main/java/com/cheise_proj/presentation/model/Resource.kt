package com.cheise_proj.presentation.model

/**
 * Resource wrapper for live data
 * @property Resource
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        /**
         * loading state
         */
        fun<T> loading(): Resource<T> {
            return Resource(
                Status.LOADING,
                null,
                null
            )
        }

        /**
         * @param data provide data type
         * @param message default null
         * @return Resource<T> return wrapper result
         */
        fun <T> success(data: T?, message: String? = null): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                message
            )
        }

        /**
         * @param message provide an error message
         * @param data default null
         * @return Resource<T> returns wrapper result
         */
        fun <T> error(message: String?, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                message
            )
        }
    }
}

/**
 * Loading state
 * @property Status
 */
enum class Status {
    LOADING, SUCCESS, ERROR
}