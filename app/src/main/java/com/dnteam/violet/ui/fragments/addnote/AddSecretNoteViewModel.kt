package com.dnteam.violet.ui.fragments.addnote

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import com.dnteam.violet.data.database.NotesDatabase
import com.dnteam.violet.models.Note

class AddSecretNoteViewModel : ViewModel() {

    suspend fun insertNote(context: Context, note: Note) =
        try {
            NotesDatabase.getDatabase(context)?.notesDao()?.insertNote(note)
        } catch (e: SQLiteConstraintException) {
            -1L
        }

}