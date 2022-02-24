package com.example.risingproject.src.login.kakaosignup.models
import com.example.risingproject.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class KakaoSignupResponse (
    @SerializedName("result") val result: Result
):BaseResponse()
data class Result(
    @SerializedName("userId") val userId: Int,
    @SerializedName("jwt") val jwt: String
)