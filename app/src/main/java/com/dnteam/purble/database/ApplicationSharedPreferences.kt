package com.dnteam.purble.database

import android.content.Context
import com.dnteam.purble.Constants

class ApplicationSharedPreferences {
    companion object {
        fun setKeyLocation(context: Context, x: Float, y: Float) {
            val sharedPreferences = context.getSharedPreferences(Constants.LOCATION_KEY, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putFloat(Constants.KEY_X, x)
            editor.putFloat(Constants.KEY_X, y)
            editor.apply()
        }

        fun getKeyLocation(context: Context): Pair<Float, Float> {
            val sharedPreferences = context.getSharedPreferences(Constants.LOCATION_KEY, Context.MODE_PRIVATE)
            return Pair(sharedPreferences.getFloat(Constants.KEY_X,0.0f),sharedPreferences.getFloat(Constants.KEY_Y,0.0f))
        }
    }
}