package com.dnteam.violet.domain

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import kotlin.reflect.KFunction1

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


