package com.example.risingproject.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.risingproject.R
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.config.BaseActivity
import com.example.risingproject.databinding.ActivitySplashBinding
import com.example.risingproject.src.login.LoginActivity
import com.example.risingproject.src.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //자동 로그인
        if(ApplicationClass.sSharedPreferences.getString("X-ACCESS-TOKEN","") != ""){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(0, 0)
                finish()
            }, 3500)
        }
        else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                overridePendingTransition(0, 0)
                finish()
            }, 3500)
        }
    }
}