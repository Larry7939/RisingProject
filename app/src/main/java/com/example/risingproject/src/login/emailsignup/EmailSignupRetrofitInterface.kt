package com.example.risingproject.src.login.emailsignup

import com.example.risingproject.src.login.emailsignup.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EmailSignupRetrofitInterface {
    @POST("/app/sign-up")
    fun postSignUp(@Body params: EmailSignupRequest): Call<EmailSignupResponse>
    @GET("/app/emails")
    fun getEmailOverlap(@Query("email")email:String): Call<EmailOverlapResponse>
    @GET("/app/nicknames")
    fun getNicknameOverlap(@Query("nickName")nickName:String): Call<NicknameOverlapResponse>
}