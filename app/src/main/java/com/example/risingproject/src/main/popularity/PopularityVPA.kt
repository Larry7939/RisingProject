package com.example.risingproject.src.main.popularity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingproject.databinding.FragmentPopularityViewpagerBannerBinding

class PopularityVPA(bannerList:ArrayList<Int>):RecyclerView.Adapter<PagerViewHolder>() {
    var item = bannerList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = FragmentPopularityViewpagerBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PagerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.setView(item[position%17])
    }
    override fun getItemCount(): Int = Int.MAX_VALUE	// 아이템의 갯수를 임의로 확 늘린다.

}
class PagerViewHolder(private val binding: FragmentPopularityViewpagerBannerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setView(bannerArr:Int) {
            binding.popularityBanner.setBackgroundResource(bannerArr)
    }
}