package com.dnteam.violet.ui.fragments

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.dnteam.violet.R
import com.dnteam.violet.database.NotesDatabase
import com.dnteam.violet.databinding.FragmentAddSecretNoteBinding
import com.dnteam.violet.models.Note
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddSecretNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddSecretNoteBinding
    private lateinit var save: ImageButton
    private lateinit var titleContainer: TextInputLayout
    private lateinit var titleInput: TextInputEditText
    private lateinit var contentContainer: TextInputLayout
    private lateinit var contentInput: TextInputEditText


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddSecretNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        save = binding.save
        titleContainer = binding.titleContainer
        titleInput = binding.noteTitle
        contentContainer = binding.contentContainer
        contentInput = binding.noteContent
    }

    private fun initListeners() {
        save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val res = withContext(Dispatchers.IO) {
                    try {
                        titleContainer.error = null
                        val note = Note(titleInput.text.toString(), contentInput.text.toString())
                        NotesDatabase.getDatabase(requireContext())?.notesDao()?.insertNote(note)
                    } catch (e: SQLiteConstraintException) {
                        -1L
                    }
                }
                withContext(Dispatchers.Main) {
                    if (res == -1L) {
                        titleContainer.error = resources.getString(R.string.invalid_title);
                    } else {
                        requireActivity().onBackPressed()
                    }
                }
                withContext(Dispatchers.Main) {
                    Handler(Looper.getMainLooper()).postDelayed({ titleContainer.error = null }, 1000)
                }
            }
        }
    }
}