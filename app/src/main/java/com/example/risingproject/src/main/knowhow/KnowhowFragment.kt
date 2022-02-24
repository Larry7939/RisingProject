package com.example.risingproject.src.main.knowhow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.risingproject.R
import com.example.risingproject.config.BaseFragment
import com.example.risingproject.databinding.FragmentKnowhowBinding
import com.example.risingproject.databinding.FragmentPopularityBinding

class KnowhowFragment : BaseFragment<FragmentKnowhowBinding>(FragmentKnowhowBinding::bind,R.layout.fragment_knowhow)
{
    fun newInstance(): Fragment {
        return KnowhowFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}