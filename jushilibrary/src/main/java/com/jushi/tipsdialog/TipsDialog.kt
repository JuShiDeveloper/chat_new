package com.jushi.tipsdialog

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import com.jushi.base.dialog.BaseDialog
import com.jushi.share.R
import kotlinx.android.synthetic.main.exit_dialog_layout.*

/**
 * 弹窗
 */
class TipsDialog : BaseDialog {
    private lateinit var listener: OnDropBtnClickListener
    private lateinit var adapter: TipsDialogAdapter
    private var type: Any = "type"
    private var okBtnText: String = context.getString(R.string.tips_dialog_Drop)
    private lateinit var builder: TipsBuilder

    constructor(context: Context, listener: OnDropBtnClickListener) : super(context) {
        this.listener = listener
        setDialogSystemLine()
        init(R.layout.exit_dialog_layout)
        builder = TipsBuilder()
    }

    /**
     * 适配低版本顶部的出现的蓝色横线问题
     */
    private fun setDialogSystemLine() {
        try {
            var divierId = context.resources.getIdentifier("android:id/titleDivider", null, null)
            val divider: View = this.findViewById(divierId)
            divider.setBackgroundColor(Color.TRANSPARENT) //横线透明色
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initResource() {
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    override fun initWidget() {
        Cancel_btn.setOnClickListener(this)
        Drop_btn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.Cancel_btn -> {
                if (listener != null)
                    listener.onCancelButtonClick()
                dismiss()
            }
            R.id.Drop_btn -> {
                if (listener != null)
                    listener.onOkButtonClick(Drop_btn!!, type)
                dismiss()
            }
        }
    }

    private fun setListViewHeight(tips: Array<out String>?) {
        if (tips == null)
            return
        if (tips.size > 4) {
            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 480)
            ExitDialog_ListView.layoutParams = params
        }
    }

    /**
     * 设置取消按钮的文字
     */
    fun setCancelButtonText(text: String): TipsBuilder {
        Cancel_btn.text = text
        return builder
    }


    /**
     * 显示弹窗
     * 参数1：确定按钮的文字，默认为Drop
     * 参数2：可变参数，弹窗的提示内容
     */
    fun showDialog(okBtnText: String?, vararg tipsText: String): TipsBuilder {
        setListViewHeight(tipsText)
        okBtnText?.let { this.okBtnText = okBtnText }
        Drop_btn.text = this.okBtnText
        adapter = TipsDialogAdapter(tipsText.toList(), context)
        ExitDialog_ListView.adapter = adapter
        show()
        return builder
    }

    /**
     * 显示弹窗
     * 参数1：确定按钮的文字，默认为Drop
     * 参数2：设置窗口类型
     * 参数3：可变参数，弹窗的提示内容
     */
    fun showDialog(okBtnText: String?, type: Any, vararg tipsText: String): TipsBuilder {
        setListViewHeight(tipsText)
        okBtnText?.let { this.okBtnText = okBtnText }
        Drop_btn.text = this.okBtnText
        adapter = TipsDialogAdapter(tipsText.toList(), context)
        ExitDialog_ListView.adapter = adapter
        this.type = type
        show()
        return builder
    }

    /**
     * 显示弹窗
     */
    fun showDialog(): TipsBuilder {
        setListViewHeight(null)
        show()
        return builder
    }

    /**
     * 同一个页面多个弹窗，设置弹窗类型，该类型值在点击确定按钮时返回
     */
    fun setDialogType(type: Any) {
        this.type = type
    }

    interface OnDropBtnClickListener {
        fun onOkButtonClick(v: View, type: Any)
        fun onCancelButtonClick()
    }

    inner class TipsBuilder {
        /**
         * 设置按钮及提示信息的颜色
         */
        fun setTextColor(color: Int): TipsBuilder {
            Drop_btn.setTextColor(color)
            Cancel_btn.setTextColor(color)
            adapter.setContentColor(color)
            return builder
        }

        /**
         * 分别设置按钮及提示信息的颜色
         */
        fun setTextColor(okBtnColor: Int, cancelBtnColor: Int, contentColor: Int): TipsBuilder {
            Drop_btn.setTextColor(okBtnColor)
            Cancel_btn.setTextColor(cancelBtnColor)
            adapter.setContentColor(contentColor)
            return builder
        }

        /**
         * 分别设置按钮的颜色
         */
        fun setTextColor(okBtnColor: Int, cancelBtnColor: Int): TipsBuilder {
            Drop_btn.setTextColor(okBtnColor)
            Cancel_btn.setTextColor(cancelBtnColor)
            return builder
        }

        /**
         * 设置确定按钮的文字
         */
        fun setOkButtonText(text: String): TipsBuilder {
            Drop_btn.text = text
            return builder
        }

        /**
         * 设置提示信息
         * @param tipsText 可变参数
         */
        fun setHintText(vararg tipsText: String): TipsBuilder {
            adapter = TipsDialogAdapter(tipsText.toList(), context)
            ExitDialog_ListView.adapter = adapter
            return builder
        }

        fun show() {
            this@TipsDialog.show()
        }

    }

}