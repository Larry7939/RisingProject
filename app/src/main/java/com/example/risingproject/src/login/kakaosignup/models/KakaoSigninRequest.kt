package com.example.risingproject.src.login.kakaosignup.models
import com.google.gson.annotations.SerializedName

data class KakaoSignupRequest (
     @SerializedName("accessToken") val accessToken: String,
 )