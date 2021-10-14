package com.dnteam.violet.ui.addnote

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dnteam.violet.data.database.SecretNotesDao
import com.dnteam.violet.data.models.Note
import com.dnteam.violet.data.sharedpreference.isFirstTimeAddNote
import com.dnteam.violet.data.sharedpreference.setFirstTimeAddNote
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddSecretNoteViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var notesDao: SecretNotesDao

    val firstTimeHome = MutableLiveData(application.isFirstTimeAddNote())

    init {
        firstTimeHome.observeForever {
            application.setFirstTimeAddNote(it)
        }
    }

    suspend fun insertNote(note: Note) =
        try {
            notesDao.insertNote(note)
        } catch (e: SQLiteConstraintException) {
            -1L
        }

}