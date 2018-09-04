package com.jushi.share

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.text.TextUtils
import java.io.File

/**
 * 分享类
 */
object ShareUtils {
    private const val TYPE_APPLICATION = "application/*"
    private const val TYPE_AUDIO = "audio/*"
    private const val TYPE_TEXT = "text/plain"
    private const val TYPE_IMAGE_JPG = "image/jpg"
    private const val TYPE_IMAGE_GIF = "image/gif"

    /**
     * 获取所有可分享的应用
     */
    fun getShareApps(context: Context): List<ResolveInfo> {
        val apps = arrayListOf<ResolveInfo>()
        val intent = Intent(Intent.ACTION_SEND, null)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.type = TYPE_TEXT
        val pManager = context.packageManager
        apps.addAll(pManager.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT))
        apps.reverse()
        return apps
    }

    fun shareMsg(context: Context, resolveInfo: ResolveInfo, file: File) {
        val uri = Uri.parse(file.toString())
        val intent = Intent(Intent.ACTION_SEND)
        intent.component = ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)
        intent.type = TYPE_AUDIO
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * @param title 分享的标题
     * @param msg 分享的文字内容
     * @param imagePath 图片路径
     */
    fun shareMsg(context: Context, resolveInfo: ResolveInfo, title: String, msg: String, imagePath: String?) {
        val intent = Intent(Intent.ACTION_SEND)
        if (TextUtils.isEmpty(imagePath)) {
            intent.type = TYPE_TEXT
        } else {
            val file = File(imagePath)
            if (file.exists() && file.isFile) {
                intent.type = TYPE_IMAGE_JPG
                var uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
                } else {
                    Uri.fromFile(file)
                }
                intent.putExtra(Intent.EXTRA_STREAM, uri)
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, title)
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        intent.component = ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}