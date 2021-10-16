package com.dnteam.violet.ui.views

import android.widget.AdapterView

abstract class SpinnerOnItemSelectedListener : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {}
}