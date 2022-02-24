package com.example.risingproject.src.main.detail
import com.example.risingproject.src.main.detail.models2.ProductResponse
import retrofit2.Call
import retrofit2.http.*

interface ProductRetrofitInterface {
    @Headers("x-access-token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjUsImlhdCI6MTYyOTgwMDkzOCwiZXhwIjoxNjYxMzM2OTM4LCJzdWIiOiJ1c2VySW5mbyJ9.tOQI2MK2jdBABbk663aiu9eccgYCz666vYp_PkgrPX8")
    @GET("/app/products/users/{userId}")
    fun getProduct(@Path("userId")userId:Int,@Query("productId")productId:Int): Call<ProductResponse>
}