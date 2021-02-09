package com.longcode.globaldialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.*
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_global_dialog_normal.view.*
import java.net.URL

class GlobalDialogNormalView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), IGlobalDialogView, View.OnClickListener {

    private var mGlobalDialog: GlobalDialog? = null

    override fun initView(globalDialog: GlobalDialog): View {
        mGlobalDialog = globalDialog
        ll_content_layout.minimumWidth = (resources.displayMetrics.widthPixels * 0.6).toInt()
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
        if (globalDialog.mNegativeButtonText.isNullOrEmpty()) {
            if (globalDialog.mPositiveButtonText.isNullOrEmpty() && globalDialog.mNeutralButtonText.isNullOrEmpty()) {
                tv_negative_button.visibility = View.VISIBLE
                tv_negative_button.text = resources.getString(R.string.global_dialog_default_cancel_text)
                tv_negative_button.setOnClickListener(this@GlobalDialogNormalView)
            } else {
                tv_negative_button.visibility = View.GONE
            }
        } else {
            tv_negative_button.visibility = View.VISIBLE
            tv_negative_button.text = globalDialog.mNegativeButtonText
            tv_negative_button.setOnClickListener(this@GlobalDialogNormalView)
        }
        if (globalDialog.mPositiveButtonText.isNullOrEmpty()) {
            tv_positive_button.visibility = View.GONE
        } else {
            tv_positive_button.visibility = View.VISIBLE
            tv_positive_button.text = globalDialog.mPositiveButtonText
            tv_positive_button.setOnClickListener(this@GlobalDialogNormalView)
        }
        if (globalDialog.mNeutralButtonText.isNullOrEmpty()) {
            tv_neutral_button.visibility = View.GONE
        } else {
            tv_neutral_button.visibility = View.VISIBLE
            tv_neutral_button.text = globalDialog.mNeutralButtonText
            tv_neutral_button.setOnClickListener(this@GlobalDialogNormalView)
        }
        return ll_dialog_layout
    }

    override fun initWindowAttributes(window: Window, canOutsideClick: Boolean) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val windowLayoutParams = window.attributes
        if (canOutsideClick) {
            ll_dialog_layout.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            windowLayoutParams.width = ll_dialog_layout.measuredWidth
            windowLayoutParams.height = ll_dialog_layout.measuredHeight
            windowLayoutParams.gravity = Gravity.CENTER
        } else {
            windowLayoutParams.width = resources.displayMetrics.widthPixels
            windowLayoutParams.height = resources.displayMetrics.heightPixels
        }
        windowLayoutParams.flags = windowLayoutParams.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        window.attributes = windowLayoutParams
    }

    override fun getInAnimId(): Int {
        return R.anim.in_alpha
    }

    override fun getOutAnimId(): Int {
        return R.anim.out_alpha
    }

    override fun getDialogGravity(): Int {
        return GlobalDialog.DIALOG_GRAVITY_CENTER
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_negative_button -> {
                mGlobalDialog?.mNegativeButtonListener?.onClick(mGlobalDialog, DialogInterface.BUTTON_NEGATIVE)
                mGlobalDialog?.dismiss()
            }
            tv_neutral_button -> {
                mGlobalDialog?.mNeutralButtonListener?.onClick(mGlobalDialog, DialogInterface.BUTTON_NEUTRAL)
                mGlobalDialog?.dismiss(false)
            }
            tv_positive_button -> {
                mGlobalDialog?.mPositiveButtonListener?.onClick(mGlobalDialog, DialogInterface.BUTTON_POSITIVE)
                mGlobalDialog?.dismiss(false)
            }
        }
    }
}