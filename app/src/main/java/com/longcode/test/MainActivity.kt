package com.longcode.test

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.longcode.globaldialog.GlobalDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DialogInterface.OnClickListener, DialogInterface.OnDismissListener, DialogInterface.OnCancelListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_replay) {
            rb_show_title_icon.isChecked = false
            rb_cancelable.isChecked = true
            rb_outside_touchable.isChecked = false
            rb_show_btn_icon.isChecked = false
            rb_type_center.isChecked = true
            rb_show_always.isChecked = true
        } else if (item.itemId == R.id.action_check) {
            val dialog = GlobalDialog.Builder(applicationContext)
                .setTitle(R.string.app_name)
                .setMessage(R.string.app_name)
                .setPositiveButton("Positive", this)
                .setNeutralButton("Neutral", this)
                .setNegativeButton("Negative", this)
                .setOnCancelListener(this)
                .setOnDismissListener(this)
                .setIcon(if (rb_show_title_icon.isChecked) R.mipmap.ic_launcher else 0)
                .setCancelable(rb_cancelable.isChecked)
                .setOutsideTouchable(rb_outside_touchable.isChecked)
                .setGravity(
                    when (rg_show_type.checkedRadioButtonId) {
                        R.id.rb_type_top -> GlobalDialog.DIALOG_GRAVITY_TOP
                        R.id.rb_type_bottom -> GlobalDialog.DIALOG_GRAVITY_BOTTOM
                        else -> GlobalDialog.DIALOG_GRAVITY_CENTER
                    }
                )
                .setShowType(
                    when (rg_show_time.checkedRadioButtonId) {
                        R.id.rb_show_short -> GlobalDialog.DIALOG_TYPE_SHORT_SHOW
                        R.id.rb_show_long -> GlobalDialog.DIALOG_TYPE_LONG_SHOW
                        else -> GlobalDialog.DIALOG_TYPE_ALWAYS_SHOW
                    }
                )
                .create()
            if (rb_show_btn_icon.isChecked) {
                val btnIcon = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)
                dialog.mPositiveButtonIcon = btnIcon
                dialog.mNeutralButtonIcon = btnIcon
                dialog.mNegativeButtonIcon = btnIcon
            }
            dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                Toast.makeText(this, "onPositiveBtnClick", Toast.LENGTH_SHORT).show()
            }
            DialogInterface.BUTTON_NEUTRAL -> {
                Toast.makeText(this, "onNeutralBtnClick", Toast.LENGTH_SHORT).show()
            }
            DialogInterface.BUTTON_NEGATIVE -> {
                Toast.makeText(this, "onNegativeBtnClick", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        Toast.makeText(this, "onDismiss", Toast.LENGTH_SHORT).show()
    }

    override fun onCancel(dialog: DialogInterface?) {
        Toast.makeText(this, "onCancel", Toast.LENGTH_SHORT).show()
    }
}
