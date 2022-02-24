package com.example.risingproject.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.risingproject.src.main.expert.ExpertFragment
import com.example.risingproject.src.main.houses.HousesFragment
import com.example.risingproject.src.main.knowhow.KnowhowFragment
import com.example.risingproject.src.main.photo.PhotoFragment
import com.example.risingproject.src.main.popularity.PopularityFragment
import com.example.risingproject.src.main.qna.QnaFragment


class FragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        val pf = PopularityFragment()
        val hf = HousesFragment()
        val photof = PhotoFragment()
        val kf = KnowhowFragment()
        val ef = ExpertFragment()
        val qf = QnaFragment()
        return when (position) {
            0 -> pf.newInstance()
            1 -> photof.newInstance()
            2 -> hf.newInstance()
            3 -> kf.newInstance()
            4 -> ef.newInstance()
            5 -> qf.newInstance()
            else -> pf.newInstance()
        }
    }

    override fun getCount(): Int {
        return 6
    }
}