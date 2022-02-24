package com.example.risingproject.src.main.popularity

import android.util.Log
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.src.login.emailsignup.emailOverlaped
import com.example.risingproject.src.main.popularity.models.PopLookupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopService(val view:PopularityFragmentView) {
    fun tryPop(categoryId:Int){
        val popRetrofitInterface = ApplicationClass.sRetrofit.create(
            PopRetrofitInterface::class.java)
        popRetrofitInterface.getPopLookup(categoryId).enqueue(object :
            Callback<PopLookupResponse> {
            override fun onResponse(call: Call<PopLookupResponse>, response: Response<PopLookupResponse>) {
                view.onGetPopSuccess(response.body() as PopLookupResponse)
                Log.d("Success","-----인기탭 통신성공-----")
                Log.d("code",response.code().toString())
            }
            override fun onFailure(call: Call<PopLookupResponse>, t: Throwable) {
                view.onGetPopFailure(t.message ?: "인기탭 통신 오류")
                Log.d("Fail","-----통신실패-----")
            }
        })
    }
}