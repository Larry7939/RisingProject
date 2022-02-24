package com.example.risingproject.src.main.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.example.risingproject.R
import com.example.risingproject.config.BaseFragment
import com.example.risingproject.databinding.FragmentPopularityBinding
import com.example.risingproject.databinding.FragmentProductTabBinding
import com.example.risingproject.src.main.popularity.PopularityFragment
import com.example.risingproject.src.main.popularity.PopularityFragmentView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductTabFragment : BaseFragment<FragmentProductTabBinding>(FragmentProductTabBinding::bind,R.layout.fragment_product_tab)
{

    fun newInstance(): Fragment {
        return ProductTabFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = arguments
        var urlArr:ArrayList<String> = arrayListOf()
        if (bundle != null) {
            for(i in bundle.getStringArrayList("introUrl")!!){
                urlArr.add(i)
            }
            CoroutineScope(Dispatchers.Main).launch {
                binding.productTabFragment1Img1.setImageBitmap(withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(urlArr[0])}!!)
                binding.productTabFragment1Img2.setImageBitmap(withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(urlArr[1])}!!)
                binding.productTabFragment1Img3.setImageBitmap(withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(urlArr[2])}!!)
                binding.productTabFragment1Img4.setImageBitmap(withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(urlArr[3])}!!)
                binding.productTabFragment1Img5.setImageBitmap(withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(urlArr[4])}!!)
                binding.productTabFragment1Img6.setImageBitmap(withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(urlArr[5])}!!)
                binding.productTabFragment1Img7.setImageBitmap(withContext(Dispatchers.IO) { PopularityFragment.ImageLoader.loadImage(urlArr[6])}!!)
            }
        }
        binding.productFrmExpandBtn.setOnClickListener {
        binding.productFrmExpandBtn.visibility = View.GONE
        var layout = binding.productFragmentFrmlayout
        var layoutParam = layout.layoutParams
        layoutParam.height = MATCH_PARENT
        }

    }

}