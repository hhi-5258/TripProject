package com.hhi.tripproject.view.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.hhi.tripproject.R
import com.hhi.tripproject.base.BaseActivity
import com.hhi.tripproject.databinding.ActivityMainBinding
import com.hhi.tripproject.global.App.Companion.context
import com.hhi.tripproject.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val vm: MainViewModel by viewModels()
    private val adapter = MainRecyclerAdapter()
    private var currentCircleId = -1
    private val menuViewGroupId = View.generateViewId()
    private lateinit var rootLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = vm

        setUpUi()

//        vm.getTourList(vm.getUserIdx())
        vm.getTourList(1)

    }

    private fun setUpUi() {
        binding.mainRecycler.setHasFixedSize(false)
        binding.mainRecycler.adapter = adapter

        val bottomNavi = binding.mainBottomNavigation

//        for (i in 0 until bottomNavi.childCount) {
//            val iconView = bottomNavi.getChildAt(i) as BottomNavigationMenuView
//            val layoutParams = iconView.layoutParams
//            val displayMetrics = resources.displayMetrics
//            // set your height here
//            layoutParams.height =
//                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32f, displayMetrics).toInt()
//            // set your width here
//            layoutParams.width =
//                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32f, displayMetrics).toInt()
//            iconView.layoutParams = layoutParams
//        }

//        val menuViewGroup = bottomNavi.getChildAt(0) as BottomNavigationMenuView
//        menuViewGroup.id = menuViewGroupId
//        rootLayout = ConstraintLayout(context)
//        bottomNavi.removeView(menuViewGroup)
//        rootLayout.addView(menuViewGroup)
//        bottomNavi.addView(rootLayout)
//
//        // Navigate new selection in
//        val circleView = buildBackgroundCircle()
//        currentCircleId = circleView.id
//        rootLayout.addView(circleView)
//        findViewById<BottomNavigationMenuView>(menuViewGroupId).bringToFront()
//
//        setCircleSizeAndPosition(
//            circleView,
//            5,
//            50,
//            10f
//        )

    }

    private fun buildBackgroundCircle(): ImageView {
        val circleDrawable = ContextCompat.getDrawable(context, R.drawable.navigation_item_background)
        circleDrawable?.setTint(Color.GREEN)
        val circleView = ImageView(context)
        circleView.id = View.generateViewId()
        circleView.setImageDrawable(circleDrawable)
        circleView.alpha = 0F

        return circleView
    }

    private fun setCircleSizeAndPosition(
        circleView: ImageView,
        paddingBottom: Int,
        size: Int,
        x: Float
    ) {
        val params = circleView.layoutParams
        circleView.setPadding(0, 0, 0, paddingBottom / 3)
        params.width = size
        params.height = size
        circleView.layoutParams = params
        circleView.x = x
    }

}