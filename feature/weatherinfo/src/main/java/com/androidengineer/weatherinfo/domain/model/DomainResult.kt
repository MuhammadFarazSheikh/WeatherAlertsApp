package com.androidengineer.weatherinfo.domain.model

sealed class DomainResult <out T> {
    data class Success<out T>(val data: T) : DomainResult<T>()
    data class Error(val message: String) : DomainResult<Nothing>()
    object Loading : DomainResult<Nothing>()
}