package com.android.sample.domain.exception

import com.android.sample.domain.model.ErrorModel
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.HttpsURLConnection

class ApiErrorHandle {

    fun traceErrorException(throwable: Throwable?): ErrorModel {
        val errorModel: ErrorModel? = when (throwable) {
            is HttpException -> {
                if (throwable.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
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
                HttpsURLConnection.HTTP_BAD_REQUEST,
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