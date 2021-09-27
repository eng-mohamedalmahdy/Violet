package com.dnteam.violet.ui.fragments.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dnteam.violet.data.database.SecretNotesDao
import com.dnteam.violet.data.sharedpreference.getKeyLocation
import com.dnteam.violet.data.sharedpreference.getPassword
import com.dnteam.violet.data.sharedpreference.setKeyLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var notesDao: SecretNotesDao

    val keyLocation: MutableLiveData<Pair<Float, Float>> = MutableLiveData(application.getKeyLocation())

    suspend fun getNote(title: String) = notesDao.getNote(title)


    fun getPassword() = getApplication<Application>().getPassword()


}