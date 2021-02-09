package com.longcode.globaldialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import androidx.annotation.*

class GlobalDialog(context: Context?) : DialogInterface{

    companion object {
        const val DIALOG_GRAVITY_CENTER = 0
        const val DIALOG_GRAVITY_TOP = 1
        const val DIALOG_GRAVITY_BOTTOM = 2
        private const val DIALOG_GRAVITY_CENTER_HORIZONTAL = 3
        private const val DIALOG_GRAVITY_CENTER_VERTICAL = 4
        private const val DIALOG_GRAVITY_LEFT = 5
        private const val DIALOG_GRAVITY_RIGHT = 6

        @IntDef(DIALOG_GRAVITY_CENTER, DIALOG_GRAVITY_TOP, DIALOG_GRAVITY_BOTTOM, DIALOG_GRAVITY_CENTER_HORIZONTAL,
                DIALOG_GRAVITY_CENTER_VERTICAL, DIALOG_GRAVITY_LEFT, DIALOG_GRAVITY_RIGHT)
        annotation class DialogGravity

        const val DIALOG_SHORT_SHOW_TIMEMILLS = 3000L
        const val DIALOG_LONG_SHOW_TIMEMILLS = 5000L

        const val DIALOG_TYPE_ALWAYS_SHOW = 0
        const val DIALOG_TYPE_SHORT_SHOW = 1
        const val DIALOG_TYPE_LONG_SHOW = 2

        @IntDef(DIALOG_TYPE_ALWAYS_SHOW, DIALOG_TYPE_SHORT_SHOW, DIALOG_TYPE_LONG_SHOW)
        annotation class DialogShowType
    }

    var mGravity: Int = DIALOG_GRAVITY_CENTER
        set(@DialogGravity value) {
            field = value
        }

    var mShowType: Int = DIALOG_TYPE_ALWAYS_SHOW
        set(@DialogShowType value) {
            field = value
        }

    val mContext: Context? = context?.applicationContext
    var mTitle: CharSequence? = null
    var mMessage: CharSequence? = null
    var mIconId: Int = 0
    var mIconUrl: String? = null
    var mPositiveButtonText: CharSequence? = null
    var mPositiveButtonListener: DialogInterface.OnClickListener? = null
    var mPositiveButtonIcon: Drawable? = null
    var mNegativeButtonText: CharSequence? = null
    var mNegativeButtonListener: DialogInterface.OnClickListener? = null
    var mNegativeButtonIcon: Drawable? = null
    var mNeutralButtonText: CharSequence? = null
    var mNeutralButtonListener: DialogInterface.OnClickListener? = null
    var mNeutralButtonIcon: Drawable? = null
    var mCancelable: Boolean = true
    var mOnCancelListener: DialogInterface.OnCancelListener? = null
    var mOnDismissListener: DialogInterface.OnDismissListener? = null
    var mOutsideTouchable: Boolean = false
    var mDismissRunnable: GlobalDismissRunnable? = null

    override fun dismiss() {
        dismiss(true)
    }

    fun dismiss(isCancel: Boolean) {
        mDismissRunnable?.dismiss(isCancel)
    }

    override fun cancel() {
        dismiss()
    }

    fun show() {
        GlobalDialogActivity.startActivity(this)
    }

    fun refresh() {
        show()
    }
    
    class Builder(applicationContext: Context?) {

        private val mDialog: GlobalDialog = GlobalDialog(applicationContext)

        fun setGravity(@DialogGravity gravity: Int): Builder {
            mDialog.mGravity = gravity
            return this
        }

        fun setShowType(@DialogShowType showType: Int): Builder {
            mDialog.mShowType = showType
            return this
        }

        fun setTitle(@StringRes titleId: Int): Builder {
            mDialog.mTitle = mDialog.mContext?.getText(titleId)
            return this
        }
        
        fun setTitle(title: CharSequence?): Builder {
            mDialog.mTitle = title
            return this
        }
        
        fun setMessage(@StringRes messageId: Int): Builder {
            mDialog.mMessage = mDialog.mContext?.getText(messageId)
            return this
        }
        
        fun setMessage(message: CharSequence?): Builder {
            mDialog.mMessage = message
            return this
        }
        
        fun setIcon(@DrawableRes iconId: Int): Builder {
            mDialog.mIconId = iconId
            mDialog.mIconUrl = null
            return this
        }

        fun setIcon(icon: String?): Builder {
            mDialog.mIconUrl = icon
            mDialog.mIconId = 0
            return this
        }
        
        fun setPositiveButton(@StringRes textId: Int, listener: DialogInterface.OnClickListener): Builder {
            mDialog.mPositiveButtonText = mDialog.mContext?.getText(textId)
            mDialog.mPositiveButtonListener = listener
            return this
        }
        
        fun setPositiveButton(text: CharSequence, listener: DialogInterface.OnClickListener): Builder {
            mDialog.mPositiveButtonText = text
            mDialog.mPositiveButtonListener = listener
            return this
        }
        
        fun setPositiveButtonIcon(icon: Drawable): Builder {
            mDialog.mPositiveButtonIcon = icon
            return this
        }
        
        fun setNegativeButton(@StringRes textId: Int, listener: DialogInterface.OnClickListener): Builder {
            mDialog.mNegativeButtonText = mDialog.mContext?.getText(textId)
            mDialog.mNegativeButtonListener = listener
            return this
        }
        
        fun setNegativeButton(text: CharSequence, listener: DialogInterface.OnClickListener): Builder {
            mDialog.mNegativeButtonText = text
            mDialog.mNegativeButtonListener = listener
            return this
        }
        
        fun setNegativeButtonIcon(icon: Drawable): Builder {
            mDialog.mNegativeButtonIcon = icon
            return this
        }
        
        fun setNeutralButton(@StringRes textId: Int, listener: DialogInterface.OnClickListener): Builder {
            mDialog.mNeutralButtonText = mDialog.mContext?.getText(textId)
            mDialog.mNeutralButtonListener = listener
            return this
        }
        
        fun setNeutralButton(text: CharSequence, listener: DialogInterface.OnClickListener): Builder {
            mDialog.mNeutralButtonText = text
            mDialog.mNeutralButtonListener = listener
            return this
        }
        
        fun setNeutralButtonIcon(icon: Drawable): Builder {
            mDialog.mNeutralButtonIcon = icon
            return this
        }
        
        fun setCancelable(cancelable: Boolean): Builder {
            mDialog.mCancelable = cancelable
            return this
        }

        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener): Builder {
            mDialog.mOnCancelListener = onCancelListener
            return this
        }
        
        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener): Builder {
            mDialog.mOnDismissListener = onDismissListener
            return this
        }

        fun setOutsideTouchable(outsideTouchable: Boolean): Builder {
            mDialog.mOutsideTouchable = outsideTouchable
            return this
        }
        
        fun create(): GlobalDialog {
            return mDialog
        }
        
        fun show(): GlobalDialog {
            create().show()
            return mDialog
        }
    }

    abstract class GlobalDismissRunnable: Runnable {
        override fun run() {
            dismiss(true)
        }

        abstract fun dismiss(isCancel: Boolean)
    }
    
}