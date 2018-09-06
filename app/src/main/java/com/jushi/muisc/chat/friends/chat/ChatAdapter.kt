package com.jushi.muisc.chat.friends.chat

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.jushi.base.easyrecyclerview.adapter.BaseViewHolder
import com.jushi.base.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.jushi.muisc.chat.R

class ChatAdapter(val mContext: Context) : RecyclerArrayAdapter<EMMessage>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> = ChatViewHolder(parent)

    inner class ChatViewHolder : BaseViewHolder<EMMessage> {
        private val msgContent: TextView = `$`(R.id.msg_content)
        private val msgLayout: LinearLayout = `$`(R.id.msg_layout)
        private val msgUserName: TextView = `$`(R.id.msg_user_name)

        constructor(parent: ViewGroup?) : super(parent, R.layout.activity_chat_item_layout)

        @SuppressLint("StringFormatInvalid")
        override fun setData(data: EMMessage) {
            super.setData(data)
            val msgType = data.type
            if (data.from == EMClient.getInstance().currentUser) {
                msgLayout.layoutParams = rightGravity()
                msgUserName.text = context.getString(R.string.who_, EMClient.getInstance().currentUser)
            } else {
                msgLayout.layoutParams = leftGravity()
                msgUserName.text = context.getString(R.string.who_, data.from)
            }
            when (msgType) {
                EMMessage.Type.TXT -> { //文本消息
                    val textBody = data.body as EMTextMessageBody
                    msgContent.text = textBody.message
                }
                EMMessage.Type.IMAGE -> { //图片消息

                }
                EMMessage.Type.VIDEO -> { //视频消息

                }
                EMMessage.Type.VOICE -> { //语音消息

                }
                EMMessage.Type.LOCATION -> { //位置消息

                }
                EMMessage.Type.FILE -> { //文件消息

                }
                EMMessage.Type.CMD -> { //透传消息

                }
            }
        }
    }

    private fun leftGravity(): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.LEFT
        params.topMargin = 10
        params.bottomMargin = 10
        params.leftMargin = 30
        params.rightMargin = 30
        return params
    }

    private fun rightGravity(): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.RIGHT
        params.topMargin = 10
        params.bottomMargin = 10
        params.leftMargin = 30
        params.rightMargin = 30
        return params
    }

}