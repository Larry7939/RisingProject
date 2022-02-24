package com.example.risingproject.src.login.kakaosignup


import android.util.Log
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.src.login.kakaosignup.models.KakaoSignupRequest
import com.example.risingproject.src.login.kakaosignup.models.KakaoSignupResponse


class KakaoSignup(accessToken: String):KakaoSignupView {
    private var accesstoken = accessToken
    fun PostAccessToken(){
        //액세스 토큰이 널이 아니면 서버로 전송해줘야함. 그러면 서버에서는 jwt를 보내줄 것임.
        if(accesstoken != null || accesstoken != ""){
            KakaoSignupService(this).tryKakaoPostSignup(KakaoSignupRequest(accesstoken))
        }
    }
    override fun onKakaoPostSignUpSuccess(response: KakaoSignupResponse) {
        response.message?.let {
            if(response.code == 1000){
                //jwt수신완료, jwt받았으면 따로 저장해놔야함.
                Log.d("jwt수신여부","jwt 수신 성공 -> ${response.result.jwt}")
                Log.d("message","${response.message}")
                ApplicationClass.sSharedPreferences.edit().putString("J-ACCESS-TOKEN",response.result.jwt).commit()
            }
            else {
                Log.d("jwt수신여부","jwt 수신 실패-> ${response.code}")
                Log.d("message","${response.message}")
            }
        }

    }

    override fun onKakaoPostSignUpFailure(message: String) {

    }

}

