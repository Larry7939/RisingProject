package com.example.risingproject.util

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.risingproject.R
import com.example.risingproject.databinding.LoadingDialog2Binding

class LoadingDialog(context: Context, loadingType:Int) : Dialog(context) {

    private lateinit var binding: LoadingDialog2Binding
    val handler = Handler()
    var loadingtype = loadingType
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = LoadingDialog2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
        if(loadingtype == 1){
            binding.loadingTv.text="로그인 중입니다."
        }
        else if(loadingtype == 2){
            binding.loadingTv.text="회원가입 중입니다."
        }
        else if(loadingtype ==3){
            binding.loadingTv.text="카카오와 연결중입니다."
        }

    }
    override fun show() {

        if(!this.isShowing) super.show()
        handler.post {
            var rotate = AnimationUtils.loadAnimation(context, R.anim.loading_anim_rotate)
            binding.loadingIcon.startAnimation(rotate)
        }
    }
}