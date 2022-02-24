package com.example.risingproject.src.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.config.BaseActivity
import com.example.risingproject.databinding.ActivityMyBinding
import com.example.risingproject.src.login.LoginActivity

class MyActivity : BaseActivity<ActivityMyBinding>(ActivityMyBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mySetting.setOnClickListener {
            ApplicationClass.sSharedPreferences.edit().putString("X-ACCESS-TOKEN","").commit()
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(0,0)
            finish()
            showCustomToast("로그아웃 되었습니다.")
        }
    }
}