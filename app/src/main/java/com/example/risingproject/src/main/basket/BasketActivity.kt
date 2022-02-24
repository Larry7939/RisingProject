package com.example.risingproject.src.main.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.risingproject.R
import com.example.risingproject.config.ApplicationClass
import com.example.risingproject.config.BaseActivity
import com.example.risingproject.databinding.ActivityBasketBinding
import com.example.risingproject.src.main.detail.ProductActivityView
import com.example.risingproject.src.main.detail.ProductService
import com.example.risingproject.src.main.detail.models2.ProductResponse
import com.example.risingproject.src.main.popularity.PopularityFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat

class BasketActivity : BaseActivity<ActivityBasketBinding>(ActivityBasketBinding::inflate), ProductActivityView {
    private var productID = -1
    private var productNum = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productID = intent.getIntExtra("productId",-1)
        productNum = intent.getIntExtra("productNum",-1)
        Log.d("productId===========>","${productID}")
        if(productID!=-1){
            ProductService(this).tryProduct(5,intent.getIntExtra("productId",1))
            binding.basketEmptyCat.visibility = View.GONE
            binding.bastketEmptyMore.visibility = View.GONE
            binding.basketConst1.visibility = View.VISIBLE
            binding.basketConst2.visibility = View.VISIBLE
            binding.basketConst3.visibility = View.VISIBLE
        }
        else{
            binding.basketEmptyCat.visibility = View.VISIBLE
            binding.bastketEmptyMore.visibility = View.VISIBLE
            binding.basketConst1.visibility = View.GONE
            binding.basketConst2.visibility = View.GONE
            binding.basketConst3.visibility = View.GONE
        }
        ButtonSetting()
    }
    override fun onResume() {
        super.onResume()

        productID = ApplicationClass.bSharedPreferences.getInt("productId",-1)
        if(productID!=-1){
            ProductService(this).tryProduct(5,intent.getIntExtra("productId",1))
            binding.basketEmptyCat.visibility = View.GONE
            binding.bastketEmptyMore.visibility = View.GONE
            binding.basketConst1.visibility = View.VISIBLE
            binding.basketConst2.visibility = View.VISIBLE
            binding.basketConst3.visibility = View.VISIBLE
        }
    }
    override fun onPause() {
        super.onPause()
        val editor = ApplicationClass.bSharedPreferences.edit()
        editor.putInt("productId",productID)
        editor.commit()
    }
    private fun ButtonSetting(){
        binding.basketBack.setOnClickListener { finish() }
        binding.basketItemCancel.setOnClickListener {
            productID = -1
            binding.basketEmptyCat.visibility = View.VISIBLE
            binding.bastketEmptyMore.visibility = View.VISIBLE
            binding.basketConst1.visibility = View.GONE
            binding.basketConst2.visibility = View.GONE
            binding.basketConst3.visibility = View.GONE
        }
    }

    override fun onGetProductSuccess(response: ProductResponse) {
        val myFormatter = DecimalFormat("#,##0")

        CoroutineScope(Dispatchers.Main).launch {
            binding.basketItem1.setImageBitmap(withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(response.result[0].ProductImage[0].imageUrl)}!!)

        }
        binding.basketBrandName.text = "${response.result[0].ProductInfo[0].BrandName}샵 배송"
        binding.basketDecs.text = response.result[0].ProductInfo[0].ProductName
        binding.basketDeliveryCost.text = response.result[0].ProductInfo[0].DeliveryType
        binding.basketDeliveryType.text = response.result[0].ProductInfo[0].DeliveryCost
        //색상 intent로 받아와야함.
        if(productNum!=-1){
        binding.basketItemCount.text = productNum.toString()
        binding.basketItem1Cost.text = myFormatter.format((response.result[0].ProductInfo[0].Cost).replace(",","").toInt()*productNum)
        binding.basketItem1CostAll.text = myFormatter.format((response.result[0].ProductInfo[0].Cost).replace(",","").toInt()*productNum)
        binding.basketConst3Count.text = "1"
        binding.basketConst3CostDelivery.text = myFormatter.format(response.result[0].ProductInfo[0].DeliveryCost.replace(",","").replace("원","").toInt())
        binding.basketConst3CostSale.text = myFormatter.format(response.result[0].ProductInfo[0].SaleCost.replace(",","").toInt())
        binding.basketDeliveryCostTv.text= "배송비 착불 ${response.result[0].ProductInfo[0].DeliveryCost}"
        //총 상품금액 + 총 배송비 - 총 할인금액
        binding.basketConst3CostAll.text =
            myFormatter.format(((response.result[0].ProductInfo[0].Cost).replace(",","").toInt()*productNum)+(response.result[0].ProductInfo[0].DeliveryCost.replace(",","").replace("원","").toInt())-(response.result[0].ProductInfo[0].SaleCost.replace(",","").toInt()))

        binding.basketConst3CostItem.text = myFormatter.format((response.result[0].ProductInfo[0].Cost).replace(",","").toInt()*productNum)
        binding.basketConst3CostBottom.text = myFormatter.format((response.result[0].ProductInfo[0].Cost).replace(",","").toInt())
        }


        Log.d("BasketActivity에서 상세상품조회 try 성공","")
    }

    override fun onGetProductFailure(message: String) {
        TODO("Not yet implemented")
    }
}