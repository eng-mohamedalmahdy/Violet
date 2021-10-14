package com.dnteam.violet.data.sharedpreference

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.dnteam.violet.data.sharedpreference.SharedPreferencesKeys.FIRST_TIME_ADD_NOTE
import com.dnteam.violet.data.sharedpreference.SharedPreferencesKeys.FIRST_TIME_HOME
import com.dnteam.violet.data.sharedpreference.SharedPreferencesKeys.FIRST_TIME_OPEN_NOTE
import com.dnteam.violet.data.sharedpreference.SharedPreferencesKeys.PASSWORD_KEY
import com.dnteam.violet.data.sharedpreference.SharedPreferencesKeys.PREFERENCE_NAME


private val Context.masterKey: MasterKey
    get() = MasterKey
        .Builder(this)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

private val Context.preferencesFile
    get() = EncryptedSharedPreferences.create(
        this,
        PREFERENCE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

fun Context.getKeyLocation(): Pair<Float, Float> =

    with(preferencesFile) {
        val first = getFloat(SharedPreferencesKeys.KEY_X, 0f)
        val second = getFloat(SharedPreferencesKeys.KEY_Y, 0f)
        return Pair(first, second)
    }

fun Context.setKeyLocation(location: Pair<Float, Float>) {

    with(preferencesFile.edit()) {
        putFloat(SharedPreferencesKeys.KEY_X, location.first)
        putFloat(SharedPreferencesKeys.KEY_Y, location.second)
        apply()
    }
}


fun Context.setPassword(password: String) =
    preferencesFile.edit().putString(PASSWORD_KEY, password).apply()


fun Context.getPassword(): String =
    preferencesFile.getString(PASSWORD_KEY, "") ?: ""


fun Context.setFirstTimeHome(firstTime: Boolean) =
    preferencesFile.edit().putBoolean(FIRST_TIME_HOME, firstTime).apply()

fun Context.isFirstTimeHome() =
    preferencesFile.getBoolean(FIRST_TIME_HOME, true)


fun Context.setFirstTimeAddNote(firstTime: Boolean) =
    preferencesFile.edit().putBoolean(FIRST_TIME_ADD_NOTE, firstTime).apply()

fun Context.isFirstTimeAddNote() = preferencesFile.getBoolean(FIRST_TIME_ADD_NOTE, true)

fun Context.setFirstTimeOpenNote(firstTime: Boolean) =
    preferencesFile.edit().putBoolean(FIRST_TIME_OPEN_NOTE, firstTime).apply()

fun Context.isFirstTimeOpenNote() = preferencesFile.getBoolean(FIRST_TIME_OPEN_NOTE, true)
