package com.example.risingproject.src.main.qna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.risingproject.R
import com.example.risingproject.config.BaseFragment
import com.example.risingproject.databinding.FragmentPhotoBinding
import com.example.risingproject.databinding.FragmentQnaBinding

class QnaFragment : BaseFragment<FragmentQnaBinding>(FragmentQnaBinding::bind,R.layout.fragment_qna)
{
    fun newInstance(): Fragment {
        return QnaFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}