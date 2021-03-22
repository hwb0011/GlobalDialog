# GlobalDialog [V1.0.0.arr](https://github.com/hwb0011/GlobalDialog/releases/download/v1.0.0/globaldialog-release-v1.0.0.aar)
A dialog can create by ApplicationContext in Android. It developed by Kotlin language. 

You can use GlobalDialog like AlertDialog, but GlobalDialog can be created by ApplicationContext, you can create it in anywhere don't think how to find your Activity, it will be very flexible just like Toast.

However, not all features have yet been fully implemented, but it will get stronger and stronger over time.

Android全局对话框，可通过ApplicationContext创建。Kotlin语言开发。

你可以像使用AlertDialog一样使用GlobalDialog，但是GlobalDialog可以使用ApplicationContext创建，你可以在任何地方创建对话框而不需要想怎么去拿到Activity对象，它可以像Toast一样非常灵活的使用。

但是目前还没有完全实现AlertDialog拥有的全部功能，但是它会随着时间变得越来越健壮。

# ChangeHistory

## v1.0.0

### Support   支持的功能

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

### Something special   一些特殊之处

* GlobalDialog support `setGravity(gravity: Int)`, you can specify it's location, currently supported are `DIALOG_GRAVITY_CENTER` / `DIALOG_GRAVITY_TOP` / `DIALOG_GRAVITY_BOTTOM`, corresponding animations are also provided, default config is `DIALOG_GRAVITY_CENTER`.
* GlobalDialog support `setShowType(showType: Int)`, used to set the time it appears on the screen just like Toast, currently supported are `DIALOG_TYPE_ALWAYS_SHOW` / `DIALOG_TYPE_SHORT_SHOW` / `DIALOG_TYPE_LONG_SHOW`, default config is `DIALOG_TYPE_ALWAYS_SHOW`.
* GlobalDialog support `setOutsideTouchable(outsideTouchable: Boolean)`, default config is `false`, if you want the TouchEvent or ClickEvent can dispatch to other View but don't effect GlobalDialog, you can use `setOutsideTouchable(true)`.

* GlobalDialog支持`setGravity(gravity: Int)`方法，你可以指定它弹出的位置，目前支持`DIALOG_GRAVITY_CENTER` / `DIALOG_GRAVITY_TOP` / `DIALOG_GRAVITY_BOTTOM`这三个参数，并且提供了对应的弹出动画，默认配置是`DIALOG_GRAVITY_CENTER`。
* GlobalDialog支持`setShowType(showType: Int)`方法，用来设置对话框在屏幕上显示的时间，以实现Toast一样的效果，目前支持`DIALOG_TYPE_ALWAYS_SHOW` / `DIALOG_TYPE_SHORT_SHOW` / `DIALOG_TYPE_LONG_SHOW`，默认配置是`DIALOG_TYPE_ALWAYS_SHOW`。
* GlobalDialog支持`setOutsideTouchable(outsideTouchable: Boolean)`方法，默认配置是`false`，如果你希望触摸事件或者点击事件可以在不影响对话框的前提下分发给对话框之外的View的话，可以调用`setOutsideTouchable(true)`。
