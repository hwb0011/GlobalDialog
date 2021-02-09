/*
 * IGlobalDialogView.kt
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */
package com.longcode.globaldialog

import android.view.View
import android.view.Window

interface IGlobalDialogView{

    fun initView(globalDialog: GlobalDialog): View

    fun initWindowAttributes(window: Window, canOutsideClick: Boolean)

    fun getInAnimId(): Int

    fun getOutAnimId(): Int

    fun getDialogGravity(): Int
}