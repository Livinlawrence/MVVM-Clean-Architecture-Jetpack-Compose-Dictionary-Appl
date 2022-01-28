package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data, null)
    class Error<T>(data: T? = null,message: String?) : Resource<T>(data, message)
    class Success<T>(data: T? = null) : Resource<T>(data)
}