package com.dnteam.purble.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


fun Context.getKeyLocation(): Pair<Float, Float> {
    val sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    val first = sharedPreferences.getFloat(Constants.KEY_X, 0f)
    val second = sharedPreferences.getFloat(Constants.KEY_Y, 0f)
    return Pair(first, second)
}

fun Context.setKeyLocation(location: Pair<Float, Float>) {
    val sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putFloat(Constants.KEY_X, location.first)
    editor.putFloat(Constants.KEY_Y, location.second)
    editor.apply()
}

fun Context.setPassword(password: String) {
    val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            "passwords",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    sharedPreferences.edit().putString(masterKeyAlias, password).apply()
}

fun Context.getPassword(): String {
    val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            "passwords",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    return sharedPreferences.getString(masterKeyAlias, "") ?: ""
}
