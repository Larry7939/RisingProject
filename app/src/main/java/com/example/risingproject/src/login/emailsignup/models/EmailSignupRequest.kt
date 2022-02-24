package com.example.risingproject.src.login.emailsignup.models
import com.google.gson.annotations.SerializedName

data class EmailSignupRequest(
    @SerializedName("email") val email: String,
    @SerializedName("passWord") val password: String,
    @SerializedName("passWordCheck") val confirmPassword: String,
    @SerializedName("nickName") val nickname: String
)
