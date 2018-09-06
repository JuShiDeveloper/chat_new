package com.jushi.muisc.chat.friends.chat

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.MenuItem
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.jushi.base.activity.BaseActivity
import com.jushi.muisc.chat.R
import kotlinx.android.synthetic.main.activity_chat_layout.*

/**
 * 聊天界面
 */
class ChatActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val FRIENDS_NAME_KEY = "friendsName"
    }

    private lateinit var conversation: EMConversation
    private val chatAdapter by lazy { ChatAdapter(this) }
    private lateinit var friendsName: String

    override fun setContentView() {
        setContentView(R.layout.activity_chat_layout)
        friendsName = intent.getStringExtra(FRIENDS_NAME_KEY)
    }

    override fun initWidget() {
        initToolbar()
        setSendBtnClickListener()
        initRecyclerView()
    }

    private fun initToolbar() {
        chat_toolbar.title = friendsName
        setSupportActionBar(chat_toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setSendBtnClickListener() {
        chat_send_btn.setOnClickListener {
            val inputMsg = et_input_msg.text.toString()
            if (TextUtils.isEmpty(inputMsg)) return@setOnClickListener
            val msg = EMMessage.createTxtSendMessage(inputMsg, friendsName)
            msg.from = EMClient.getInstance().currentUser
            msg.msgTime = System.currentTimeMillis()
            EMClient.getInstance().chatManager().sendMessage(msg)
            et_input_msg.setText("")
            notificationMsgCountChanged(msg)
        }
    }

    private fun initRecyclerView() {
        chat_msg_RecyclerView.setLayoutManager(LinearLayoutManager(this))
        chat_msg_RecyclerView.adapter = chatAdapter
        chat_msg_RecyclerView.setRefreshListener(this)
    }

    private fun notificationMsgCountChanged(message: EMMessage) {
        runOnUiThread {
            chatAdapter.add(message)
            chatAdapter.notifyDataSetChanged()
            chat_msg_RecyclerView.scrollToPosition(chatAdapter.count - 1)
        }
    }

    override fun initResource() {
        getChatHistroyMsg()
        setAddMessageListener()
    }

    /**
     * 获取消息记录
     */
    private fun getChatHistroyMsg() {
        conversation = EMClient.getInstance().chatManager().getConversation(friendsName)
        val allMessages = conversation.allMessages
        chatAdapter.addAll(allMessages)
    }

    /**
     * 获取更多消息记录
     */
    override fun onRefresh() {
        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，
        // APP中无需再次把获取到的messages添加到会话中
        val moreList = conversation.loadMoreMsgFromDB(chatAdapter.allData[0].msgId, 20)
        chatAdapter.addAll(moreList)
        chatAdapter.notifyDataSetChanged()
    }

    /**
     * 设置接收消息监听
     */
    private fun setAddMessageListener() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private var msgListener: EMMessageListener = object : EMMessageListener {

        override fun onMessageReceived(messages: List<EMMessage>) {
            //收到消息
            for (message in messages) {
                notificationMsgCountChanged(message)
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