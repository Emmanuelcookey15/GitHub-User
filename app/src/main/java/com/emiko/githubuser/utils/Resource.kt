package com.emiko.githubuser.utils

sealed class Resource <T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String? = null, cause: Throwable? = null, data: T? = null) : Resource<T>(data, cause?.message?:message)
    class Loading<T> : Resource<T>()
}