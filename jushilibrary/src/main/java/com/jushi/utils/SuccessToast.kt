package com.jushi.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.jushi.share.R

/**
 * 成功后显示的提示View
 */
object SuccessToast {
    private lateinit var toast: Toast
    private lateinit var toastBuilder: ToastBuilder

    fun makeToast(context: Context): ToastBuilder {
        val rootView = View.inflate(context, R.layout.success_toast_layout, null)
        val layout = rootView.findViewById<LinearLayout>(R.id.toast_layout)
        val params = layout.layoutParams
        params.width = context.resources.displayMetrics.widthPixels
        layout.layoutParams = params
        toast = Toast(context)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = rootView
        toastBuilder = ToastBuilder(rootView)
        return toastBuilder
    }

    class ToastBuilder(val rootView: View) {
        private var tvToastMsg: TextView = rootView.findViewById(R.id.toast_msg)

        fun show(msg: String) {
            tvToastMsg.text = msg
            toast.show()
        }

        fun show() {
            toast.show()
        }
    }
}