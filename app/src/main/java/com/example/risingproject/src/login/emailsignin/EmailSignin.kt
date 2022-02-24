package com.example.risingproject.src.login.emailsignin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.risingproject.R
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.config.BaseActivity
import com.example.risingproject.databinding.ActivityEmailSigninBinding
import com.example.risingproject.src.login.LoginActivity
import com.example.risingproject.src.login.emailsignin.models.EmailSigninRequest
import com.example.risingproject.src.login.emailsignin.models.EmailSigninResponse
import com.example.risingproject.src.main.MainActivity

class EmailSignin : BaseActivity<ActivityEmailSigninBinding>(ActivityEmailSigninBinding::inflate),EmailSigninView {
    var signinPasswordInput = ""
    var signinEmailInput = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginEmailsigninBackbtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            overridePendingTransition(0,0)
            finish()
        }
        emailCheck()
        passwordCheck()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,LoginActivity::class.java))
        overridePendingTransition(0,0)
        finish()
    }
    fun signinCheck(){
        signinPasswordInput = binding.loginEmailSigninPassword.text.toString()
        signinEmailInput = binding.loginEmailSigninEmail.text.toString()
        if(signinEmailInput.isNotEmpty() && signinPasswordInput.isNotEmpty()){
            binding.loginEmailSigninLoginBtn.setBackgroundResource(R.drawable.login_border_signinfinish_on)
        }
        else{
            binding.loginEmailSigninLoginBtn.setBackgroundResource(R.drawable.login_border_signinfinish_off)
        }
        binding.loginEmailSigninLoginBtn.setOnClickListener {
            signinEmailInput=binding.loginEmailSigninEmail.text.toString()
            signinPasswordInput=binding.loginEmailSigninPassword.text.toString()
            if(signinEmailInput.isEmpty()){
                showCustomToast("이메일을 입력해주세요.")
            }
            else if(signinPasswordInput.isEmpty()){
                showCustomToast("비밀번호를 입력해주세요.")
            }
            else{
                showLoadingDialog(this,1)
                EmailSigninService(this).tryPostSignIn(EmailSigninRequest(signinEmailInput,signinPasswordInput))
            }
        }
    }
    fun emailCheck(){
        binding.loginEmailSigninEmail.addTextChangedListener(
            object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    signinCheck()
                }
                override fun afterTextChanged(s: Editable?) {
                    signinCheck()
                }
            }
        )
    }
    fun passwordCheck(){
        binding.loginEmailSigninPassword.addTextChangedListener(
            object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    signinCheck()
                }
                override fun afterTextChanged(s: Editable?) {
                    signinCheck()
                }
            }
        )
    }

    override fun onPostSignInSuccess(response: EmailSigninResponse) {
        dismissLoadingDialog()
        response.message?.let {
            if(response.code == 1000){
                showCustomToast("로그인 성공")
                //로그인 성공 시 홈 화면으로 이동 및 jwt 저장
                ApplicationClass.sSharedPreferences.edit().putString("X-ACCESS-TOKEN",response.result.jwt).commit()
                Log.d("액세스 토큰",  "${response.result.jwt}")
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(0,0)
                finish()
            }
            else {
                showCustomToast(it)
            }
        }
    }
    override fun onPostSignInFailure(message: String) {
        dismissLoadingDialog()
    }
}