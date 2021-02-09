package com.longcode.globaldialog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class GlobalDialogActivity : AppCompatActivity() {

    companion object {

        var sNewGlobalDialog: GlobalDialog? = null

        fun startActivity(globalDialog: GlobalDialog) {
            sNewGlobalDialog = globalDialog
            val intent = Intent(globalDialog.mContext, GlobalDialogActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            globalDialog.mContext?.startActivity(intent)
        }

    }

    private var mDialogView: IGlobalDialogView? = null

    private var mRootView: View? = null

    private var isCancel = true

    private var mGlobalDialog: GlobalDialog? = null

    private val mDismissRunable = object : GlobalDialog.GlobalDismissRunnable() {
        override fun dismiss(isCancel: Boolean) {
            this@GlobalDialogActivity.isCancel = isCancel
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        display()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        display()
    }

    private fun display(){
        mGlobalDialog?.mDismissRunnable = null
        mGlobalDialog = sNewGlobalDialog
        sNewGlobalDialog = null
        mGlobalDialog?.mDismissRunnable = mDismissRunable
        (mGlobalDialog?.run {
            isCancel = true
            if (mDialogView == null || mDialogView?.getDialogGravity() != mGravity){
                setContentView(when (mGravity) {
                    GlobalDialog.DIALOG_GRAVITY_TOP -> R.layout.view_global_dialog_top
                    GlobalDialog.DIALOG_GRAVITY_BOTTOM -> R.layout.view_global_dialog_bottom
                    else -> R.layout.view_global_dialog_normal
                })
                mDialogView = findViewById<View>(R.id.ll_dialog_layout) as IGlobalDialogView
            }
            mRootView = mDialogView?.initView(this)?.parent as View
            mDialogView?.initWindowAttributes(window, mOutsideTouchable)
            overrideTransition()
            mRootView?.removeCallbacks(mDismissRunable)
            mRootView?.setOnClickListener {
                if (!mOutsideTouchable && mCancelable) {
                    dismiss()
                }
            }
        }) ?: let{
            setContentView(R.layout.activity_global_dialog)
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        when(mGlobalDialog?.mShowType) {
            GlobalDialog.DIALOG_TYPE_SHORT_SHOW -> {
                mRootView?.postDelayed(mDismissRunable, GlobalDialog.DIALOG_SHORT_SHOW_TIMEMILLS)
            }
            GlobalDialog.DIALOG_TYPE_LONG_SHOW -> {
                mRootView?.postDelayed(mDismissRunable, GlobalDialog.DIALOG_LONG_SHOW_TIMEMILLS)
            }
        }
    }

    private fun overrideTransition() {
        overridePendingTransition(
                mDialogView?.getInAnimId()?:R.anim.in_alpha,
                mDialogView?.getOutAnimId()?:R.anim.out_alpha)
    }

    override fun onBackPressed() {
        if (mGlobalDialog?.mCancelable == true) {
            dismiss()
        } else {
            return
        }
    }

    fun dismiss() {
        finish()
        overrideTransition()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRootView?.removeCallbacks(mDismissRunable)
        mGlobalDialog?.run {
            if (isCancel) mOnCancelListener?.onCancel(this)
            mOnDismissListener?.onDismiss(this)
        }
        mGlobalDialog?.mDismissRunnable = null
        mGlobalDialog = null
    }
}
