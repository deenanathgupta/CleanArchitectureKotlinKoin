package com.android.cardinalhealthtask.domain.usecase.base

import com.android.cardinalhealthtask.domain.model.ErrorModel


interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(errorModel: ErrorModel?)
}

