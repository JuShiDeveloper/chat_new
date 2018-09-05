package com.jushi.muisc.chat.friends.chat

import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.jushi.base.activity.BaseActivity
import com.jushi.muisc.chat.R

/**
 * 聊天界面
 */
class ChatActivity : BaseActivity() {

    companion object {
        const val FRIENDS_NAME_KEY = "friendsName"
    }

    private lateinit var friendsName: String

    override fun setContentView() {
        setContentView(R.layout.activity_chat_layout)
        friendsName = intent.getStringExtra(FRIENDS_NAME_KEY)
    }

    override fun initWidget() {

    }

    override fun initResource() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener)
    }

    private var msgListener: EMMessageListener = object : EMMessageListener {

        override fun onMessageReceived(messages: List<EMMessage>) {
            //收到消息
            for (message in messages) {
                val msgType = message.type
                when(msgType){
                    EMMessage.Type.TXT ->{ //文本消息
                        val textBody: EMTextMessageBody = message.body as EMTextMessageBody
                    }
                    EMMessage.Type.IMAGE ->{ //图片消息

                    }
                    EMMessage.Type.VIDEO ->{ //视频消息

                    }
                    EMMessage.Type.VOICE ->{ //语音消息

                    }
                    EMMessage.Type.LOCATION ->{ //位置消息

                    }
                    EMMessage.Type.FILE ->{ //文件消息

                    }
                    EMMessage.Type.CMD ->{ //透传消息

                    }
                }
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

    override fun onDestroy() {
        super.onDestroy()
        //记得在不需要的时候移除listener，如在activity的onDestroy()时
        EMClient.getInstance().chatManager().removeMessageListener(msgListener)
    }
}