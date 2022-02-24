package com.example.risingproject.src.login.emailsignup.models
import com.google.gson.annotations.SerializedName

data class EmailOverlapRequest (
        @SerializedName("email") val email: String
)