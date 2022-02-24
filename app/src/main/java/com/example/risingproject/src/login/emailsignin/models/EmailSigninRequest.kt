package com.example.risingproject.src.login.emailsignin.models

import com.google.gson.annotations.SerializedName

data class EmailSigninRequest(
    @SerializedName("email") val email: String,
    @SerializedName("passWord") val password: String
)
