package com.dnteam.violet.ui.fragments.addnote

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import com.dnteam.violet.data.database.SecretNotesDao
import com.dnteam.violet.data.models.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddSecretNoteViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var notesDao: SecretNotesDao

    suspend fun insertNote(note: Note) =
        try {
            notesDao.insertNote(note)
        } catch (e: SQLiteConstraintException) {
            -1L
        }

}