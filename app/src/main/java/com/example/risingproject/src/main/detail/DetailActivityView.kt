package com.example.risingproject.src.main.detail

import com.example.risingproject.src.main.detail.models.DetailHousesResponse

interface DetailActivityView {
    fun onGetDetailSuccess(response: DetailHousesResponse)
    fun onGetDetailFailure(message: String)
}