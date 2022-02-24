package com.example.risingproject.src.main.houses

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.risingproject.R
import com.example.risingproject.config.BaseFragment
import com.example.risingproject.databinding.FragmentHousesBinding
import com.example.risingproject.src.main.detail.DetailActivity

class HousesFragment : BaseFragment<FragmentHousesBinding>(FragmentHousesBinding::bind,R.layout.fragment_houses)
{
    private var sortmode=0

    fun newInstance(): Fragment {
        return HousesFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sortMode()
        toDetailActivity()
    }
    fun sortMode(){
        binding.housesSortBtn.setOnClickListener {
            if (sortmode == 0) {
                binding.housesSortBtn.setImageResource(R.drawable.houses_sort1)
                binding.housesConstCenter1.visibility = View.VISIBLE
                binding.housesConstCenter2.visibility = View.GONE
                sortmode=1
            } else if (sortmode == 1) {
                binding.housesSortBtn.setImageResource(R.drawable.houses_sort2)
                binding.housesConstCenter1.visibility = View.GONE
                binding.housesConstCenter2.visibility = View.VISIBLE
                sortmode=0
            }
        }
    }
    private fun toDetailActivity(){
        binding.housesCd1.setOnClickListener {
            startActivity(Intent(activity, DetailActivity()::class.java))
        }
        binding.housesCda.setOnClickListener {
            startActivity(Intent(activity, DetailActivity()::class.java))
        }
    }
}