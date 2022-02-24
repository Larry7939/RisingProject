package com.example.risingproject.src.login.emailsignup.models
import com.google.gson.annotations.SerializedName

data class EmailSignupResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)