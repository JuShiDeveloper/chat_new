package com.jushi.muisc.chat.friends.friends


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.hyphenate.chat.EMClient
import com.jushi.base.fragment.ViewPagerFragment

import com.jushi.muisc.chat.R
import com.jushi.muisc.chat.friends.add_friends.AddFriendsDialog
import com.jushi.muisc.chat.friends.add_friends.AddStatusListener
import com.jushi.muisc.chat.friends.chat.ChatActivity
import com.jushi.muisc.chat.friends.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_friends.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 *好友列表
 *
 */
class FriendsFragment : ViewPagerFragment(), SwipeRefreshLayout.OnRefreshListener, AddStatusListener {

    private val adapter by lazy { FriendsListAdapter(context!!) }
    private val addFriendsDialog by lazy { AddFriendsDialog(context!!, this) }
    private lateinit var friendsList: List<String>
    private var isFinished = false
    private var isRefresh = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_friends, container, false)
        context!!.registerReceiver(refreshFriendsListReceiver, IntentFilter(context!!.getString(R.string.ADD_FRIENDS_SUCCESS_BROADCAST_ACTION)))
        return rootView
    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        if (isVisible && !isFinished) {
            initAllContacts()
        }
    }

    /**
     * 初始化所有的好友列表数据
     */
    private fun initAllContacts() {
        Observable.just("")
                .subscribeOn(Schedulers.newThread())
                .map {
                    return@map EMClient.getInstance().contactManager().allContactsFromServer
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (isRefresh) {
                        adapter.clear()
                    }
                    adapter.addAll(it)
                    adapter.notifyDataSetChanged()
                    friendsList = it
                    isFinished = true
                    friends_listRecyclerView.setRefreshing(false)
                }, { friends_listRecyclerView.setRefreshing(false) })
    }

    override fun initWidget() {
        initRecyclerView()
        setAddBtnClickListener()
        setItemClick()
    }

    /**
     * 设置监听
     */
    override fun setListener() {

    }

    private fun setItemClick() {
        adapter.setOnItemClickListener(object : FriendsListAdapter.ItemClickListener {
            override fun onItemClick(userName: String) {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra(ChatActivity.FRIENDS_NAME_KEY, userName)
                startActivity(intent)
            }
        })
    }

    private fun setAddBtnClickListener() {
        add_friends_btn.setOnClickListener {
            if (!EMClient.getInstance().isLoggedInBefore) { //如果未登陆就跳转到登陆页面
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                return@setOnClickListener
            }
            addFriendsDialog.setFriendsList(friendsList)
            addFriendsDialog.show()
        }
    }

    private fun initRecyclerView() {
        friends_listRecyclerView.setLayoutManager(LinearLayoutManager(context))
        friends_listRecyclerView.setRefreshListener(this)
        friends_listRecyclerView.adapter = adapter
        friends_listRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && add_friends_btn.visibility == View.VISIBLE) {
                    add_friends_btn.hide()
                } else if (dy < 0 && add_friends_btn.visibility != View.VISIBLE) {
                    add_friends_btn.show()
                }
            }
        })
    }

    override fun onRefresh() {
        isRefresh = true
        friends_listRecyclerView.setRefreshing(true)
        initAllContacts()
    }

    /**
     * 在弹窗中发送好友添加请求成功
     */
    override fun onSendAddFriendsSuccess() {
//        handler.post({
//            SuccessToast.makeToast(context!!).show(context!!.getString(R.string.add_msg_send_success))
//        })
    }

    private val refreshFriendsListReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //接收从MainActivity中发送过来的添加好友成功的广播,和登陆成功的广播
            if (intent!!.action == context!!.getString(R.string.ADD_FRIENDS_SUCCESS_BROADCAST_ACTION)
                    || intent!!.action == context!!.getString(R.string.LOGIN_SUCCESS_BROADCAST_ACTION)) {
                isRefresh = true
                initAllContacts()
            }
        }
    }

    fun exitLoginSuccess() {
        adapter.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(refreshFriendsListReceiver)
    }

}
