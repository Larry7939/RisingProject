package com.example.risingproject.src.main.popularity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingproject.databinding.FragmentPopularityViewpagerBestBinding

class PopularityVPAforBest(bestList:ArrayList<Bitmap>,bestInfoList:ArrayList<BestInfo>): RecyclerView.Adapter<PagerViewHolder2>() {
    var item = bestList
    var itemTv = bestInfoList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder2 {
        val binding = FragmentPopularityViewpagerBestBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PagerViewHolder2(binding)
    }
    override fun onBindViewHolder(holder: PagerViewHolder2, position: Int) {
        holder.setView(item,itemTv,position)
    }
    override fun getItemCount(): Int{
      return 17
    }
}
class PagerViewHolder2(private val binding:FragmentPopularityViewpagerBestBinding) : RecyclerView.ViewHolder(binding.root) {

    fun setView(bestArr:ArrayList<Bitmap>, bestInfoList:ArrayList<BestInfo>, position: Int) {
        Log.d("setView 호출" ,"-> position : $position")
        Log.d("setView 호출" ,"${bestArr[0]},${bestArr[1]},${bestArr[2]},${bestArr[3]}")
        if(position ==0) {
            Log.d("position 0 호출" ,"======")
            Log.d("setView 호출 if문 내부" ,"${bestArr[0]},${bestArr[1]},${bestArr[2]},${bestArr[3]}")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[0])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+1])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+2])
            binding.popularityViewpagerTv1.text = bestInfoList[0].ProductName
            binding.popularityViewpagerTv2.text = bestInfoList[0].Discount
            binding.popularityViewpagerTv3.text = bestInfoList[0].Cost
            binding.popularityViewpagerTv4.text = bestInfoList[1].ProductName
            binding.popularityViewpagerTv5.text = bestInfoList[1].Discount
            binding.popularityViewpagerTv6.text = bestInfoList[1].Cost
            binding.popularityViewpagerTv7.text = bestInfoList[2].ProductName
            binding.popularityViewpagerTv8.text = bestInfoList[2].Discount
            binding.popularityViewpagerTv9.text = bestInfoList[2].Cost
        }
        else if(position==1){
            Log.d("position 1 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+2])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+3])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+4])
            binding.popularityViewpagerTv1.text = bestInfoList[3].ProductName
            binding.popularityViewpagerTv2.text = bestInfoList[3].Discount
            binding.popularityViewpagerTv3.text = bestInfoList[3].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[4].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[4].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[4].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[5].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[5].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[5].Cost
        }
        else if(position==2){
            Log.d("position 2 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+4])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position +5])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position +6])
//            binding.popularityViewpagerTv1.text = bestInfoList[6].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[6].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[6].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[7].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[7].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[7].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[8].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[8].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[8].Cost
        }
        else if(position==3){
            Log.d("position 3 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+6])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position +7])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position +8])
//            binding.popularityViewpagerTv1.text = bestInfoList[9].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[9].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[9].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[10].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[10].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[10].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[11].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[11].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[11].Cost
        }
        else if(position==4){
            Log.d("position 4 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+8])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position +9])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position +10])
//            binding.popularityViewpagerTv1.text = bestInfoList[12].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[12].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[12].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[13].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[13].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[13].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[14].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[14].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[14].Cost
        }
        else if(position==5){
            Log.d("position 5 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+10])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position +11])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position +12])
//            binding.popularityViewpagerTv1.text = bestInfoList[15].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[15].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[15].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[16].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[16].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[16].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[17].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[17].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[17].Cost
        }
        else if(position==6){
            Log.d("position 6 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+12])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+13])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+14])
//            binding.popularityViewpagerTv1.text = bestInfoList[18].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[18].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[18].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[19].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[19].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[19].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[20].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[20].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[20].Cost
        }
        else if(position==7){
            Log.d("position 7 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+14])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+15])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+16])
//            binding.popularityViewpagerTv1.text = bestInfoList[21].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[21].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[21].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[22].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[22].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[22].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[23].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[23].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[23].Cost
        }
        else if(position==8){
            Log.d("position 8 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+16])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position +17])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position +18])
//            binding.popularityViewpagerTv1.text = bestInfoList[24].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[24].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[24].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[25].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[25].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[25].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[26].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[26].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[26].Cost
        }
        else if(position==9){
            Log.d("position 9 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+18])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+19])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+20])
//            binding.popularityViewpagerTv1.text = bestInfoList[27].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[27].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[27].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[28].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[28].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[28].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[29].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[29].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[29].Cost
        }
        else if(position==10){
            Log.d("position 10 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+20])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+21])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+22])
//            binding.popularityViewpagerTv1.text = bestInfoList[30].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[30].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[30].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[31].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[31].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[31].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[32].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[32].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[32].Cost
        }
        else if(position==11){
            Log.d("position 11 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+22])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position +23])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position +24])
//            binding.popularityViewpagerTv1.text = bestInfoList[33].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[33].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[33].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[34].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[34].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[34].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[35].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[35].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[35].Cost
        }
        else if(position==12){
            Log.d("position 12 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+24])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+25])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+26])
//            binding.popularityViewpagerTv1.text = bestInfoList[36].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[36].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[36].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[37].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[37].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[37].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[38].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[38].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[38].Cost
        }
        else if(position==13){
            Log.d("position 13 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+26])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+27])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+28])
//            binding.popularityViewpagerTv1.text = bestInfoList[39].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[39].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[39].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[40].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[40].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[40].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[41].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[41].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[41].Cost
        }
        else if(position==14){
            Log.d("position 14 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+28])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+29])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+30])
//            binding.popularityViewpagerTv1.text = bestInfoList[42].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[42].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[42].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[43].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[43].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[43].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[44].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[44].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[44].Cost
        }
        else if(position==15){
            Log.d("position 15 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+30])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+31])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+32])
//            binding.popularityViewpagerTv1.text = bestInfoList[45].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[45].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[45].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[46].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[46].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[46].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[47].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[47].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[47].Cost
        }
        else if(position==16){
            Log.d("position 16 호출" ,"======")
            binding.popularityBestPhotoIv1.setImageBitmap(bestArr[position+32])
            binding.popularityBestPhotoIv2.setImageBitmap(bestArr[position+33])
            binding.popularityBestPhotoIv3.setImageBitmap(bestArr[position+34])
//            binding.popularityViewpagerTv1.text = bestInfoList[48].ProductName
//            binding.popularityViewpagerTv2.text = bestInfoList[48].Discount
//            binding.popularityViewpagerTv3.text = bestInfoList[48].Cost
//            binding.popularityViewpagerTv4.text = bestInfoList[49].ProductName
//            binding.popularityViewpagerTv5.text = bestInfoList[49].Discount
//            binding.popularityViewpagerTv6.text = bestInfoList[49].Cost
//            binding.popularityViewpagerTv7.text = bestInfoList[50].ProductName
//            binding.popularityViewpagerTv8.text = bestInfoList[50].Discount
//            binding.popularityViewpagerTv9.text = bestInfoList[50].Cost
        }

    }
}