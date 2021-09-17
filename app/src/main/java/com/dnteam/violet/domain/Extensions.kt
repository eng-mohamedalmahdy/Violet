package com.dnteam.violet.domain

import android.view.View
import android.widget.EditText

fun EditText.stringContent() = this.text.toString()

fun View.location() = Pair(this.x, this.y)
fun View.setLocation(x: Float, y: Float) {
    this.x = x; this.y = y
}