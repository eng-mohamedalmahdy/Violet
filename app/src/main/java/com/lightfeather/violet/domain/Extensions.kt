package com.lightfeather.violet.domain

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.widget.EditText
import android.widget.Toast
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity


fun EditText.stringContent() = this.text.toString()

fun View.location() = Pair(this.x, this.y)
fun View.setLocation(x: Float, y: Float) {
    this.x = x; this.y = y
}

fun View.setLocation(location: Pair<Float, Float>) {
    this.x = location.first; this.y = location.second
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

fun Context.toast(message: Int, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, getString(message), length).show()

fun Context.copyToClipBoard(text: String) {
    val clipboard =
        this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("${applicationInfo.name} Clip", text)
    clipboard.setPrimaryClip(clip)
}

fun View.guide(message: String, onGuide: () -> Unit) =
    GuideView.Builder(getActivity(context)).apply {
        setTitle(message)
        setDismissType(DismissType.anywhere)
        setTargetView(this@guide)
        setGravity(Gravity.center)
        setGuideListener { onGuide() }
    }.build().show()


fun getActivity(context: Context?): Activity? {
    if (context == null) {
        return null
    } else if (context is ContextWrapper) {
        return if (context is Activity) {
            context
        } else {
            getActivity(context.baseContext)
        }
    }
    return null
}