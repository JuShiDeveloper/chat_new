package com.jushi.muisc.chat.friends.message

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMTextMessageBody
import com.jushi.base.easyrecyclerview.adapter.BaseViewHolder
import com.jushi.base.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.jushi.muisc.chat.R
import com.jushi.muisc.chat.utils.DateUtils

/**
 * 消息列表适配器
 */
class MessageAdapter(val mContext: Context) : RecyclerArrayAdapter<EMConversation>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> = MessageHolder(parent)

    inner class MessageHolder : BaseViewHolder<EMConversation> {

        private val userName: TextView = `$`(R.id.msg_userName)
        private val msgInfo: TextView = `$`(R.id.tv_msg_info)
        private val msgTime: TextView = `$`(R.id.msg_time)

        constructor(parent: ViewGroup?) : super(parent, R.layout.message_item_layout)

        override fun setData(data: EMConversation) {
            super.setData(data)
            val message = data.allMessages[0]
            if (message.userName == context.getString(R.string.admin_)){
                userName.text = "管理员"
            }else userName.text = message.userName
            msgInfo.text = (message.body as EMTextMessageBody).message
            msgTime.text = DateUtils.timeToDate(message.msgTime,DateUtils.MM_DD_HH_mm)
        }
    }
}