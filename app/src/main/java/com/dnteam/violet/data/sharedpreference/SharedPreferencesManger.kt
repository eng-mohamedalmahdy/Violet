package com.dnteam.violet.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.dnteam.violet.data.constants.PreferencesConstants
import com.dnteam.violet.data.constants.PreferencesConstants.FIRST_TIME_HOME
import com.dnteam.violet.data.constants.PreferencesConstants.PASSWORD_KEY


fun Context.getKeyLocation(): Pair<Float, Float> {
    val sharedPreferences =
        getSharedPreferences(PreferencesConstants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    with(sharedPreferences) {
        val first = getFloat(PreferencesConstants.KEY_X, 0f)
        val second = getFloat(PreferencesConstants.KEY_Y, 0f)
        return Pair(first, second)
    }
}

fun Context.setKeyLocation(location: Pair<Float, Float>) {
    val sharedPreferences =
        getSharedPreferences(PreferencesConstants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    with(editor) {
        putFloat(PreferencesConstants.KEY_X, location.first)
        putFloat(PreferencesConstants.KEY_Y, location.second)
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

fun Context.setFirstTimeHome(firstTime: Boolean) =
    EncryptedSharedPreferences.create(
        this,
        "passwords",
        getMasterKey(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    ).edit().putBoolean(FIRST_TIME_HOME, firstTime)

fun Context.isFirstTimeHome() = EncryptedSharedPreferences.create(
    this,
    "passwords",
    getMasterKey(),
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
).getBoolean(FIRST_TIME_HOME, true)