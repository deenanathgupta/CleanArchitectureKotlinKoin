package com.android.cardinalhealthtask.domain.model

data class ErrorModel(
    val message: String?,
    val code: Int?,
    var errorStatus: ErrorStatus
) {

    constructor(message: String?, errorStatus: ErrorStatus) : this(message, null, errorStatus)

    enum class ErrorStatus {
        NO_CONNECTION,
        BAD_RESPONSE,
        TIMEOUT,
        NOT_DEFINED,
        UNAUTHORIZED
    }
}