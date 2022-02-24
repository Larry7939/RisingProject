package com.example.risingproject.src.login.emailsignup
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.risingproject.R
import com.example.risingproject.config.BaseActivity
import com.example.risingproject.databinding.ActivityEmailSignupBinding
import com.example.risingproject.src.login.LoginActivity
import com.example.risingproject.src.login.emailsignup.models.EmailOverlapResponse
import com.example.risingproject.src.login.emailsignup.models.EmailSignupRequest
import com.example.risingproject.src.login.emailsignup.models.EmailSignupResponse
import com.example.risingproject.src.login.emailsignup.models.NicknameOverlapResponse
import com.github.ybq.android.spinkit.animation.AnimationUtils.start
var emailOverlaped = false
var nicknameOverlaped = false
class EmailSignup :BaseActivity<ActivityEmailSignupBinding>(ActivityEmailSignupBinding::inflate),EmailSignupView {
    private var emailInput:String=""
    private var passwordInput:String=""
    private var passwordConfirmInput:String=""
    private var nicknameInput:String=""
    private var signupCompleteBtnState:Boolean = false
    var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginEmailsignupBackbtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(0,0)
            finish()
        }
        emailWarning()
        passwordWarning()
        passwordConfirmWarning()
        nicknameWarning()
        checkBoxAll()
        signupComplete()
    }
    //dp -> px변환
    fun dpTopx(dp:Int):Int{
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val dpi = displayMetrics.densityDpi
        val density = displayMetrics.density
        return (dp*density+0.5).toInt()
    }
    //영어 포함 여부 판별
    fun isContainEng(s:String):Boolean{
        var i=0
        while(i < passwordInput.length){
            val c = s.elementAt(i)
            if(!c.isDigit()) {
                val c = s.codePointAt(i)
                if (c in 0x0041..0x007A) {
                    return true
                }
            }
            i+=1
        }
        return false
    }
    //숫자 포함 여부 판별
    fun isContainDigit(s:String):Boolean{
        var i=0
        while(i < passwordInput.length){
            val c = s.elementAt(i)
            if(c.isDigit()){
                return true
            }
        i+=1
        }
        return false
    }
    //이메일 적합성 검사
    fun emailWarningFunc(params: ViewGroup.LayoutParams): Boolean {
        emailInput = binding.loginEmailSignupEmail.text.toString()
        if(emailOverlaped){
            //이메일 인증하기 버튼 비활성화(border 및 clickable)
            binding.loginEmailSignupVerify.setBackgroundResource(R.drawable.login_border_verify_inactivate)
            binding.loginEmailSignupVerify.setTextColor(ContextCompat.getColor(this@EmailSignup, R.color.verifyBtnColor_off))
            binding.loginEmailSignupVerify.isClickable =false
            //height 늘리기(70dp)
            params.height=dpTopx(70)
            binding.loginEmailSignupEmail.layoutParams = params
            //visible안해도 됨
            //background를 login_email_login_boder_warning으로 바꾸기
            binding.loginEmailSignupEmail.setBackgroundResource(R.drawable.login_email_login_border_warning)
            //이메일 중복 시, 경고 text변경
            binding.loginEmailSignupEmailWarning.text = "이미 가입한 이메일입니다.'이메일로 로그인'으로 로그인해주세요."
            return false
        }
        else if(emailInput.contains("@")&&(emailInput.contains("naver.com")||emailInput.contains("hanmail.net")||emailInput.contains("google.com")||emailInput.contains("daum.net"))&&emailInput.length>8){
            //이메일 인증하기 버튼 활성화(border(login_border_verify_activate,textcolor(#4AC4ED) 변경 및 clickable)
            binding.loginEmailSignupVerify.setBackgroundResource(R.drawable.login_border_verify_activate)
            binding.loginEmailSignupVerify.setTextColor(ContextCompat.getColor(this@EmailSignup, R.color.verifyBtnColor))
            binding.loginEmailSignupVerify.isClickable =true
            //height 원래대로 되돌리기(47dp)
            params.height=dpTopx(47)
            binding.loginEmailSignupEmail.layoutParams = params
            //background를 원래대로(login_email_login_border)으로 돌려놓기
            binding.loginEmailSignupEmail.setBackgroundResource(R.drawable.login_email_login_border)
            return true
        }
        else{
            //이메일 인증하기 버튼 비활성화(border 및 clickable)
            binding.loginEmailSignupVerify.setBackgroundResource(R.drawable.login_border_verify_inactivate)
            binding.loginEmailSignupVerify.setTextColor(ContextCompat.getColor(this@EmailSignup, R.color.verifyBtnColor_off))
            binding.loginEmailSignupVerify.isClickable =false
            //height 늘리기(70dp)
            params.height=dpTopx(70)
            binding.loginEmailSignupEmail.layoutParams = params
            //visible안해도 됨
            //background를 login_email_login_boder_warning으로 바꾸기
            binding.loginEmailSignupEmail.setBackgroundResource(R.drawable.login_email_login_border_warning)

            //경고 text변경
            binding.loginEmailSignupEmailWarning.text ="이메일 형식이 올바르지 않습니다."
            return false
        }
    }
    fun emailWarning(){
        val params = binding.loginEmailSignupEmail.layoutParams
        binding.loginEmailSignupEmail.addTextChangedListener(
            object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                emailWarningFunc(params)
                EmailSignupService(this@EmailSignup).tryEmailOverlap(binding.loginEmailSignupEmail.text.toString())
                handler.postDelayed(
                    Runnable {
                        emailWarningFunc(params)
                    }
                    ,500)
            }
            override fun afterTextChanged(s: Editable?) {
                signupComplete()
            }
        }
        )
    }
    //비밀번호 적합성 검사
    fun passwordWarningFunc(params: ViewGroup.LayoutParams): Boolean {
        passwordInput = binding.loginEmailSignupPassword.text.toString()
        if(passwordInput.length>=8 && isContainEng(passwordInput) && isContainDigit(passwordInput)){
            params.height=dpTopx(47)
            binding.loginEmailSignupPassword.layoutParams = params
            binding.loginEmailSignupPassword.setBackgroundResource(R.drawable.login_email_login_border)
            return true
        }
        else{
            params.height=dpTopx(70)
            binding.loginEmailSignupPassword.layoutParams = params
            binding.loginEmailSignupPassword.setBackgroundResource(R.drawable.login_email_login_border_warning)
            return false
        }
    }
    fun passwordWarning(){
        val params = binding.loginEmailSignupPassword.layoutParams
        binding.loginEmailSignupPassword.addTextChangedListener(
            object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { passwordWarningFunc(params) }
                override fun afterTextChanged(s: Editable?) {
                    passwordWarningFunc(params)
                    signupComplete()
                }
            })
    }
    //비밀번호 확인 적합성 검사
    fun passwordConfirmWarningFunc(params:ViewGroup.LayoutParams): Boolean {
        passwordConfirmInput = binding.loginEmailSignupPasswordConfirm.text.toString()
        passwordInput = binding.loginEmailSignupPassword.text.toString()
        if (passwordConfirmInput.isNotEmpty() && passwordConfirmInput == passwordInput) {
            params.height = dpTopx(47)
            binding.loginEmailSignupPasswordConfirm.layoutParams = params
            binding.loginEmailSignupPasswordConfirm.setBackgroundResource(R.drawable.login_email_login_border)
            return true
        } else if (passwordConfirmInput.isEmpty()) {
            params.height = dpTopx(70)
            binding.loginEmailSignupPasswordConfirm.layoutParams = params
            binding.loginEmailSignupPasswordConfirm.setBackgroundResource(R.drawable.login_email_login_border_warning)
            binding.loginEmailSignupPasswordConfirmWarning.text = "확인을 위해 비밀번호를 한 번 더 입력해주세요."
            return false
        } else {
            params.height = dpTopx(70)
            binding.loginEmailSignupPasswordConfirm.layoutParams = params
            binding.loginEmailSignupPasswordConfirm.setBackgroundResource(R.drawable.login_email_login_border_warning)
            binding.loginEmailSignupPasswordConfirmWarning.text = "비밀번호가 일치하지 않습니다."
            return false
        }
    }
    fun passwordConfirmWarning(){
        val params = binding.loginEmailSignupPasswordConfirm.layoutParams
        binding.loginEmailSignupPasswordConfirm.addTextChangedListener(
            object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    passwordConfirmWarningFunc(params)
                }
                override fun afterTextChanged(s: Editable?) {
                    passwordConfirmWarningFunc(params)
                    signupComplete()
                }
            }
        )
    }
    //별명 적합성 검사
    fun nicknameWarningFunc(params:ViewGroup.LayoutParams): Boolean {
        nicknameInput = binding.loginEmailSignupNickname.text.toString()
        if(nicknameOverlaped){
            //height 늘리기(70dp)
            params.height=dpTopx(70)
            binding.loginEmailSignupNickname.layoutParams = params
            //visible안해도 됨
            //background를 login_email_login_boder_warning으로 바꾸기
            binding.loginEmailSignupNickname.setBackgroundResource(R.drawable.login_email_login_border_warning)
            //이메일 중복 시, 경고 text변경
            binding.loginEmailSignupNicknameWarning.text = "사용 중인 별명입니다."
            return false
        }
        else if(nicknameInput.length in 2..15){
            params.height = dpTopx(47)
            binding.loginEmailSignupNickname.layoutParams = params
            binding.loginEmailSignupNickname.setBackgroundResource(R.drawable.login_email_login_border)
            return true
        }
        else{
            params.height = dpTopx(70)
            binding.loginEmailSignupNickname.layoutParams = params
            binding.loginEmailSignupNickname.setBackgroundResource(R.drawable.login_email_login_border_warning)
            binding.loginEmailSignupNicknameWarning.text = "별명을 2~15자 내로 입력해주세요."
            return false
        }
    }
    fun nicknameWarning(){
        val params = binding.loginEmailSignupNickname.layoutParams
        binding.loginEmailSignupNickname.addTextChangedListener(
            object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    nicknameWarningFunc(params)
                    EmailSignupService(this@EmailSignup).tryNicknameOverlap(binding.loginEmailSignupNickname.text.toString())
                    handler.postDelayed(
                        Runnable {
//                            showCustomToast("======emailOverlaped -> ${emailOverlaped}\n======emailText -> ${binding.loginEmailSignupNickname.text.toString()}")
                            nicknameWarningFunc(params)
                        }
                        ,500)
                }
                override fun afterTextChanged(s: Editable?) {
                    signupComplete()
                }
            })
    }
    //체크박스 전체동의
    fun checkBoxWarning(): Boolean {
        val c1 = binding.loginCheckbox1
        val c2 = binding.loginCheckbox2
        val c3 = binding.loginCheckbox3
        if(c1.isSelected && c2.isSelected && c3.isSelected){
            binding.loginEmailSignupTermsWarning.visibility = View.GONE
            binding.loginTermsAndConditions.setBackgroundResource(R.drawable.login_email_login_border)
            return true
        }
        else{
            binding.loginEmailSignupTermsWarning.visibility = View.VISIBLE
            binding.loginTermsAndConditions.setBackgroundResource(R.drawable.login_email_login_border_warning)
            return false
        }
    }
    fun checkBoxAll(){
        val ca = binding.loginCheckboxAll
        val c1 = binding.loginCheckbox1
        val c2 = binding.loginCheckbox2
        val c3 = binding.loginCheckbox3
        val c4 = binding.loginCheckbox4
        ca.setOnClickListener {
          if(ca.isSelected){
              ca.isSelected = false
              c1.isSelected = false
              c2.isSelected = false
              c3.isSelected = false
              c4.isSelected = false
              signupComplete()
              checkBoxWarning()
          }
          else{
              ca.isSelected = true
              c1.isSelected = true
              c2.isSelected = true
              c3.isSelected = true
              c4.isSelected = true
              signupComplete()
              checkBoxWarning()
          }
        }
        c1.setOnClickListener {
            c1.isSelected = !c1.isSelected
            ca.isSelected = c1.isSelected && c2.isSelected && c3.isSelected && c4.isSelected
            signupComplete()
            checkBoxWarning()
        }
        c2.setOnClickListener {
            c2.isSelected = !c2.isSelected
            ca.isSelected = c1.isSelected && c2.isSelected && c3.isSelected && c4.isSelected
            signupComplete()
            checkBoxWarning()
        }
        c3.setOnClickListener {
            c3.isSelected = !c3.isSelected
            ca.isSelected = c1.isSelected && c2.isSelected && c3.isSelected && c4.isSelected
            signupComplete()
            checkBoxWarning()
        }
        c4.setOnClickListener {
            c4.isSelected = !c4.isSelected
            ca.isSelected = c1.isSelected && c2.isSelected && c3.isSelected && c4.isSelected
            signupComplete()
            checkBoxWarning()
        }

    }
    //회원가입 완료 버튼 활성화
    fun signupComplete(){
        val c1 = binding.loginCheckbox1
        val c2 = binding.loginCheckbox2
        val c3 = binding.loginCheckbox3
        val params1 = binding.loginEmailSignupEmail.layoutParams
        val params2 = binding.loginEmailSignupPassword.layoutParams
        val params3 = binding.loginEmailSignupPasswordConfirm.layoutParams
        val params4 = binding.loginEmailSignupNickname.layoutParams
        if (emailInput.isNotEmpty() && passwordInput.isNotEmpty() && passwordConfirmInput.isNotEmpty() && nicknameInput.isNotEmpty()&& c1.isSelected && c2.isSelected && c3.isSelected) {
            if (emailWarningFunc(params1) && passwordWarningFunc(params2) && passwordConfirmWarningFunc(params3) && nicknameWarningFunc(params4)&&checkBoxWarning()) {
                binding.loginSignupComplete.setBackgroundResource(R.drawable.login_border_signupfinish_on)
                signupCompleteBtnState = true
            }
            else {
                binding.loginSignupComplete.setBackgroundResource(R.drawable.login_border_signupfinish_off)
                signupCompleteBtnState = false
            }
        }
        else{
            binding.loginSignupComplete.setBackgroundResource(R.drawable.login_border_signupfinish_off)
            signupCompleteBtnState = false
        }
        binding.loginSignupComplete.setOnClickListener {
            val viewLocation = IntArray(2)
            if(signupCompleteBtnState){
                //회원가입 시도
                showLoadingDialog(this,2)
                emailInput = binding.loginEmailSignupEmail.text.toString()
                passwordInput = binding.loginEmailSignupPassword.text.toString()
                passwordConfirmInput = binding.loginEmailSignupPasswordConfirm.text.toString()
                nicknameInput = binding.loginEmailSignupNickname.text.toString()
                EmailSignupService(this).tryPostSignUp(EmailSignupRequest(emailInput,passwordInput,passwordConfirmInput,nicknameInput))
            }
            else{
                showCustomToast("필수 사항을 확인해주세요.")
                val params1 = binding.loginEmailSignupEmail.layoutParams
                val params2 = binding.loginEmailSignupPassword.layoutParams
                val params3 = binding.loginEmailSignupPasswordConfirm.layoutParams
                val params4 = binding.loginEmailSignupNickname.layoutParams
                if(!checkBoxWarning()){
                    binding.loginTermsAndConditions.getLocationOnScreen(viewLocation)
                    binding.loginScrollView.scrollTo(viewLocation[0],viewLocation[1])
                }
                if(!nicknameWarningFunc(params4)){
                    binding.loginEmailSignupNickname.getLocationOnScreen(viewLocation)
                    binding.loginScrollView.scrollTo(viewLocation[0],viewLocation[1])
                }
                if(!passwordConfirmWarningFunc(params3)){
                    binding.loginEmailSignupPasswordConfirm.getLocationOnScreen(viewLocation)
                    binding.loginScrollView.scrollTo(viewLocation[0],viewLocation[1])
                }
                if(!passwordWarningFunc(params2)){
                    binding.loginEmailSignupPassword.getLocationOnScreen(viewLocation)
                    binding.loginScrollView.scrollTo(viewLocation[0],viewLocation[1])
                }
                if(!emailWarningFunc(params1)) {
                    binding.loginEmailSignupEmail.getLocationOnScreen(viewLocation)
                    binding.loginScrollView.scrollTo(viewLocation[0],viewLocation[1])
                }
            }
        }
    }
    override fun onPostSignUpSuccess(response: EmailSignupResponse) {
        dismissLoadingDialog()
        response.message?.let {
            if(response.code == 1000){
                showCustomToast("회원가입 성공")
                //회원가입 성공 시 로그인 화면으로 이동
                startActivity(Intent(this, LoginActivity::class.java))
                overridePendingTransition(0,0)
                finish()
            }
            else {
                showCustomToast(it)
            }
        }
    }
    override fun onPostSignUpFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetOverlapSuccess(response: EmailOverlapResponse) {

    }

    override fun onGetOverlapFailure(message: String) {
        showCustomToast("오류 : $message")

    }

    override fun onGetNicknameOverlapSuccess(response: NicknameOverlapResponse) {

    }

    override fun onGetNicknameOverlapFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}