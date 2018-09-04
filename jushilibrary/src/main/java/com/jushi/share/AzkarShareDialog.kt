package com.jushi.share

import android.content.Context
import android.content.pm.ResolveInfo
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jushi.base.easyrecyclerview.EasyRecyclerView
import com.jushi.base.easyrecyclerview.adapter.BaseViewHolder
import com.jushi.base.easyrecyclerview.adapter.RecyclerArrayAdapter
import java.lang.ref.WeakReference

/**
 * 点击分享或祈福详情中右上角菜单按钮后显示的底部弹窗
 */
class AzkarShareDialog {

    companion object {
        const val HORIZONTAL = 0x0001
        const val VERTICAL = HORIZONTAL + HORIZONTAL
        private lateinit var weakReference: WeakReference<Context>
        private lateinit var rootView: View
        private lateinit var bottomDialog: BottomSheetDialog
        private lateinit var builder: Builder
        private lateinit var cancelBtn: TextView
        private lateinit var sRecyclerView: EasyRecyclerView //可分享的app列表

        fun create(context: Context): Builder {
            weakReference = WeakReference(context)
            initDialog()
            findWidget()
            builder = Builder()
            return builder
        }

        private fun initDialog() {
            bottomDialog = BottomSheetDialog(weakReference.get()!!)
            rootView = View.inflate(weakReference.get()!!, R.layout.share_dialog_layout, null)
            bottomDialog.setContentView(rootView)
        }

        private fun findWidget() {
            cancelBtn = rootView.findViewById(R.id.share_dialog_cancel)
            cancelBtn.setOnClickListener { bottomDialog.dismiss() }
            sRecyclerView = rootView.findViewById(R.id.dialog_share_apps_RecyclerView)
        }
    }

    class Builder {
        private lateinit var listener: OnShareAppsClickListener
        private val shareAdapter by lazy { ShareAppsAdapter(weakReference.get()!!) }

        fun setOnShareAppsClickListener(listener: OnShareAppsClickListener): Builder {
            this.listener = listener
            return builder
        }

        fun setOrientation(orientation: Int): Builder {
            when (orientation) {
                HORIZONTAL -> {
                    val sLayoutManager = LinearLayoutManager(weakReference.get()!!)
                    sLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                    sRecyclerView.setLayoutManager(sLayoutManager)
                }
                VERTICAL -> {
                    sRecyclerView.setLayoutManager(GridLayoutManager(weakReference.get()!!, 4))
                }
            }
            return builder
        }


        private fun initAdapter() {
            shareAdapter.addAll(ShareUtils.getShareApps(weakReference.get()!!))
            sRecyclerView.adapter = shareAdapter

        }

        private fun setItemClickListener() {
            //点击要分享的应用
            shareAdapter.setOnItemClickListener {
                val appInfo = shareAdapter.allData[it]
//                ShareUtils.shareMsg(weakReference.get()!!, appInfo, weakReference.get()!!.applicationInfo.loadLabel(weakReference.get()!!.packageManager).toString(),
//                        azkarBean.content + weakReference.get()!!.getString(R.string.app_downloadUrl), null)
                listener?.let { listener.onAppsClick(appInfo) }
            }

            fun show() {
                initAdapter()
                setItemClickListener()
                bottomDialog.show()
            }

        }

        /**
         * 用户点击了可分享的应用时回调
         */
        interface OnShareAppsClickListener {
            fun onAppsClick(appInfo: ResolveInfo)
        }

        /**
         * 列出可分享的app的列表的adapter
         */
        class ShareAppsAdapter(val mContext: Context) : RecyclerArrayAdapter<ResolveInfo>(mContext) {
            override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
                return ShareHolder(parent)
            }

            inner class ShareHolder : BaseViewHolder<ResolveInfo> {
                private val imageLayout: LinearLayout = `$`(R.id.share_apps_image_layout)
                private val image: ImageView = `$`(R.id.share_apps_image)
                private val appsName: TextView = `$`(R.id.share_apps_name)
                private val packageManager by lazy { context.packageManager }

                constructor(parent: ViewGroup) : super(parent, R.layout.share_apps_item)

                override fun setData(data: ResolveInfo) {
                    imageLayout.setBackgroundResource(R.drawable.bg_round_share_apps_item)
                    image.setImageDrawable(data.loadIcon(packageManager))
                    appsName.text = data.loadLabel(packageManager)
                }
            }
        }

    }
}