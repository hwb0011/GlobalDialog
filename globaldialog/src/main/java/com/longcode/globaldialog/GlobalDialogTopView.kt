package com.longcode.globaldialog

import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_global_dialog_top.view.*
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import kotlin.math.abs
import android.graphics.BitmapFactory
import java.net.URL

class GlobalDialogTopView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), IGlobalDialogView {

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
                    } else if (mCurPosY - mPosY < 0) {
                        globalDialog.dismiss()
                    }
                }
            }
            true
        }
        return ll_dialog_layout
    }

    override fun initWindowAttributes(window: Window, canOutsideClick: Boolean) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(context, R.color.global_dialog_top_statue_bar_color)
        val windowLayoutParams = window.attributes
        windowLayoutParams.width = resources.displayMetrics.widthPixels
        if (canOutsideClick) {
            ll_dialog_layout.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            windowLayoutParams.height = ll_dialog_layout.measuredHeight + getStatusBarHeight()
        } else {
            windowLayoutParams.height = resources.displayMetrics.heightPixels
        }
        windowLayoutParams.gravity = Gravity.TOP
        windowLayoutParams.flags = windowLayoutParams.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        window.attributes = windowLayoutParams
    }

    override fun getInAnimId(): Int {
        return R.anim.in_from_top
    }

    override fun getOutAnimId(): Int {
        return R.anim.out_from_top
    }

    override fun getDialogGravity(): Int {
        return GlobalDialog.DIALOG_GRAVITY_TOP
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            result = resources.getDimensionPixelOffset(resId)
        }
        if (result <= 0) {
            result = resources.getDimensionPixelOffset(R.dimen.global_dialog_default_status_bar_height)
        }
        return result
    }
}