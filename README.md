# GlobalDialog [V1.0.0.arr](https://github.com/hwb0011/GlobalDialog/releases/download/v1.0.0/globaldialog-release-v1.0.0.aar)
A dialog can create by ApplicationContext in Android. It developed by Kotlin language. 

You can use GlobalDialog like AlertDialog, but GlobalDialog can be created by ApplicationContext, you can create it in anywhere don't think how to find your Activity, it will be very flexible just like Toast.

However, not all features have yet been fully implemented, but it will get stronger and stronger over time.

# ChangeHistory

## v1.0.0

### Support

* class Builder(applicationContext: Context?)
* setTitle(@StringRes titleId: Int)
* setTitle(title: CharSequence?)
* setMessage(@StringRes messageId: Int)
* setMessage(message: CharSequence?)
* setIcon(@DrawableRes iconId: Int)
* setIcon(iconUrl: String?)
* setPositiveButton(@StringRes textId: Int, listener: DialogInterface.OnClickListener)
* setPositiveButton(text: CharSequence, listener: DialogInterface.OnClickListener)
* setNegativeButton(@StringRes textId: Int, listener: DialogInterface.OnClickListener)
* setNegativeButton(text: CharSequence, listener: DialogInterface.OnClickListener)
* setNeutralButton(@StringRes textId: Int, listener: DialogInterface.OnClickListener)
* setNeutralButton(text: CharSequence, listener: DialogInterface.OnClickListener)
* setCancelable(cancelable: Boolean)
* setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener)
* setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener)

### Something special

* GlobalDialog support `setGravity(gravity: Int)`, you can specify it's location, currently supported are `DIALOG_GRAVITY_CENTER` / `DIALOG_GRAVITY_TOP` / `DIALOG_GRAVITY_BOTTOM`, corresponding animations are also provided, default config is `DIALOG_GRAVITY_CENTER`.
* GlobalDialog support `setShowType(showType: Int)`, used to set the time it appears on the screen just like Toast, currently supported are `DIALOG_TYPE_ALWAYS_SHOW` / `DIALOG_TYPE_SHORT_SHOW` / `DIALOG_TYPE_LONG_SHOW`, default config is `DIALOG_TYPE_ALWAYS_SHOW`.
* GlobalDialog support `setOutsideTouchable(outsideTouchable: Boolean)`, default config is `false`, if you want the TouchEvent or ClickEvent can dispatch to other View but don't effect GlobalDialog, you can use `setOutsideTouchable(true)`.
