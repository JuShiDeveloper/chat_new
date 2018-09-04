package com.jushi.base.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import com.jushi.share.R

open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {
    /**
     * 顶部向下偏移量
     */
    private var topOffset = 0
    private lateinit var behavior: BottomSheetBehavior<FrameLayout>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if (context == null) {
            super.onCreateDialog(savedInstanceState)
        } else {
            BottomSheetDialog(context!!, R.style.TransparentBottomSheetStyle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.delegate.findViewById<FrameLayout>(android.support.design.R.id.design_bottom_sheet)
        if (bottomSheet != null) {
//            val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
//            layoutParams.height = getHeight()
            behavior = BottomSheetBehavior.from(bottomSheet)
            //初始为全部展开状态
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    /**
     * 屏幕的高度
     */
    private fun getHeight(): Int {
        var height = 1920
        if (context != null) {
            val wm = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point()
            if (wm != null) {
                wm.defaultDisplay.getSize(point) // 减去了状态栏高度
                height = point.y - getTopOffset()
            }
        }
        return height
    }

    private fun getTopOffset(): Int = topOffset

    fun setTopOffset(topOffset: Int) {
        this.topOffset = topOffset
    }

    fun getBehavior(): BottomSheetBehavior<FrameLayout> = behavior

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}