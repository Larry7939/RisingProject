package com.example.risingproject.src.main.detail

import com.example.risingproject.src.main.detail.models.DetailHousesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailRetrofitInterface {
    @GET("/app/posts/housewarms/{houseWarmId}")
    fun getDetail(@Path("houseWarmId")houseWarmId:Int): Call<DetailHousesResponse>
}