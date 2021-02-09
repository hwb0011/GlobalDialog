package com.longcode.globaldialog

import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.*
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_global_dialog_bottom.view.*
import java.net.URL
import kotlin.math.abs

class GlobalDialogBottomView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), IGlobalDialogView {

    private var mPosX = 0f
    private var mPosY = 0f
    private var mCurPosX = 0f
    private var mCurPosY = 0f

    override fun initView(globalDialog: GlobalDialog): View {
        if (globalDialog.mTitle.isNullOrEmpty()) {
            tv_title.visibility = View.GONE
        } else {
            tv_title.visibility = View.VISIBLE
            tv_title.text = globalDialog.mTitle
        }
        if (globalDialog.mIconUrl.isNullOrEmpty() && globalDialog.mIconId == 0) {
            iv_title_icon.visibility = View.GONE
        } else {
            iv_title_icon.visibility = View.VISIBLE
            if (globalDialog.mIconUrl.isNullOrEmpty())
                iv_title_icon.setImageResource(globalDialog.mIconId)
            else iv_title_icon.setImageBitmap(
                BitmapFactory.decodeStream(URL(globalDialog.mIconUrl).openStream()))
        }
        if (globalDialog.mMessage.isNullOrEmpty()){
            tv_message.visibility = View.GONE
        } else {
            tv_message.visibility = View.VISIBLE
            tv_message.text = globalDialog.mMessage
        }
        ll_dialog_layout.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mPosX = event.x
                    mPosY = event.y
                    mCurPosX = event.x
                    mCurPosY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    mCurPosX = event.x
                    mCurPosY = event.y
                }
                MotionEvent.ACTION_UP -> {
                    if (abs(mCurPosY - mPosY) < 25) {
                        globalDialog.mPositiveButtonListener?.onClick(globalDialog, DialogInterface.BUTTON_POSITIVE)
                        globalDialog.dismiss(false)
                    } else if (mCurPosY - mPosY > 0) {
                        globalDialog.dismiss()
                    }
                }
            }
            true
        }
        return ll_dialog_layout
    }

    override fun initWindowAttributes(window: Window, canOutsideClick: Boolean) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val windowLayoutParams = window.attributes
        windowLayoutParams.width = resources.displayMetrics.widthPixels
        if (canOutsideClick) {
            ll_dialog_layout.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            windowLayoutParams.height = ll_dialog_layout.measuredHeight + getNavigationBarHeight()
        } else {
            windowLayoutParams.height = resources.displayMetrics.heightPixels
        }
        windowLayoutParams.gravity = Gravity.BOTTOM
        windowLayoutParams.flags = windowLayoutParams.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        window.attributes = windowLayoutParams
    }

    override fun getInAnimId(): Int {
        return R.anim.in_from_bottom
    }

    override fun getOutAnimId(): Int {
        return R.anim.out_from_bottom
    }

    override fun getDialogGravity(): Int {
        return GlobalDialog.DIALOG_GRAVITY_BOTTOM
    }

    private fun getNavigationBarHeight(): Int {
        var result = 0
        if (hasNavigationBar() && resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    private fun hasNavigationBar(): Boolean {
        var hasNavigationBar = false
        val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = resources.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val method = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride =
                method.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
        }
        return hasNavigationBar
    }
}