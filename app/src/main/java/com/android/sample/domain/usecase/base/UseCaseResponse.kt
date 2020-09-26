package com.android.sample.domain.usecase.base

import com.android.sample.domain.model.ErrorModel


interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(errorModel: ErrorModel?)
}

