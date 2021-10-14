package com.dnteam.violet.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dnteam.violet.data.database.SecretNotesDao
import com.dnteam.violet.data.sharedpreference.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var notesDao: SecretNotesDao

    val keyLocation: MutableLiveData<Pair<Float, Float>> =
        MutableLiveData(application.getKeyLocation())

    val firstTimeHome = MutableLiveData(application.isFirstTimeHome())

    val isShowingGuide = liveData {
        emit(
            application.isFirstTimeHome()
                    && application.getPassword().isNotEmpty()
        )
    }


    init {
        keyLocation.observeForever { application.setKeyLocation(it) }
        firstTimeHome.observeForever { application.setFirstTimeHome(it) }
    }

    suspend fun getNote(title: String) = notesDao.getNote(title)

    fun getPassword() = getApplication<Application>().getPassword()


}