package com.example.risingproject.src.main.expert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.risingproject.R
import com.example.risingproject.config.BaseFragment
import com.example.risingproject.databinding.FragmentExpertBinding
import com.example.risingproject.databinding.FragmentPopularityBinding

class ExpertFragment : BaseFragment<FragmentExpertBinding>(FragmentExpertBinding::bind,R.layout.fragment_expert)
{
    fun newInstance(): Fragment {
        return ExpertFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}