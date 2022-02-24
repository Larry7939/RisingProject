package com.example.risingproject.src.main.detail

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import com.example.risingproject.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.DecimalFormat


class BottomSheet() : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sheetBackground = view?.findViewById<ImageView>(R.id.sheet_background)
        val selectItem = view?.findViewById<ScrollView>(R.id.sheet_color_item1)
        val selectItemTv = view?.findViewById<TextView>(R.id.sheet_color_item1_tv)
        val selectedItemInfo = view?.findViewById<CardView>(R.id.sheet_selected_item_info)
        val selectedItemCancel = view?.findViewById<ImageView>(R.id.sheet_selected_item_cancel)

        val sheetColorItemCost = view?.findViewById<TextView>(R.id.sheet_color_item1_cost)
        val sheetSelectedItemCost = view?.findViewById<TextView>(R.id.sheet_selected_item_cost)
        val itemCountPlus = view?.findViewById<ImageView>(R.id.sheet_selected_item_plus)
        val itemCountminus = view?.findViewById<ImageView>(R.id.sheet_selected_item_minus)
        val itemCount = view?.findViewById<TextView>(R.id.sheet_selected_item_count)
        val sheetAllCost = view?.findViewById<TextView>(R.id.sheet_cost_all)
        val item1Cost = 299900

        val myFormatter = DecimalFormat("#,##0")


        //색상선택 메뉴 바
        view?.findViewById<TextView>(R.id.sheet_color)?.setOnClickListener {
            if (selectItem != null) {
                if(selectItem.visibility==View.GONE){
                    selectItem.getChildAt(0).startAnimation(
                            AnimationUtils.loadAnimation(
                                context, R.anim.scrollviewopen
                            )
                        )
                    selectItem.visibility = View.VISIBLE
                } else if(selectItem.visibility==View.VISIBLE){
                    selectItem.visibility = View.GONE
                }
            }
        }

        selectItemTv?.setOnClickListener {
            //회색 화면 VISIBLE 설정 및 금액 올라가게 설정
            //회색화면 GONE설정은 따로 정해진 X버튼을 통해 한다. 그리고 금액도 원래대로 돌려놓기
            if (selectItem != null) {
                selectItem.visibility = View.GONE
            }
            if (selectedItemInfo != null) {
                selectedItemInfo.visibility = View.VISIBLE
                var layout = sheetBackground
                var layoutParam = layout?.layoutParams
                if (layoutParam != null) {
                    layoutParam.height = dpTopx(310)
                }
                itemCount?.text="1"
                sheetAllCost?.text = myFormatter.format(item1Cost.toString().toInt())
                setItemCount(sheetColorItemCost,sheetSelectedItemCost,itemCountPlus,itemCountminus,itemCount,sheetAllCost,item1Cost)
            }
        }

        //가격 plus minus 버튼 구현
        setItemCount(sheetColorItemCost,sheetSelectedItemCost,itemCountPlus,itemCountminus,itemCount,sheetAllCost,item1Cost)

        //취소버튼 - background height낮추고 Gone으로 변경
        selectedItemCancel?.setOnClickListener {
            if (selectedItemInfo != null) {
                selectedItemInfo.visibility = View.GONE
            }
            var layout = sheetBackground
            var layoutParam = layout?.layoutParams
            if (layoutParam != null) {
                layoutParam.height = dpTopx(240)
            }
            sheetAllCost?.text = "0"
        }
        view?.findViewById<TextView>(R.id.sheet_basket)?.setOnClickListener {
            if(itemCount?.text.toString().toInt()==0){
                Toast.makeText(context, "상품 옵션을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                (activity as ProductActivity?)?.purchaseFinished()
                (activity as ProductActivity?)?.isBasket = true
                (activity as ProductActivity?)?.productNum = itemCount?.text.toString().toInt()
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                fragmentManager.beginTransaction().remove(this@BottomSheet).commit()
                fragmentManager.popBackStack()
            }
        }
    }
    fun dpTopx(dp:Int):Int{
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val dpi = displayMetrics.densityDpi
        val density = displayMetrics.density
        return (dp*density+0.5).toInt()
    }
    fun setItemCount(sheetColorItemCost: TextView?, sheetSelectedItemCost: TextView?, itemCountPlus: ImageView?, itemCountminus: ImageView?, itemCount: TextView?, sheetAllCost: TextView?, item1Cost: Int) {
        val myFormatter = DecimalFormat("#,##0")
        if (sheetSelectedItemCost != null &&sheetColorItemCost != null) {
            sheetSelectedItemCost.text = myFormatter.format(item1Cost.toString().toInt())

            itemCountPlus?.setOnClickListener {
                itemCount?.text = (itemCount?.text.toString().toInt()+ 1).toString()
                sheetSelectedItemCost.text=myFormatter.format(itemCount?.text.toString().toInt() *item1Cost)
                sheetAllCost?.text = myFormatter.format(itemCount?.text.toString().toInt() *item1Cost)
            }
            itemCountminus?.setOnClickListener {
                if(itemCount?.text.toString().toInt()>1){
                itemCount?.text = (itemCount?.text.toString().toInt()-1).toString()
                sheetSelectedItemCost.text=myFormatter.format(itemCount?.text.toString().toInt() *item1Cost)
                sheetAllCost?.text = myFormatter.format(itemCount?.text.toString().toInt() *item1Cost)
                }
                else{
                    Toast.makeText(context, "최소 한 개 이상의 제품 개수를 입력해주세요!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}