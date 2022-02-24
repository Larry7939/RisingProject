package com.example.risingproject.src.main.detail

import android.util.Log
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.src.main.detail.models2.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductService(val view: ProductActivityView) {
    fun tryProduct(userId:Int,productId:Int){
        val productRetrofitInterface = ApplicationClass.sRetrofit.create(
            ProductRetrofitInterface::class.java)
        productRetrofitInterface.getProduct(userId,productId).enqueue(object :
            Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                view.onGetProductSuccess(response.body() as ProductResponse)
                Log.d("Success","-----특정 상품 조회 통신성공-----")
                Log.d("code",response.code().toString())
            }
            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                view.onGetProductFailure(t.message ?: "특정 상품 조회 통신 오류")
                Log.d("Fail","-----통신실패-----")
            }
        })
    }
}