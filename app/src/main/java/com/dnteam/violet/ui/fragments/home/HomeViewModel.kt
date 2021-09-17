package com.dnteam.violet.ui.fragments.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.dnteam.violet.data.database.NotesDatabase
import com.dnteam.violet.domain.location
import com.dnteam.violet.domain.setKeyLocation

class HomeViewModel : ViewModel() {
    suspend fun getNote(context: Context, title: String) =
        NotesDatabase.getDatabase(context)?.notesDao()?.getNote(title)

    fun setKeyLocation(context: Context, location: Pair<Float, Float>) =
        context.setKeyLocation(location)


}