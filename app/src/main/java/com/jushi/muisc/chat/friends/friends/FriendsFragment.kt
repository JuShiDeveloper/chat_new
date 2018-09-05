package com.jushi.muisc.chat.friends.friends


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.hyphenate.chat.EMClient
import com.jushi.base.fragment.ViewPagerFragment

import com.jushi.muisc.chat.R
import com.jushi.muisc.chat.friends.add_friends.AddFriendsDialog
import kotlinx.android.synthetic.main.fragment_friends.*


/**
 *好友列表
 *
 */
class FriendsFragment : ViewPagerFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val adapter by lazy { FriendsListAdapter(context!!) }
    private val addFriendsDialog by lazy { AddFriendsDialog(context!!) }
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
        val allContacts = EMClient.getInstance().contactManager().allContactsFromServer
        if (isRefresh) {
            adapter.clear()
        }
        adapter.addAll(allContacts)
        adapter.notifyDataSetChanged()
        isFinished = true
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
    }

    override fun onRefresh() {
        initAllContacts()
    }

}
