package com.example.risingproject.src.login.emailsignin.models

import com.example.risingproject.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class EmailSigninResponse(
    @SerializedName("result") val result: Result
):BaseResponse()
data class Result(
    @SerializedName("userId") val userId: Int,
    @SerializedName("jwt") val jwt: String
)