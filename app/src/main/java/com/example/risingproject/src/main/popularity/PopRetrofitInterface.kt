package com.example.risingproject.src.main.popularity

import com.example.risingproject.src.main.popularity.models.PopLookupResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PopRetrofitInterface {
    @GET("/app/posts/popular")
    fun getPopLookup(@Query("categoryId")categoryId:Int): Call<PopLookupResponse>
}