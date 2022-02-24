package com.example.risingproject.src.login.kakaosignup

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import com.example.risingproject.R
import com.example.risingproject.config.BaseActivity
import com.example.risingproject.databinding.ActivityKakaoAddInfoBinding
import com.example.risingproject.src.login.LoginActivity
import com.example.risingproject.src.login.emailsignup.EmailSignupService
import com.example.risingproject.src.login.emailsignup.models.EmailSignupRequest
import com.kakao.sdk.user.UserApiClient

class KakaoAddInfo : BaseActivity<ActivityKakaoAddInfoBinding>(ActivityKakaoAddInfoBinding::inflate) {
    private var nicknameInput:String=""
    private var signupCompleteBtnState:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginKakaosignupBackbtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(0,0)
            finish()
        }
        nicknameWarning()
        loadUserInfo()
    }
    fun loadUserInfo(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(
                    ContentValues.TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                binding.loginKakaoSignupEmail.text=user.kakaoAccount?.email
            }
        }
    }
    //dp -> px변환
    fun dpTopx(dp:Int):Int{
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val dpi = displayMetrics.densityDpi
        val density = displayMetrics.density
        return (dp*density+0.5).toInt()
    }
    fun nicknameWarningFunc(params: ViewGroup.LayoutParams): Boolean {
        nicknameInput = binding.loginKakaoSignupNickname.text.toString()
        if(nicknameInput.length in 2..15){
            params.height = dpTopx(47)
            binding.loginKakaoSignupNickname.layoutParams = params
            binding.loginKakaoSignupNickname.setBackgroundResource(R.drawable.login_email_login_border)
            return true
        }
        else{
            params.height = dpTopx(70)
            binding.loginKakaoSignupNickname.layoutParams = params
            binding.loginKakaoSignupNickname.setBackgroundResource(R.drawable.login_email_login_border_warning)
            binding.loginKakaoSignupNicknameWarning.text = "별명을 2~15자 내로 입력해주세요."
            return false
        }
    }
    fun nicknameWarning(){
        val params = binding.loginKakaoSignupNickname.layoutParams
        binding.loginKakaoSignupNickname.addTextChangedListener(
            object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    nicknameWarningFunc(params)
                }
                override fun afterTextChanged(s: Editable?) {
                    nicknameWarningFunc(params)
                    signupComplete()
                }
            })
    }
    fun signupComplete(){
        val params = binding.loginKakaoSignupNickname.layoutParams
        if (nicknameInput.isNotEmpty()) {
            if (nicknameWarningFunc(params)) {
                binding.kakaoSignupComplete.setBackgroundResource(R.drawable.login_border_signupfinish_on)
                signupCompleteBtnState = true
            }
            else {
                binding.kakaoSignupComplete.setBackgroundResource(R.drawable.login_border_signupfinish_off)
                signupCompleteBtnState = false
            }
        }
        binding.kakaoSignupComplete.setOnClickListener {
            if(signupCompleteBtnState){
                //회원가입 시도
                showLoadingDialog(this,2)
                showCustomToast("회원가입 완료!")
                startActivity(Intent(this, LoginActivity::class.java))
                overridePendingTransition(0,0)
                finish()
                // 이 부분을 jwt를 보내는 accessToken을 보내는 기능으로 대체해야함 (accessToken - "내가 따로 ApplicationClass에 정의할 AccessToken에서 가져와서")
                // EmailSignupService(this).tryPostSignUp(EmailSignupRequest(emailInput,passwordInput,passwordConfirmInput,nicknameInput))
            }
            else{
                showCustomToast("필수 사항을 확인해주세요.")
                val params = binding.loginKakaoSignupNickname.layoutParams
                nicknameWarningFunc(params)
            }
        }
    }

}