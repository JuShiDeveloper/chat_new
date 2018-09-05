package com.jushi.muisc.chat.friends.friends

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.jushi.base.easyrecyclerview.adapter.BaseViewHolder
import com.jushi.base.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.jushi.muisc.chat.R

class FriendsListAdapter(val mContext: Context) : RecyclerArrayAdapter<String>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> {
        return FriendsListHolder(parent)
    }

    inner class FriendsListHolder : BaseViewHolder<String> {
        private val friendsName: TextView = `$`(R.id.tv_friends_name)

        constructor(parent: ViewGroup?) : super(parent, R.layout.fragment_friends_list_item)

        override fun setData(data: String) {
            super.setData(data)
            friendsName.text = data
        }
    }
}