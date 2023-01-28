package com.lightfeather.violet.ui.notestitles.titlesdialog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.lightfeather.violet.data.database.SecretNotesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TitlesDialogViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var notesDao: SecretNotesDao

    var password: MutableLiveData<String> = MutableLiveData("")

    var allNotesTitles : LiveData<List<String>> = liveData { emit(notesDao.getAllKeys()) }

}