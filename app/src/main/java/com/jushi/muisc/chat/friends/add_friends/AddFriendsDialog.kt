package com.jushi.muisc.chat.friends.add_friends

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.jushi.base.dialog.BaseDialog
import com.jushi.muisc.chat.R
import kotlinx.android.synthetic.main.add_friends_dialog_layout.*

/**
 * 发送添加好友请求的dialog
 */
class AddFriendsDialog(val mContext: Context, val listener: AddStatusListener) : BaseDialog(mContext) {
    private lateinit var friendsName: String
    private lateinit var addReason: String
    private lateinit var friendsList: List<String>

    init {
        init(R.layout.add_friends_dialog_layout)
        setDialogSystemLine()
    }

    /**
     * 适配低版本顶部的出现的蓝色横线问题
     */
    private fun setDialogSystemLine() {
        try {
            var divierId = context.resources.getIdentifier("android:id/titleDivider", null, null)
            val divider: View = this.findViewById(divierId)
            divider.setBackgroundColor(Color.TRANSPARENT) //横线透明色
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initResource() {

    }

    override fun initWidget() {
        setBtnClickListener()
    }

    private fun setBtnClickListener() {
        add_Cancel.setOnClickListener {
            clearData()
            dismiss()
        }
        add_OK_btn.setOnClickListener {
            friendsName = et_add_friends_name.text.toString()
            addReason = et_add_friends_reason.text.toString()
            if (TextUtils.isEmpty(friendsName)) {
                add_friends_hint.text = mContext.getString(R.string.not_input_friends_name)
                return@setOnClickListener
            }
            if (friendsName == EMClient.getInstance().currentUser) {
                add_friends_hint.text = mContext.getString(R.string.can_not_add_self)
                return@setOnClickListener
            }
            if (friendsList.contains(friendsName)) {
                add_friends_hint.text = mContext.getString(R.string.can_not_repeat_add_friends)
                return@setOnClickListener
            }
            EMClient.getInstance().contactManager().aysncAddContact(friendsName, addReason, callBack)
            clearData()
        }
    }

    private fun clearData() {
        et_add_friends_name.setText("")
        et_add_friends_reason.setText("")
        add_friends_hint.text = ""
    }

    fun setFriendsList(friendsList: List<String>) {
        this.friendsList = friendsList
    }

    private val callBack = object : EMCallBack {
        private val ERROR_MSG = "Username is illegal"

        override fun onSuccess() {
            listener.onSendAddFriendsSuccess()
            dismiss()
        }

        override fun onProgress(progress: Int, status: String?) {

        }

        override fun onError(code: Int, error: String) {

        }
    }


}