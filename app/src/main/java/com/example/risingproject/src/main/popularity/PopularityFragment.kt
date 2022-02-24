package com.example.risingproject.src.main.popularity


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.*
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.risingproject.R
import com.example.risingproject.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingproject.config.BaseFragment
import com.example.risingproject.databinding.FragmentPopularityBinding
import com.example.risingproject.src.main.MainActivity
import com.example.risingproject.src.main.detail.DetailActivity
import com.example.risingproject.src.main.popularity.models.PopLookupResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

data class BestInfo(
    var ProductName:String,
    var Discount:String,
    var Cost:String,
)
class PopularityFragment :BaseFragment<FragmentPopularityBinding>(FragmentPopularityBinding::bind,R.layout.fragment_popularity),PopularityFragmentView
{
    private var myHandler = MyHandler()
    private var currentPosition = 1700
    private val intervalTime = 5000.toLong()
    private var timer_now: Timer? = null
    private var timer_back: Timer? = null
    private var timertask: TimerTask? = null
    private var hour = 0
    private var minute = 0
    private var second = 0
    private var currTime = 28800
    private lateinit var mainActivity:MainActivity
    private var bannerList:ArrayList<Int> = arrayListOf(R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4,R.drawable.banner5,R.drawable.banner6,R.drawable.banner7,R.drawable.banner8,
        R.drawable.banner9, R.drawable.banner10,R.drawable.banner11,R.drawable.banner12,R.drawable.banner13,R.drawable.banner14,R.drawable.banner15, R.drawable.banner16,R.drawable.banner17)
    private var num = bannerList.size //배너 갯수
    private var bestList:ArrayList<Bitmap> = arrayListOf()
    private var bestInfoList:ArrayList<BestInfo> = arrayListOf()
    //각 탭별 best 1 -> bestList[0][3][6][9][12][15][18][21][24][27][30][33][36][39][42][45][48]
    private var tabLayout: TabLayout? = null //
    private val tabNames: ArrayList<String> = arrayListOf("전체","가구","패브릭","조명","가전","주방용품","장식/소품","수납/정리","생활용품","생필품","공구/DIY","인테리어시공","반려동물","캠핑용품","실내운동","유아/아동","렌탈")//
    private val handler = Handler(Looper.getMainLooper())

    fun newInstance(): Fragment {
        return PopularityFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBanner()
        setTabwidthFucn()
        popSetting()
        setBest()
    }
    override fun onResume() {
        super.onResume()
        binding.textViewCurrentBanner.text = sSharedPreferences.getInt("ViewPagerPosition",1).toString()
        autoScrollStart(intervalTime)
        if (timer_back != null) {
            timer_back!!.cancel() //타이머 종료
        }
        todayDealTimer()
        setBest()

    }
    // 다른 페이지로 떠나있는 동안 스크롤이 동작할 필요는 없음. 정지
    override fun onPause() {
        super.onPause()
        sSharedPreferences.edit().putInt("ViewPagerPosition",binding.textViewCurrentBanner.text.toString().toInt()).commit()
        autoScrollStop()
        if (timer_now != null) {
            timer_now!!.cancel() //타이머 종료
        }
        todayDealTimer_back()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    fun dpTopx(dp:Int):Int{
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val dpi = displayMetrics.densityDpi
        val density = displayMetrics.density
        return (dp*density+0.5).toInt()
    }
    private fun setBanner(){
        binding.popularityVp.adapter = PopularityVPA(bannerList)
        PopularityVPA(bannerList).notifyDataSetChanged()
        numOfBanner()
    }
    private fun numOfBanner(){
        binding.popularityVp.setCurrentItem(currentPosition,false)
        binding.textViewTotalBanner.text = num.toString()
        binding.popularityVp.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPosition=position //내가 드래그 해서 선택한 페이지의 position을 currentPosition에 대입해야, 자동 스크롤이 내가 선택한 페이지의 바로 다음 페이지로 스크롤 된다.
                    binding.textViewCurrentBanner.text = "${(position%num)+1}"
                }
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        // 뷰페이저에서 손 떼었을때 / 뷰페이저 멈춰있을 때
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                        // 뷰페이저 움직이는 중
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                }
            })
        }
    }
    private fun autoScrollStart(intervalTime: Long) {
        myHandler.removeMessages(0) // 이거 안하면 핸들러가 1개, 2개, 3개 ... n개 만큼 계속 늘어남
        myHandler.sendEmptyMessageDelayed(0, intervalTime) // intervalTime 만큼 반복해서 핸들러를 실행하게 함
    }
    private fun autoScrollStop(){
        myHandler.removeMessages(0) // 핸들러를 중지시킴
    }
    private inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if(msg.what == 0) {
                binding.popularityVp.setCurrentItem(++currentPosition, true) // 다음 페이지로 이동
                autoScrollStart(intervalTime) // 스크롤을 계속 이어서 한다.
            }
        }
    }
    private fun todayDealTimer() {
        hour = currTime /3600
        minute = (currTime %3600)/60
        second = currTime %60
        if (timer_now != null) {
            timer_now!!.cancel() //타이머 종료
        }
        timertask = timerTask {
            mainActivity.runOnUiThread {
                binding.popularityTodayDealTime.text =String.format("%02d:%02d:%02d 남음", hour, minute, second)
//                showCustomToast("현재 시간 ${second}")
                if (hour == 0 && second == 0 && minute == 0) {
                    cancel()
                }
                if (second == 0) {
                    minute--
                    second = 60
                }
                if (minute == 0 || minute == -1) {
                    hour--
                    minute = 59
                }
                second--
                currTime--
            }
        }
        timer_now = Timer()
        timer_now!!.scheduleAtFixedRate(timertask, 0, 1000)
    }
    private fun todayDealTimer_back() {
        hour = this.currTime /3600
        minute = (this.currTime %3600)/60
        second = this.currTime %60
        if (timer_back != null) {
            timer_back!!.cancel() //타이머 종료
        }
        timertask = timerTask {
            mainActivity.runOnUiThread {
//                showCustomToast("현재 시간 ${second}")
                if (hour == 0 && second == 0 && minute == 0) {
                    cancel()
                }
                if (second == 0) {
                    minute--
                    second = 60
                }
                if (minute == 0 || minute == -1) {
                    hour--
                    minute = 59
                }
                second--
                currTime--
            }
        }
        timer_back = Timer()
        timer_back!!.scheduleAtFixedRate(timertask, 0, 1000)
    }
    private fun setTabwidth(tabposition: Int, width:Int) {
        val layout: LinearLayout = (binding.popularityBestTabs?.getChildAt(0) as LinearLayout).getChildAt(tabposition) as LinearLayout
        val layoutParams: LinearLayout.LayoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 0f
        layoutParams.width = dpTopx(width)
        layout.layoutParams = layoutParams
    }
    private fun setTabwidthFucn(){
        setTabwidth(0,55)//2글자
        setTabwidth(1,55)
        setTabwidth(2,74)//3글자
        setTabwidth(3,55)
        setTabwidth(4,55)
        setTabwidth(5,83)//4글자
        setTabwidth(6,93)//5글자(/)
    }
    private fun setBest(){
//        bestList[9] = R.drawable.banner1
        //이 2줄은 배너 넘기는 뷰페이저에서 가져온 코드. 뷰페이저랑 bestList랑 연결하는 코드
        Thread{
            Thread.sleep(1700)
            handler.post {
        binding.popularityBestVp.adapter = PopularityVPAforBest(bestList,bestInfoList)
        PopularityVPAforBest(bestList,bestInfoList).notifyDataSetChanged()
        //이건 MainActivity에서 가져온, tab이랑 뷰페이저 연결하는 코드
        tabLayout = binding.popularityBestTabs
        val adapter = PopularityVPAforBest(bestList,bestInfoList)
        val viewPager = binding.popularityBestVp
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout!!,viewPager){ tab, position->
            tab.text = tabNames[position%17]
        }.attach()
            }
        }.start()
    }
    private fun popSetting(){

//        for(i in 1..16) {
//        PopService(this).tryPop(i)
        PopService(this).tryPop(0)
        PopService(this).tryPop(1)
//        }
        if(bestList.isEmpty()){
            for(i in 0..50){
                bestList.add(R.drawable.popularity_pop_photo1.toDrawable().toBitmap(1,1))
            }
        }
        if(bestInfoList.isEmpty()) {
            for (i in 0..50) {
                bestInfoList.add(BestInfo("", "", ""))
            }
        }
    }
    override fun onGetPopSuccess(response: PopLookupResponse) {
        //오늘의 스토리
        binding.popularityTodayStoryTv1.text=response.result[0].TodayStory[0].HouseWarmTitle
        binding.popularityTodayStoryTv2.text=response.result[0].TodayStory[1].HouseWarmTitle
        binding.popularityTodayStoryTv3.text=response.result[0].TodayStory[2].HouseWarmTitle
        binding.popularityTodayStoryTv4.text=response.result[0].TodayStory[3].HouseWarmTitle
        binding.popularityTodayStoryTv5.text=response.result[0].TodayStory[4].HouseWarmTitle
        binding.popularityTodayStoryTv6.text=response.result[0].TodayStory[5].HouseWarmTitle
        binding.popularityTodayStoryTv7.text=response.result[0].TodayStory[6].HouseWarmTitle
        binding.popularityTodayStoryTv8.text=response.result[0].TodayStory[7].HouseWarmTitle
//        binding.popularityTodayStoryTv9.text=response.result[0].TodayStory[8].HouseWarmTitle
//        binding.popularityTodayStoryTv10.text=response.result[0].TodayStory[9].HouseWarmTitle

        val imageUrl1:String = response.result[0].TodayStory[0].MainImage
        val imageUrl2:String =response.result[0].TodayStory[1].MainImage
        val imageUrl3:String =response.result[0].TodayStory[2].MainImage
        val imageUrl4:String = response.result[0].TodayStory[3].MainImage
        val imageUrl5:String =response.result[0].TodayStory[4].MainImage
        val imageUrl6:String =response.result[0].TodayStory[5].MainImage
        val imageUrl7:String = response.result[0].TodayStory[6].MainImage
        val imageUrl8:String =response.result[0].TodayStory[7].MainImage
//        val imageUrl9:String =response.result[0].TodayStory[8].MainImage
//        val imageUrl10:String =response.result[0].TodayStory[9].MainImage

        Glide.with(this).load(imageUrl1).into(binding.popularityTodayStoryIv1)
        Glide.with(this).load(imageUrl2).into(binding.popularityTodayStoryIv2)
        Glide.with(this).load(imageUrl3).into(binding.popularityTodayStoryIv3)
        Glide.with(this).load(imageUrl4).into(binding.popularityTodayStoryIv4)
        Glide.with(this).load(imageUrl5).into(binding.popularityTodayStoryIv5)
        Glide.with(this).load(imageUrl6).into(binding.popularityTodayStoryIv6)
        Glide.with(this).load(imageUrl7).into(binding.popularityTodayStoryIv7)
        Glide.with(this).load(imageUrl8).into(binding.popularityTodayStoryIv8)
//        Glide.with(this).load(imageUrl9).into(binding.popularityTodayStoryIv9)
//        Glide.with(this).load(imageUrl10).into(binding.popularityTodayStoryIv10)

        //베스트
//        Log.d("=============","${response.result[0].BestProduct[0][0].CategoryName}")
//        Log.d("=============","${response.result[0].BestProduct[1][0].ProductImage}")
        if(response.result[0].BestProduct[0][0].CategoryName!=null) {
            val best2 =response.result[0].BestProduct[0][0].CategoryName
            if (best2 == "전체") {
                CoroutineScope(Dispatchers.Main).launch {
                    bestList[0] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
                    bestList[1] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage)}!!
                    bestList[2] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage)}!!
                }
                bestInfoList[0].ProductName =response.result[0].BestProduct[1][0].ProductName
                bestInfoList[0].Cost =response.result[0].BestProduct[1][0].Cost
                bestInfoList[0].Discount =response.result[0].BestProduct[1][0].DiscountPercent
                bestInfoList[1].ProductName =response.result[0].BestProduct[1][1].ProductName
                bestInfoList[1].Cost =response.result[0].BestProduct[1][1].Cost
                bestInfoList[1].Discount =response.result[0].BestProduct[1][1].DiscountPercent
                bestInfoList[2].ProductName =response.result[0].BestProduct[1][2].ProductName
                bestInfoList[2].Cost =response.result[0].BestProduct[1][2].Cost
                bestInfoList[2].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "가구") {
                CoroutineScope(Dispatchers.Main).launch {
                    bestList[3] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
                    // bestList[4] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
                    // bestList[5] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
                }
                bestInfoList[3].ProductName =response.result[0].BestProduct[1][0].ProductName
                bestInfoList[3].Cost =response.result[0].BestProduct[1][0].Cost
                bestInfoList[3].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[4].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[4].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[4].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[5].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[5].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[5].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "패브릭"){
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[6] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[7] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[8] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[6].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[6].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[6].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[7].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[7].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[7].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[8].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[8].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[8].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "조명"){
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[9] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[10] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[11] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[9].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[9].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[9].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[10].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[10].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[10].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[11].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[11].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[11].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "가전"){
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[12] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[13] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[14] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[12].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[12].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[12].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[13].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[13].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[13].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[14].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[14].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[14].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "주방용품") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[15] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[16] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[17] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[15].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[15].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[15].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[16].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[16].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[16].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[17].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[17].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[17].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "장식/소품") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[18] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[19] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[20] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[18].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[18].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[18].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[19].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[19].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[19].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[20].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[20].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[20].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "수납/정리") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[21] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[22] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[23] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[21].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[21].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[21].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[22].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[22].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[22].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[23].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[23].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[23].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "생활용품") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[24] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[25] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[26] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[24].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[24].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[24].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[25].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[25].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[25].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[26].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[26].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[26].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "생필품") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[27] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[28] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[29] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[27].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[27].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[27].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[28].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[28].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[28].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[29].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[29].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[29].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "공구/DIY") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[30] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[31] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[32] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[30].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[30].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[30].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[31].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[31].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[31].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[32].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[32].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[32].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "인테리어시공") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[33] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[34] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[35] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[33].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[33].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[33].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[34].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[34].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[34].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[35].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[35].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[35].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "반려동물") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[36] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[37] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[38] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[36].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[36].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[36].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[37].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[37].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[37].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[38].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[38].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[38].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "캠핑용품") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[39] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[40] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[41] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[39].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[39].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[39].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[40].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[40].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[40].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[41].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[41].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[41].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "실내운동") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[42] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[43] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[44] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[42].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[42].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[42].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[43].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[43].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[43].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[44].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[44].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[44].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "유아/아동") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[45] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[46] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[47] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[45].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[45].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[45].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[46].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[46].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[46].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[47].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[47].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[47].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
            if (best2 == "렌탈") {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        bestList[48] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][0].ProductImage)}!!
//                        bestList[49] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][1].ProductImage) }!!
//                        bestList[50] = withContext(Dispatchers.IO) { ImageLoader.loadImage(response.result[0].BestProduct[1][2].ProductImage) }!!
//                    }
//                    bestInfoList[48].ProductName =response.result[0].BestProduct[1][0].ProductName
//                    bestInfoList[48].Cost =response.result[0].BestProduct[1][0].Cost
//                    bestInfoList[48].Discount =response.result[0].BestProduct[1][0].DiscountPercent
//                    bestInfoList[49].ProductName =response.result[0].BestProduct[1][1].ProductName
//                    bestInfoList[49].Cost =response.result[0].BestProduct[1][1].Cost
//                    bestInfoList[49].Discount =response.result[0].BestProduct[1][1].DiscountPercent
//                    bestInfoList[50].ProductName =response.result[0].BestProduct[1][2].ProductName
//                    bestInfoList[50].Cost =response.result[0].BestProduct[1][2].Cost
//                    bestInfoList[50].Discount =response.result[0].BestProduct[1][2].DiscountPercent
                setBest()
            }
        }
    }

    override fun onGetPopFailure(message: String) {
        showCustomToast("오류 : $message")
    }
    //url -> bitmap 이미지
    object ImageLoader{
        fun loadImage(imageUrl:String):Bitmap?{
            val bmp:Bitmap? = null
            try{
                val url = URL(imageUrl)
                val stream = url.openStream()
                return BitmapFactory.decodeStream(stream)
            }catch (e:MalformedURLException){
                e.printStackTrace()
            }catch (e:IOException){
                e.printStackTrace()
            }
            return bmp
        }
    }

}