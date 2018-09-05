package com.jushi.muisc.chat.friends.friends


import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.hyphenate.chat.EMClient
import com.jushi.base.fragment.ViewPagerFragment

import com.jushi.muisc.chat.R
import com.jushi.muisc.chat.friends.add_friends.AddFriendsDialog
import com.jushi.muisc.chat.friends.add_friends.AddStatusListener
import com.jushi.utils.SuccessToast
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
    private val handler by lazy { Handler() }
    private var isFinished = false
    private var isRefresh = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_friends, container, false)
        return rootView
    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        if (isVisible && !isFinished) {
            initAllContacts()
        }
    }

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
                    isFinished = true
                }, {})
    }

    override fun initWidget() {
        initRecyclerView()
        setAddBtnClickListener()
    }

    private fun setAddBtnClickListener() {
        add_friends_btn.setOnClickListener {
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
        initAllContacts()
    }

    /**
     * 在弹窗中发送好友添加请求成功
     */
    override fun onSendAddFriendsSuccess() {
        handler.post({
            SuccessToast.makeToast(context!!).show(context!!.getString(R.string.add_msg_send_success))
        })
    }
}
