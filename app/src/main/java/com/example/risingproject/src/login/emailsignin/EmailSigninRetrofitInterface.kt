package com.example.risingproject.src.login.emailsignin

import com.example.risingproject.src.login.emailsignin.models.EmailSigninRequest
import com.example.risingproject.src.login.emailsignin.models.EmailSigninResponse
import com.example.risingproject.src.login.emailsignup.models.EmailSignupRequest
import com.example.risingproject.src.login.emailsignup.models.EmailSignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailSigninRetrofitInterface {
    @POST("/app/login")
    fun postSignin(@Body params: EmailSigninRequest): Call<EmailSigninResponse>
}