package com.jushi.muisc.chat.friends.message


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.jushi.base.fragment.ViewPagerFragment

import com.jushi.muisc.chat.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.jushi.muisc.chat.friends.chat.ChatActivity
import kotlinx.android.synthetic.main.fragment_news.*
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 *消息页面(所有的会话)
 */
class NewsFragment : ViewPagerFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val messageAdapter by lazy { MessageAdapter(context!!) }
    private var isFinished = false
    private var isRefresh = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news, container, false)
        }
        return rootView
    }

    override fun initWidget() {
        initRecyclerView()

    }

    private fun initRecyclerView() {
        msg_RecyclerView.setLayoutManager(LinearLayoutManager(context!!))
        msg_RecyclerView.setRefreshListener(this)
        msg_RecyclerView.setRefreshing(true)
        msg_RecyclerView.adapter = messageAdapter
        messageAdapter.setOnItemClickListener {
            val friendsName = messageAdapter.allData[it].allMessages[0].userName
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ChatActivity.FRIENDS_NAME_KEY, friendsName)
            startActivity(intent)
        }
    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        if (isVisible && !isFinished) {
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
                    if (isRefresh){
                        messageAdapter.removeAll()
                    }
                    messageAdapter.addAll(it)
                    messageAdapter.notifyDataSetChanged()
                    isFinished = true
                    msg_RecyclerView.setRefreshing(false)
                },{})
    }


    override fun onRefresh() {
        isRefresh = true
        msg_RecyclerView.setRefreshing(true)
        initAllConversations()
    }

}
