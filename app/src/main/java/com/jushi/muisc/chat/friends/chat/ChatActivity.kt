package com.jushi.muisc.chat.friends.chat

import com.jushi.base.activity.BaseActivity
import com.jushi.muisc.chat.R

/**
 * 聊天界面
 */
class ChatActivity : BaseActivity() {

    companion object {
        const val USER_NAME_KEY = "userName"
    }

    override fun setContentView() {
        setContentView(R.layout.activity_chat_layout)
    }

    override fun initWidget() {

    }

    override fun initResource() {

    }
}