package com.example.risingproject.src.main.detail

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentTransaction
import com.example.risingproject.R
import com.example.risingproject.config.BaseActivity
import com.example.risingproject.databinding.ActivityProductBinding
import com.example.risingproject.src.main.MainActivity
import com.example.risingproject.src.main.basket.BasketActivity
import com.example.risingproject.src.main.detail.models2.ProductResponse
import com.example.risingproject.src.main.popularity.PopularityFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductActivity : BaseActivity<ActivityProductBinding>(ActivityProductBinding::inflate),ProductActivityView{
    private var productTitle = ""
    private var productintro:ArrayList<String> = arrayListOf()
    private var bundle = Bundle()
    var productId:Int = -1
    var productNum:Int = -1
    var isBasket:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProductService(this).tryProduct(5,intent.getIntExtra("productNum",1))
        SelectStyle()
        setReview()
        purchase()
        setTopButtons()
    }
    override fun onGetProductSuccess(response: ProductResponse) {
        binding.productBrand.text = response.result[0].ProductInfo[0].BrandName
        binding.productTitle2.text = response.result[0].ProductInfo[0].ProductName
        binding.productStar.setStar(response.result[0].ProductInfo[0].StarGrade.toFloat())
        binding.productReviewCount.text = "(${response.result[0].ProductInfo[0].ReviewCount})"
        binding.productDiscount.text = response.result[0].ProductInfo[0].Discount
        binding.productCost.text = response.result[0].ProductInfo[0].Cost
        binding.productSaleCost.text = "${response.result[0].ProductInfo[0].SaleCost}원"
//            binding.productBenefitPoint.text = response.result[0].ProductInfo[0].Benefit
        binding.productDeliveryCost.text = response.result[0].ProductInfo[0].DeliveryCost
        binding.productDeliveryType.text = response.result[0].ProductInfo[0].DeliveryType
        productTitle = response.result[0].ProductInfo[0].ProductName
        if(productTitle.length>15){
            productTitle= productTitle.substring(0,15)
            productTitle= "$productTitle..."
            binding.productTitle.text = productTitle
        }
        else{
            binding.productTitle.text = productTitle
        }
        binding.productCost.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//        binding.productUsersStylingNickname1.text = response.result[0].ProductStylingShot[0].UserNickName
        CoroutineScope(Dispatchers.Main).launch {
            binding.productImage.setImageBitmap(withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(response.result[0].ProductImage[0].imageUrl)}!!)
//            binding.productUsersStylingIv1.setImageBitmap(withContext(Dispatchers.IO){PopularityFragment.ImageLoader.loadImage(response.result[0].ProductStylingShot[0].Image)}!!)
//            binding.productUsersStylingProfile1.setImageBitmap(withContext(Dispatchers.IO){PopularityFragment.ImageLoader.loadImage(response.result[0].ProductStylingShot[0].ProfileImage)}!!)
        }
        if(productintro.isEmpty()) {
            for (i in 0..6) {
                productintro.add(response.result[0].ProductIntro[i].imageUrl)
            }
        }
        bundle.putStringArrayList("introUrl",productintro)
        callFragment(1,bundle)
        productId = response.result[0].ProductInfo[0].ProductId
    }
    override fun onGetProductFailure(message: String) {
    }
    private fun SelectStyle(){
        binding.productUsersStylingCv1.setOnClickListener{
            binding.productUsersStylingIv1.setImageResource(R.drawable.product_user_styling_img1)
            binding.productUsersStylingCv1Selected.visibility = View.VISIBLE
            binding.productUsersStylingCv2Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv3Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv4Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv5Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv6Selected.visibility = View.INVISIBLE
            binding.productUsersStylingNickname1.text = "como__"
            binding.productUsersStylingProfile1.setImageResource(R.drawable.product_users_styling_profile_img1)
            binding.productUsersStylingCurrpage.text = "1"
        }
        binding.productUsersStylingCv2.setOnClickListener{
            binding.productUsersStylingIv1.setImageResource(R.drawable.product_user_styling_img2)
            binding.productUsersStylingCv1Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv2Selected.visibility = View.VISIBLE
            binding.productUsersStylingCv3Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv4Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv5Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv6Selected.visibility = View.INVISIBLE
            binding.productUsersStylingNickname1.text = "모콩네"
            binding.productUsersStylingProfile1.setImageResource(R.drawable.product_users_styling_profile_img2)
            binding.productUsersStylingCurrpage.text = "2"

        }
        binding.productUsersStylingCv3.setOnClickListener{
            binding.productUsersStylingIv1.setImageResource(R.drawable.product_user_styling_img3)
            binding.productUsersStylingCv1Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv2Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv3Selected.visibility = View.VISIBLE
            binding.productUsersStylingCv4Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv5Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv6Selected.visibility = View.INVISIBLE
            binding.productUsersStylingNickname1.text = "moonglaf"
            binding.productUsersStylingProfile1.setImageResource(R.drawable.product_users_styling_profile_img3)
            binding.productUsersStylingCurrpage.text = "3"

        }
        binding.productUsersStylingCv4.setOnClickListener{
            binding.productUsersStylingIv1.setImageResource(R.drawable.product_user_styling_img4)
            binding.productUsersStylingCv1Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv2Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv3Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv4Selected.visibility = View.VISIBLE
            binding.productUsersStylingCv5Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv6Selected.visibility = View.INVISIBLE
            binding.productUsersStylingNickname1.text = "동별이네"
            binding.productUsersStylingProfile1.setImageResource(R.drawable.product_users_styling_profile_img4)
            binding.productUsersStylingCurrpage.text = "4"
        }
        binding.productUsersStylingCv5.setOnClickListener{
            binding.productUsersStylingIv1.setImageResource(R.drawable.product_user_styling_img5)
            binding.productUsersStylingCv1Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv2Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv3Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv4Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv5Selected.visibility = View.VISIBLE
            binding.productUsersStylingCv6Selected.visibility = View.INVISIBLE
            binding.productUsersStylingNickname1.text = "Jinsook.c"
            binding.productUsersStylingProfile1.setImageResource(R.drawable.product_users_styling_profile_img5)

            binding.productUsersStylingCurrpage.text = "5"
        }
        binding.productUsersStylingCv6.setOnClickListener{
            binding.productUsersStylingIv1.setImageResource(R.drawable.product_user_styling_img6)
            binding.productUsersStylingCv1Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv2Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv3Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv4Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv5Selected.visibility = View.INVISIBLE
            binding.productUsersStylingCv6Selected.visibility = View.VISIBLE
            binding.productUsersStylingNickname1.text = "Larry"
            binding.productUsersStylingProfile1.setImageResource(R.drawable.product_users_styling_profile_img6)
            binding.productUsersStylingCurrpage.text = "6"
        }
    }
    private fun callFragment(frament_no: Int,bundle:Bundle) {

        // 프래그먼트 사용을 위해
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when (frament_no) {
            1 -> {
                // '프래그먼트1' 호출
                val fragment1 = ProductTabFragment()
                transaction.replace(R.id.product_frm, fragment1)
                transaction.commit()
                Log.d("bundle=========","${bundle}")
                fragment1.arguments = bundle
            }
        }
    }
    private fun setReview(){
        binding.productReviewStar.setStar(5F)
        binding.productReviewContents1Star.setStar(5F)
        binding.productReviewContents2Star.setStar(5F)

    }
    private fun purchase(){
        binding.productBottomPurchaseBtn.setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }
    fun purchaseFinished(){
        binding.productPurchaseFinished.visibility = View.VISIBLE
        binding.productPurchaseFinished.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fadeout))
        binding.productPurchaseFinished.visibility = View.INVISIBLE
        //여기에서 프래그먼트에서 보내준 정보를 인텐트에 담아놓고, 장바구니 버튼을 클릭하면 해당 intent를 이용해서 장바구니로 이동하도록 한다.
        //이 함수를 이용해서 어느 액티비티에서든 호출만 하면 인텐트를 보낼 수 있도록
    }
    private fun setTopButtons(){

        //뒤로가기 버튼
        binding.productBack.setOnClickListener {
            val intent = Intent(this,DetailActivity::class.java)
            startActivity(intent)
        }
        //홈버튼
        binding.productHome.setOnClickListener {
            val intent =Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.productBasket.setOnClickListener {
            val intent =Intent(this, BasketActivity::class.java)
            if(isBasket){
                intent.putExtra("productId",productId)
            }
            else{
                intent.putExtra("productId",-1)
            }
            intent.putExtra("productNum",productNum) //BottomSheetFragment에서 넘겨받음
            startActivity(intent)
        }
    }

}