package com.dnteam.violet.ui.home

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.dnteam.violet.data.Languages
import com.dnteam.violet.data.database.SecretNotesDao
import com.dnteam.violet.data.sharedpreference.*
import com.jakewharton.processphoenix.ProcessPhoenix
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {


    @Inject
    lateinit var notesDao: SecretNotesDao

    val keyLocation: MutableLiveData<Pair<Float, Float>> =
        MutableLiveData(application.getKeyLocation())

    val firstTimeHome = MutableLiveData(application.isFirstTimeHome())

    val isShowingGuide = MutableLiveData(
        application.isFirstTimeHome()
                && application.getPassword().isNotEmpty()
    )


    init {
        keyLocation.observeForever { application.setKeyLocation(it) }
        firstTimeHome.observeForever {
            application.setFirstTimeHome(it)
        }
    }

    suspend fun getNote(title: String) = notesDao.getNote(title)

    fun getPassword() = getApplication<Application>().getPassword()

    fun setLanguage(position: Int, activity: LocalizationActivity) {
        activity.setLanguage(Languages.languages[position])
        ProcessPhoenix.triggerRebirth(getApplication())
    }

    fun getSelectedLanguage(activity: LocalizationActivity): Int {
      return  Languages.languages
            .indexOfFirst { it.equals(activity.getCurrentLanguage().language,true) }
    }
}