package com.jushi.muisc.chat.friends.message


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.jushi.base.fragment.ViewPagerFragment

import com.jushi.muisc.chat.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody


/**
 *消息页面(所有的会话)
 */
class NewsFragment : ViewPagerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news, container, false)
        }
        return rootView
    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        EMClient.getInstance().chatManager().addMessageListener(msgListener)
    }

    private var msgListener: EMMessageListener = object : EMMessageListener {

        override fun onMessageReceived(messages: List<EMMessage>) {
            //收到消息
            for (message in messages) {
                val msgType = message.type
                val textBody: EMTextMessageBody = message.body as EMTextMessageBody
            }
        }

        override fun onCmdMessageReceived(messages: List<EMMessage>) {
            //收到透传消息
        }

        override fun onMessageRead(messages: List<EMMessage>) {
            //收到已读回执
        }

        override fun onMessageDelivered(message: List<EMMessage>) {
            //收到已送达回执
        }

        fun onMessageRecalled(messages: List<EMMessage>) {
            //消息被撤回
        }

        override fun onMessageChanged(message: EMMessage, change: Any) {
            //消息状态变动
        }
    }
}
