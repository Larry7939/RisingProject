package com.example.risingproject.src.main

import com.example.risingproject.R
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.LinearLayout
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.config.BaseActivity
import com.example.risingproject.databinding.ActivityMainBinding
import com.example.risingproject.src.login.LoginActivity
import com.example.risingproject.src.main.basket.BasketActivity
import com.example.risingproject.src.main.detail.ProductActivity
import com.example.risingproject.util.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


class MainActivity:BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private var backKeyPressedTime: Long = 0

    private var tabLayout: TabLayout? = null //
    private val tabNames: ArrayList<String> = arrayListOf("인기","사진","집들이","노하우","전문가집들이","질문과답변")//


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.logout.setOnClickListener {
//            ApplicationClass.sSharedPreferences.edit().putString("X-ACCESS-TOKEN","").commit()
//            startActivity(Intent(this, LoginActivity::class.java))
//            overridePendingTransition(0,0)
//            finish()
//            showCustomToast("로그아웃 되었습니다.")
//        }
//        binding.kakaoLogout.setOnClickListener {
////            ApplicationClass.sSharedPreferences.edit().putString("J-ACCESS-TOKEN","").commit()
//            startActivity(Intent(this, LoginActivity::class.java))
//            overridePendingTransition(0,0)
//            finish()
//            showCustomToast("로그아웃 되었습니다.")
//        }
        binding.homeTopBasket.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }
        setTabwidthFucn()
        setTabLayout()
        setViewPager()
        bottom_btn()
    }
    override fun onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        //super.onBackPressed()
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 showCustomToast
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showCustomToast("\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.")
            return
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity()
            System.runFinalization()
            System.exit(0)
            finish()
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
    private fun setTabwidth(tabposition: Int, width:Int) {
        val layout: LinearLayout = (binding.homeTabs?.getChildAt(0) as LinearLayout).getChildAt(tabposition) as LinearLayout
        val layoutParams: LinearLayout.LayoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 0f
        layoutParams.width = dpTopx(width)
        layout.layoutParams = layoutParams
    }
    private fun setTabwidthFucn(){
        setTabwidth(0,48)
        setTabwidth(1,48)
        setTabwidth(2,60)
        setTabwidth(3,60)
        setTabwidth(4,96)
        setTabwidth(5,84)
    }

    //--------------------------------
    @TargetApi(Build.VERSION_CODES.N) //Api 레벨 24 인 안드로이드 7.0 Nougat 버젼으로 빌드
    private fun setTabLayout() {
        tabLayout = binding.homeTabs
        val adapter = FragmentAdapter(supportFragmentManager)
        val viewPager = binding.viewPager
        viewPager.adapter = adapter
        for(name in tabNames){
            if(tabLayout!!.tabCount < adapter.count){
                tabLayout!!.addTab(tabLayout!!.newTab().setText(name))
            }
        }
    }
    private fun setViewPager() {
        val adapter = FragmentAdapter(supportFragmentManager)
        val viewPager = binding.viewPager
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
    //--------------------------------
    private fun bottom_btn(){
        binding.bottomBtn1.setOnClickListener {
            binding.bottomBtn1.setImageResource(R.drawable.bottom_btn1_on)
            binding.bottomBtn2.setImageResource(R.drawable.bottom_btn2_off)
            binding.bottomBtn3.setImageResource(R.drawable.bottom_btn3_off)
            binding.bottomBtn4.setImageResource(R.drawable.bottom_btn4_off)
            binding.bottomTv1.setTextColor(resources.getColor(R.color.bottomBarTvColor_on))
            binding.bottomTv2.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
            binding.bottomTv3.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
            binding.bottomTv4.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
        }
        binding.bottomBtn2.setOnClickListener {
            binding.bottomBtn1.setImageResource(R.drawable.bottom_btn1_off)
            binding.bottomBtn2.setImageResource(R.drawable.bottom_btn2_on)
            binding.bottomBtn3.setImageResource(R.drawable.bottom_btn3_off)
            binding.bottomBtn4.setImageResource(R.drawable.bottom_btn4_off)
            binding.bottomTv1.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
            binding.bottomTv2.setTextColor(resources.getColor(R.color.bottomBarTvColor_on))
            binding.bottomTv3.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
            binding.bottomTv4.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
        }
        binding.bottomBtn3.setOnClickListener {
            binding.bottomBtn1.setImageResource(R.drawable.bottom_btn1_off)
            binding.bottomBtn2.setImageResource(R.drawable.bottom_btn2_off)
            binding.bottomBtn3.setImageResource(R.drawable.bottom_btn3_on)
            binding.bottomBtn4.setImageResource(R.drawable.bottom_btn4_off)
            binding.bottomTv1.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
            binding.bottomTv2.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
            binding.bottomTv3.setTextColor(resources.getColor(R.color.bottomBarTvColor_on))
            binding.bottomTv4.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
        }
        binding.bottomBtn4.setOnClickListener {
            binding.bottomBtn1.setImageResource(R.drawable.bottom_btn1_off)
            binding.bottomBtn2.setImageResource(R.drawable.bottom_btn2_off)
            binding.bottomBtn3.setImageResource(R.drawable.bottom_btn3_off)
            binding.bottomBtn4.setImageResource(R.drawable.bottom_btn4_on)
            binding.bottomTv1.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
            binding.bottomTv2.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
            binding.bottomTv3.setTextColor(resources.getColor(R.color.bottomBarTvColor_off))
            binding.bottomTv4.setTextColor(resources.getColor(R.color.bottomBarTvColor_on))
            val intent = Intent(this,MyActivity::class.java)
            startActivity(intent)
        }
    }
}