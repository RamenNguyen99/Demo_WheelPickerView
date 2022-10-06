package com.example.demo_wheelpickerview

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.example.demo_wheelpickerview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), OnOptionsSelectListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var optionsPickerView: OptionsPickerView<String>
    private var indexSelected = 0
    private val listExample = listOf("Manh", "Tuong", "Vuong", "Dinh")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCustomLayoutPickerView()
        initListener()
    }

    override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
        indexSelected = options1
    }

    private fun initCustomLayoutPickerView() {
        if (!this@MainActivity::optionsPickerView.isInitialized) {
            // init & handle onClick button, option
            optionsPickerView = OptionsPickerBuilder(
                this,
                this

                /* listener nay tuong tu nhu xu ly OnClick,
                 neu k lam nhu nay thi co the doi "this" bang object như ben duoi,
                 luc nay no se vao ham override fun onOptionsSelect() */

//                object : OnOptionsSelectListener {
//                    override fun onOptionsSelect(
//                        options1: Int,
//                        options2: Int,
//                        options3: Int,
//                        v: View?
//                    ) {
//                        indexSelected = options1
//                    }
//                }
            )
                .setLayoutRes(R.layout.custom_layout_option_picker_view) { v ->
                    val tvExit = v.findViewById<TextView>(R.id.tvCancel)
                    val tvFinish = v.findViewById<TextView>(R.id.tvFinish)
                    tvFinish.setOnClickListener {
                        optionsPickerView.run {
                            returnData()
                            dismiss()
                        }
                        binding.tvOption.text = listExample[indexSelected]
                    }
                    tvExit.setOnClickListener {
                        optionsPickerView.dismiss()
                    }
                }
                .setTextColorCenter(Color.GRAY)
                .setDividerColor(Color.GRAY)
                .isCenterLabel(true)
                .isDialog(true)
                .setContentTextSize(15)
                .setOutSideCancelable(true)
                .build()
        }

        // init Layout picker
        with(optionsPickerView) {
            dialogContainerLayout.layoutParams = android.widget.FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.BOTTOM
            )
            this.dialog.window?.apply {
                setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)
                attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
                setGravity(Gravity.BOTTOM)
            }
        }
    }

    private fun initListener() {
        binding.tvOption.setOnClickListener {
            optionsPickerView.apply {
                setPicker(listExample)
                setSelectOptions(indexSelected)
            }
            optionsPickerView.show()
        }
    }
}
