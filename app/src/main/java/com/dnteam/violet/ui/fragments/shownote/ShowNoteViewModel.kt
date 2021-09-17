package com.dnteam.violet.ui.fragments.shownote

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnteam.violet.data.database.NotesDatabase
import com.dnteam.violet.domain.stringContent
import com.dnteam.violet.models.Note
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class ShowNoteViewModel : ViewModel() {
    suspend fun updateNote(context: Context, oldTitle: String, note: Note) =
        NotesDatabase.getDatabase(context)?.notesDao()?.updateNote(
            oldTitle,
            note.noteTitle, note.noteContent
        )

    fun deleteNote(context: Context, title: String) {
        viewModelScope.launch {
            NotesDatabase.getDatabase(context)?.notesDao()
                ?.deleteNote(title)
        }
    }

}