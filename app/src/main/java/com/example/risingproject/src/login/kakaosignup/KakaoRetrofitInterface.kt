package com.example.risingproject.src.login.kakaosignup

import com.example.risingproject.src.login.kakaosignup.models.KakaoSignupRequest
import com.example.risingproject.src.login.kakaosignup.models.KakaoSignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoRetrofitInterface {
    @POST("/app/login/kakao")
    fun postKakaoSignup(@Body params:KakaoSignupRequest) : Call<KakaoSignupResponse>
}