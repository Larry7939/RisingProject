package com.example.risingproject.src.main.detail

import android.util.Log
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.src.main.detail.models.DetailHousesResponse
import com.example.risingproject.src.main.popularity.PopRetrofitInterface
import com.example.risingproject.src.main.popularity.models.PopLookupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailService(val view: DetailActivityView) {
    fun tryDetail(houseWarmId:Int){
        val detailRetrofitInterface = ApplicationClass.sRetrofit.create(
            DetailRetrofitInterface::class.java)
        detailRetrofitInterface.getDetail(houseWarmId).enqueue(object :
            Callback<DetailHousesResponse> {
            override fun onResponse(call: Call<DetailHousesResponse>, response: Response<DetailHousesResponse>) {
                view.onGetDetailSuccess(response.body() as DetailHousesResponse)
                Log.d("Success","-----집들이 상세조회 통신성공-----")
                Log.d("code",response.code().toString())
            }
            override fun onFailure(call: Call<DetailHousesResponse>, t: Throwable) {
                view.onGetDetailFailure(t.message ?: "집들이 상세조회 통신 오류")
                Log.d("Fail","-----집들이 통신실패-----")
            }
        })
    }
}