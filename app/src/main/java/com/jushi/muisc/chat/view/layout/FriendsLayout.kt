package com.jushi.muisc.chat.view.layout

import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout

import com.jushi.muisc.chat.R
import com.jushi.muisc.chat.friends.friends.FriendsFragment
import com.jushi.muisc.chat.friends.my.MyFragment
import com.jushi.muisc.chat.friends.message.NewsFragment

/**
 * Created by wyf on 2018/5/4.
 * 好友模块
 */

class FriendsLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr),
        BottomNavigationView.OnNavigationItemSelectedListener ,FriendsLayoutListener{

    private lateinit var navigationView: BottomNavigationView
    private var fragments = HashMap<String, Fragment>()

    private val TAG_NEWS_FRAGMENT: String = NewsFragment::class.java.simpleName
    private val TAG_FRIENDS_FRAGMENT = FriendsFragment::class.java.simpleName
    private val TAG_MY_FRAGMENT = MyFragment::class.java.simpleName
    private lateinit var preFragment: Fragment

    init {
        initView()
    }

    private fun initView() {
        findWidget()
        setListener()
        preFragment = getFragment(TAG_NEWS_FRAGMENT)
        addFragment(preFragment, TAG_NEWS_FRAGMENT)
    }

    private fun findWidget() {
        View.inflate(context, R.layout.layout_friends, this)
        navigationView = findViewById(R.id.BottomNavigationView)
    }

    private fun setListener() {
        navigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun toFriendsPage() {
        navigationView.selectedItemId = R.id.my_friends
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.news -> {//消息
                replaceFragment(getFragment(TAG_NEWS_FRAGMENT), TAG_NEWS_FRAGMENT)
                return true
            }
            R.id.my_friends -> {//好友
                replaceFragment(getFragment(TAG_FRIENDS_FRAGMENT), TAG_FRIENDS_FRAGMENT)
                return true
            }
            R.id.my_ -> {//我的
                replaceFragment(getFragment(TAG_MY_FRAGMENT), TAG_MY_FRAGMENT)
                return true
            }
        }
        return false
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentContainer, fragment, tag)
        transaction.commitAllowingStateLoss()
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.hide(preFragment)
        if (fragment.isAdded) {
            transaction.show(fragment)
        } else {
            transaction.add(R.id.fragmentContainer, fragment, tag)
        }
        transaction.commitAllowingStateLoss()
        preFragment = fragment
    }

    private fun getFragment(tag: String): Fragment {
        var fragment: Fragment? = fragments[tag]
        if (fragment == null) {
            when (tag) {
                TAG_NEWS_FRAGMENT -> {
                    fragment = NewsFragment()
                    fragment.setListener(this)
                }
                TAG_FRIENDS_FRAGMENT -> {
                    fragment = FriendsFragment()
                }
                TAG_MY_FRAGMENT -> {
                    fragment = MyFragment()
                }
            }
        }
        fragments[tag] = fragment!!
        return fragment
    }
}
