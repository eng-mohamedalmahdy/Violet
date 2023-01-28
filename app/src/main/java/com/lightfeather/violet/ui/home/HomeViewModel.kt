package com.lightfeather.violet.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.lightfeather.violet.data.Languages
import com.lightfeather.violet.data.database.SecretNotesDao
import com.lightfeather.violet.data.sharedpreference.*
import com.jakewharton.processphoenix.ProcessPhoenix
import com.lightfeather.violet.ui.MainActivity
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

    fun setLanguage(position: Int, activity: MainActivity) {
        activity.setLanguage(Languages.languages[position])
        ProcessPhoenix.triggerRebirth(getApplication())
    }

    fun getSelectedLanguage(activity: MainActivity): Int {
      return  Languages.languages
            .indexOfFirst { it.equals(activity.currentLanguage.language,true) }
    }
}