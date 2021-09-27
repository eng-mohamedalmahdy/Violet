package com.dnteam.violet.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.dnteam.violet.data.constants.Constants
import com.dnteam.violet.data.constants.Constants.PASSWORD_KEY


fun Context.getKeyLocation(): Pair<Float, Float> {
    val sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    with(sharedPreferences){
        val first = getFloat(Constants.KEY_X, 0f)
        val second = getFloat(Constants.KEY_Y, 0f)
        return Pair(first, second)
    }
}

fun Context.setKeyLocation(location: Pair<Float, Float>) {
    val sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    with(editor){
        putFloat(Constants.KEY_X, location.first)
        putFloat(Constants.KEY_Y, location.second)
        apply()
    }
}

private fun Context.getMasterKey() = MasterKey
    .Builder(this)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()

fun Context.setPassword(password: String) {

    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        this,
        "passwords",
        getMasterKey(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    sharedPreferences.edit().putString(PASSWORD_KEY, password).apply()
}

fun Context.getPassword(): String {

    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        this,
        "passwords",
        getMasterKey(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    return sharedPreferences.getString(PASSWORD_KEY, "") ?: ""

}
