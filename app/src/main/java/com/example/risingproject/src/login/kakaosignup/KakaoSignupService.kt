package com.example.risingproject.src.login.kakaosignup

import android.util.Log
import com.example.risingproject.config.ApplicationClass

import com.example.risingproject.src.login.kakaosignup.models.KakaoSignupRequest
import com.example.risingproject.src.login.kakaosignup.models.KakaoSignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class KakaoSignupService(val view:KakaoSignupView) {
    fun tryKakaoPostSignup(postSignupRequest:KakaoSignupRequest){
        val kakaoRetrofitInterface = ApplicationClass.sRetrofit.create(KakaoRetrofitInterface::class.java)
        kakaoRetrofitInterface.postKakaoSignup(postSignupRequest).enqueue(object:
            Callback<KakaoSignupResponse>{
            override fun onResponse(
                call: Call<KakaoSignupResponse>, response: Response<KakaoSignupResponse>) {
                if(response.body() != null) {
                    view.onKakaoPostSignUpSuccess(response.body() as KakaoSignupResponse)
                    Log.d("Success", "-----카카오 access token 통신성공-----")
                }
            }

            override fun onFailure(call: Call<KakaoSignupResponse>, t: Throwable) {
                view.onKakaoPostSignUpFailure(t.message ?: "통신 오류")
                Log.d("Fail","-----통신실패-----")
            }


        }
        )
    }
}