package com.example.risingproject.src.main.detail

import com.example.risingproject.src.main.detail.models2.ProductResponse

interface ProductActivityView {
    fun onGetProductSuccess(response: ProductResponse)
    fun onGetProductFailure(message: String)
}