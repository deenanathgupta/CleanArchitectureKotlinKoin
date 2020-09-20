package com.android.cardinalhealthtask.domain.exception

import com.android.cardinalhealthtask.domain.model.ErrorModel
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class ApiErrorHandle {

    fun traceErrorException(throwable: Throwable?): ErrorModel {
        val errorModel: ErrorModel? = when (throwable) {

            is HttpException -> {
                if (throwable.code() == 401) {
                    ErrorModel(
                        throwable.message(),
                        throwable.code(),
                        ErrorModel.ErrorStatus.UNAUTHORIZED
                    )
                } else {
                    getHttpError(throwable.response()?.errorBody())
                }
            }

            // handle api call timeout error
            is SocketTimeoutException -> {
                ErrorModel(
                    throwable.message,
                    ErrorModel.ErrorStatus.TIMEOUT
                )
            }

            // handle connection error
            is IOException -> {
                ErrorModel(
                    throwable.message,
                    ErrorModel.ErrorStatus.NO_CONNECTION
                )
            }
            else -> null
        }
        return errorModel ?: ErrorModel(
            "No Defined Error!",
            0,
            ErrorModel.ErrorStatus.BAD_RESPONSE
        )
    }

    private fun getHttpError(body: ResponseBody?): ErrorModel {
        return try {
            // use response body to get error detail
            ErrorModel(
                body.toString(),
                400,
                ErrorModel.ErrorStatus.BAD_RESPONSE
            )
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(
                message = e.message,
                errorStatus = ErrorModel.ErrorStatus.NOT_DEFINED
            )
        }

    }
}