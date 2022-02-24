package com.example.risingproject.src.login.emailsignin

import com.example.risingproject.src.login.emailsignin.models.EmailSigninResponse

interface EmailSigninView {
    fun onPostSignInSuccess(response: EmailSigninResponse)
    fun onPostSignInFailure(message: String)
}