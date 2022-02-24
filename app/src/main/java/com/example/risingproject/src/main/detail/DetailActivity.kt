package com.example.risingproject.src.main.detail

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.risingproject.R
import com.example.risingproject.config.BaseActivity
import com.example.risingproject.databinding.ActivityDetailBinding
import com.example.risingproject.src.main.MainActivity
import com.example.risingproject.src.main.detail.models.DetailHousesResponse
import com.example.risingproject.src.main.popularity.PopularityFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate),DetailActivityView {

    private var backgroundImage:Bitmap? =null
    private var userProfileImage:Bitmap? = null
    private var previewImage:Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doFullScreen()
        binding.detailBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.detailHome.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        DetailService(this).tryDetail(1)
        binding.detailPreviewImg11.setOnClickListener {
            val intent = Intent(this,ProductActivity::class.java)
            intent.putExtra("productNum",1)
            startActivity(intent)
        }

    }
    //full screen전환
    private fun doFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    override fun onGetDetailSuccess(response: DetailHousesResponse) {
        CoroutineScope(Dispatchers.Main).launch {
            backgroundImage = withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(response.result[0].HouseWarmInfo[0].Image)}!!
            userProfileImage = withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(response.result[0].HouseWarmInfo[0].UserProfileImage)}!!
//            previewImage = withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(response.result[0].HouseWarmContents[0][0].ProductImage)}!!
            binding.detailBackground.setImageBitmap(backgroundImage)
            binding.detailUserProfileImg.setImageBitmap(userProfileImage)
            binding.detailType.text = response.result[0].HouseWarmInfo[0].Type
            binding.detailUserNickname.text = response.result[0].HouseWarmInfo[0].UserNickName
//            binding.detailUserCreateAt.text = response.result[0].HouseWarmInfo[0].UserCreatedAt
            binding.detailInfo11.text = response.result[0].HouseWarmInfo[0].BuildingType
            binding.detailInfo21.text = response.result[0].HouseWarmInfo[0].Width
            binding.detailInfo31.text = response.result[0].HouseWarmInfo[0].Worker
            binding.detailInfo41.text = response.result[0].HouseWarmInfo[0].Area
            binding.detailInfo61.text = response.result[0].HouseWarmInfo[0].FamilyType
//            binding.detailPreviewImg1.setImageBitmap(previewImage)
        }
    }
    override fun onGetDetailFailure(message: String) {}
}