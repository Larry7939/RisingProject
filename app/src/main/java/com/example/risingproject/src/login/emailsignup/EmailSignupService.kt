package com.example.risingproject.src.login.emailsignup

import android.util.Log
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.src.login.emailsignup.models.EmailOverlapResponse
import com.example.risingproject.src.login.emailsignup.models.EmailSignupRequest
import com.example.risingproject.src.login.emailsignup.models.EmailSignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.risingproject.src.login.emailsignup.EmailSignup
import com.example.risingproject.src.login.emailsignup.models.NicknameOverlapResponse

class EmailSignupService(val view:EmailSignupView) {
    fun tryPostSignUp(postSignUpRequest: EmailSignupRequest){
        val emailSignupRetrofitInterface = ApplicationClass.sRetrofit.create(EmailSignupRetrofitInterface::class.java)
        emailSignupRetrofitInterface.postSignUp(postSignUpRequest).enqueue(object :
            Callback<EmailSignupResponse> {
            override fun onResponse(call: Call<EmailSignupResponse>, response: Response<EmailSignupResponse>) {
                view.onPostSignUpSuccess(response.body() as EmailSignupResponse)
                Log.d("Success","-----통신성공-----")
                Log.d("code",response.code().toString())
            }
            override fun onFailure(call: Call<EmailSignupResponse>, t: Throwable) {
                view.onPostSignUpFailure(t.message ?: "통신 오류")
                Log.d("Fail","-----통신실패-----")
            }
        })
    }
    fun tryEmailOverlap(email:String){
        val emailSignupRetrofitInterface = ApplicationClass.sRetrofit.create(EmailSignupRetrofitInterface::class.java)
        emailSignupRetrofitInterface.getEmailOverlap(email).enqueue(object :
            Callback<EmailOverlapResponse> {
            override fun onResponse(call: Call<EmailOverlapResponse>, response: Response<EmailOverlapResponse>) {
                view.onGetOverlapSuccess(response.body() as EmailOverlapResponse)
                emailOverlaped = response.body()!!.code==3001
                Log.d("Success","-----이메일 중복 통신성공-----")
                Log.d("code",response.code().toString())
            }
            override fun onFailure(call: Call<EmailOverlapResponse>, t: Throwable) {
                view.onGetOverlapFailure(t.message ?: "이메일 중복 통신 오류")
                Log.d("Fail","-----통신실패-----")
            }
        })
    }

    fun tryNicknameOverlap(nickname:String){
        val emailSignupRetrofitInterface = ApplicationClass.sRetrofit.create(EmailSignupRetrofitInterface::class.java)
        emailSignupRetrofitInterface.getNicknameOverlap(nickname).enqueue(object :
            Callback<NicknameOverlapResponse> {
            override fun onResponse(call: Call<NicknameOverlapResponse>, response: Response<NicknameOverlapResponse>) {
                view.onGetNicknameOverlapSuccess(response.body() as NicknameOverlapResponse)
                nicknameOverlaped = response.body()!!.code==3002
                Log.d("Success","-----닉네임 중복 통신성공-----")
                Log.d("code",response.code().toString())
            }
            override fun onFailure(call: Call<NicknameOverlapResponse>, t: Throwable) {
                view.onGetNicknameOverlapFailure(t.message ?: "닉네임 중복 통신 오류")
                Log.d("Fail","-----통신실패-----")
            }
        })
    }
}