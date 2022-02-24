package com.example.risingproject.src.login.emailsignin

import android.util.Log
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.src.login.emailsignin.models.EmailSigninRequest
import com.example.risingproject.src.login.emailsignin.models.EmailSigninResponse
import com.example.risingproject.src.login.emailsignup.models.EmailSignupRequest
import com.example.risingproject.src.login.emailsignup.models.EmailSignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailSigninService(val view:EmailSigninView) {
    fun tryPostSignIn(postSignInRequest: EmailSigninRequest){
        val emailSigninRetrofitInterface = ApplicationClass.sRetrofit.create(EmailSigninRetrofitInterface::class.java)
        emailSigninRetrofitInterface.postSignin(postSignInRequest).enqueue(object :
            Callback<EmailSigninResponse> {
            override fun onResponse(call: Call<EmailSigninResponse>, response: Response<EmailSigninResponse>) {
                if(response.body() != null){
                Log.d("Success","-----통신성공-----")
                view.onPostSignInSuccess(response.body() as EmailSigninResponse)
                }
            }
            override fun onFailure(call: Call<EmailSigninResponse>, t: Throwable) {
                view.onPostSignInFailure(t.message ?: "통신 오류")
                Log.d("Fail","-----통신실패-----")
            }
        })
    }
}