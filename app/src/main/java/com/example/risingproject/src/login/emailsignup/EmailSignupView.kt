package com.example.risingproject.src.login.emailsignup

import com.example.risingproject.src.login.emailsignup.models.EmailOverlapResponse
import com.example.risingproject.src.login.emailsignup.models.EmailSignupResponse
import com.example.risingproject.src.login.emailsignup.models.NicknameOverlapResponse

interface EmailSignupView {
    fun onPostSignUpSuccess(response: EmailSignupResponse)
    fun onPostSignUpFailure(message: String)

    fun onGetOverlapSuccess(response: EmailOverlapResponse)
    fun onGetOverlapFailure(message: String)

    fun onGetNicknameOverlapSuccess(response: NicknameOverlapResponse)
    fun onGetNicknameOverlapFailure(message: String)
}