package com.dnteam.violet.ui.shownote

import android.app.Application
import androidx.lifecycle.*
import com.dnteam.violet.data.database.SecretNotesDao
import com.dnteam.violet.data.sharedpreference.isFirstTimeOpenNote
import com.dnteam.violet.data.sharedpreference.setFirstTimeOpenNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowNoteViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {


    @Inject
    lateinit var notesDao: SecretNotesDao

    val noteTitle: MutableLiveData<String> = MutableLiveData()
    val noteBody: MutableLiveData<String> = MutableLiveData()
    val firstTime = MutableLiveData(application.isFirstTimeOpenNote())

    init {
        firstTime.observeForever { application.setFirstTimeOpenNote(it) }
    }

    fun updateNote(oldTitle: String) =
        viewModelScope.launch {
            notesDao.updateNote(oldTitle, noteTitle.value ?: "", noteBody.value ?: "")
        }


    fun deleteNote(title: String) =
        viewModelScope.launch { notesDao.deleteNote(title) }

}