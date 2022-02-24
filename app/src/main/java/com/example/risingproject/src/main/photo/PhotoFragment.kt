package com.example.risingproject.src.main.photo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.risingproject.R
import com.example.risingproject.config.BaseFragment
import com.example.risingproject.databinding.FragmentPhotoBinding

class PhotoFragment : BaseFragment<FragmentPhotoBinding>(FragmentPhotoBinding::bind,R.layout.fragment_photo)
{
    fun newInstance(): Fragment {
        return PhotoFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}