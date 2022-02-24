package com.example.risingproject.src.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.risingproject.R
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.config.BaseActivity
import com.example.risingproject.databinding.ActivityLoginBinding
import com.example.risingproject.src.login.emailsignin.EmailSignin
import com.example.risingproject.src.login.emailsignup.EmailSignup
import com.example.risingproject.src.login.kakaosignup.KakaoAddInfo
import com.example.risingproject.src.login.kakaosignup.KakaoSignup
import com.example.risingproject.src.main.MainActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility


class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //이메일로 로그인
        binding.loginEmailSignin.setOnClickListener {
            startActivity(Intent(this,EmailSignin::class.java))
            overridePendingTransition(0,0)
            finish()
        }
        //이메일로 회원가입
        binding.loginEmailSignup.setOnClickListener {
            startActivity(Intent(this, EmailSignup::class.java))
            overridePendingTransition(0,0)
            finish()
        }
        //카카오로 계속하기
        binding.loginKakao.setOnClickListener {
            //카카오와 연결중입니다. 로딩창 띄우고 try로 소셜로그인/회원가입한 다음에 response오면 dismiss하는걸로!
            showLoadingDialog(this,3)
            KakaoSdk.init(this, getString(R.string.kakao_app_key))
            var keyHash = Utility.getKeyHash(this)
            Log.d("KEY_HASH", keyHash)
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    dismissLoadingDialog()
                    Log.e(ContentValues.TAG, "로그인 실패", error)
                } else if (token != null) {
                    dismissLoadingDialog()
                    Log.i(ContentValues.TAG, "로그인 성공, 액세스 토큰 -> ${token.accessToken}")
                    val kakaoSignup =KakaoSignup(token.accessToken)
                    kakaoSignup.PostAccessToken()//액세스 토큰 보내기
                    //여기에서 엑세스 토큰을 받았는데, jwt가 없으면, 이 엑세스 토큰을 서버에 넘겨주는 과정 필요
                    //이 엑세스 토큰을 서버에 넘겨주면, response로 jwt를 받는다. jwt가 있으면 자동 로그인시킨다.
                    //카카오용 jwt를 따로 만들어서 저장하고, jwt 검사해서 만약 없으면, 회원가입 창으로 넘어가고,
                    //회원가입 창에서는 유저 정보를 입력해서 넘겨준다.(별명 - 이건 서버분한테 말해서 어떻게 넘겨주는지 확인해야함.)
                    //있으면 자동으로 로그인 시킨다.
                    //저장되어있는 jwt가 없으면 추가정보 입력 화면으로 이동
                    if(ApplicationClass.sSharedPreferences.getString("J-ACCESS-TOKEN","")==""){
                        startActivity(Intent(this, KakaoAddInfo::class.java))
                        overridePendingTransition(0,0)
                        finish()
                    }
                    //저장되어있는 jwt가 있으면, 바로 자동 로그인
                    else{
                        showLoadingDialog(this,3)
                        startActivity(Intent(this, MainActivity::class.java))
                        overridePendingTransition(0,0)
                        finish()
                        dismissLoadingDialog()
                    }
                }
            }
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
        //master key
        binding.masterKey.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0,0)
            finish()
        }
    }
}