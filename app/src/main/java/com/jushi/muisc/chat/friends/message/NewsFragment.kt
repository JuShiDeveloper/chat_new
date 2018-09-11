package com.jushi.muisc.chat.friends.message


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.jushi.base.fragment.ViewPagerFragment

import com.jushi.muisc.chat.R
import com.jushi.muisc.chat.friends.chat.ChatActivity
import com.jushi.muisc.chat.friends.login.LoginActivity
import com.jushi.muisc.chat.common.view.layout.FriendsLayoutListener
import kotlinx.android.synthetic.main.fragment_news.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 *消息页面(所有的会话)
 */
class NewsFragment : ViewPagerFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val messageAdapter by lazy { MessageAdapter(context!!) }
    private lateinit var listener: FriendsLayoutListener
    private var isFinished = false
    private var isRefresh = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news, container, false)
        }
        context!!.registerReceiver(refreshDataReceiver, IntentFilter(context!!.getString(R.string.LOGIN_SUCCESS_BROADCAST_ACTION)))
        return rootView
    }

    fun setListener(listener: FriendsLayoutListener) {
        this.listener = listener
    }

    override fun initWidget() {
        checkIsLogin()
        initRecyclerView()
        setBtnClickListener()
    }

    private fun checkIsLogin() {
        if (!isLogin()) {
            please_login_btn.visibility = View.VISIBLE
        }
    }

    private fun isLogin(): Boolean {
        return EMClient.getInstance().isLoggedInBefore
    }

    private fun initRecyclerView() {
        msg_RecyclerView.setLayoutManager(LinearLayoutManager(context!!))
        msg_RecyclerView.setRefreshListener(this)
        if (isLogin()) msg_RecyclerView.setRefreshing(true)
        msg_RecyclerView.adapter = messageAdapter
        messageAdapter.setOnItemClickListener {
            val friendsName = messageAdapter.allData[it].allMessages[0].userName
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ChatActivity.FRIENDS_NAME_KEY, friendsName)
            startActivity(intent)
        }
    }

    private fun setBtnClickListener() {
        tv_refresh_msg_btn.setOnClickListener {
            isRefresh = true
            msg_RecyclerView.setRefreshing(true)
            initAllConversations()
        }

        tv_to_chat_btn.setOnClickListener {
            listener.toFriendsPage()
        }

        please_login_btn.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        if (isVisible && !isFinished && isLogin()) {
            initAllConversations()
        }
    }

    /**
     * 初始化所有的会话
     */
    private fun initAllConversations() {
        Observable.just("")
                .subscribeOn(Schedulers.newThread())
                .map {
                    val allConversations = EMClient.getInstance().chatManager().allConversations
                    val conversationList = arrayListOf<EMConversation>()
                    val keys = allConversations.keys
                    for (key in keys) {
                        val conversation = allConversations[key]
                        conversationList.add(conversation!!)
                    }
                    return@map conversationList
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    msg_RecyclerView.setRefreshing(false)
                    if (it.isEmpty()) {
                        no_msg_layout.visibility = View.VISIBLE
                        return@subscribe
                    } else {
                        no_msg_layout.visibility = View.GONE
                        please_login_btn.visibility = View.GONE
                    }
                    if (isRefresh) {
                        messageAdapter.clear()
                    }
                    messageAdapter.addAll(it)
                    messageAdapter.notifyDataSetChanged()
                    isFinished = true
                }, {
                    no_msg_layout.visibility = View.VISIBLE
                    msg_RecyclerView.setRefreshing(false)
                })
    }


    override fun onRefresh() {
        if (!isLogin()) {
            messageAdapter.clear()
            please_login_btn.visibility = View.VISIBLE
            msg_RecyclerView.setRefreshing(false)
            return
        }
        isRefresh = true
        msg_RecyclerView.setRefreshing(true)
        initAllConversations()
    }

    private val refreshDataReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //接收登陆成功的广播
            if (intent!!.action == context!!.getString(R.string.LOGIN_SUCCESS_BROADCAST_ACTION)) {
                isRefresh = true
                initAllConversations()
                please_login_btn.visibility = View.GONE
            }
        }
    }

    /**
     * 退出登录成功
     */
    fun exitLoginSuccess() {
        messageAdapter.clear()
        please_login_btn.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(refreshDataReceiver)
    }
}
