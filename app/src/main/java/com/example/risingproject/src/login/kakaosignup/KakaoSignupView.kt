package com.example.risingproject.src.login.kakaosignup

import com.example.risingproject.src.login.emailsignup.models.EmailSignupResponse
import com.example.risingproject.src.login.kakaosignup.models.KakaoSignupResponse

interface KakaoSignupView {
    fun onKakaoPostSignUpSuccess(response: KakaoSignupResponse)

    fun onKakaoPostSignUpFailure(message: String)
}