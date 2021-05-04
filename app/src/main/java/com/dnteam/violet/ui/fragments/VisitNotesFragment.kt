package com.dnteam.violet.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.dnteam.violet.R
import com.dnteam.violet.database.NotesDatabase
import com.dnteam.violet.databinding.FragmentVisitNotesBinding
import com.dnteam.violet.models.Note
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VisitNotesFragment : Fragment() {

    private lateinit var binding: FragmentVisitNotesBinding
    private lateinit var args: VisitNotesFragmentArgs
    private lateinit var saveOrEdit: ImageButton
    private lateinit var delete: ImageButton
    private lateinit var titleContainer: TextInputLayout
    private lateinit var titleInput: TextInputEditText
    private lateinit var contentContainer: TextInputLayout
    private lateinit var contentInput: TextInputEditText
    private var inEditMode = false
    private lateinit var oldTitle: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentVisitNotesBinding.inflate(layoutInflater)
        args = VisitNotesFragmentArgs.fromBundle(requireArguments())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initListeners()
        initViewsData()
    }


    private fun initComponents() {
        saveOrEdit = binding.editSave
        delete = binding.delete
        titleContainer = binding.titleContainer
        titleInput = binding.noteTitle
        contentContainer = binding.contentContainer
        contentInput = binding.noteContent
    }

    private fun initListeners() {
        saveOrEdit.setOnClickListener {
            if (!inEditMode) {
                saveOrEdit.setImageResource(R.drawable.ic_baseline_check_24)
                titleInput.isEnabled = true
                contentInput.isEnabled = true
                delete.isEnabled = false
                inEditMode = true

            } else {
                saveOrEdit.setImageResource(R.drawable.ic_baseline_edit_24)
                delete.isEnabled = true
                titleInput.isEnabled = false
                contentInput.isEnabled = false
                CoroutineScope(Dispatchers.IO).launch {
                    val res = NotesDatabase.getDatabase(requireContext())?.notesDao()?.updateNote(oldTitle,
                            titleInput.text.toString(), contentInput.text.toString())

                    withContext(Dispatchers.Main) {
                        oldTitle = titleInput.text.toString()
                        inEditMode = false
                    }
                }

            }
        }
        delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                NotesDatabase.getDatabase(requireContext())?.notesDao()?.deleteNote(titleInput.text.toString())
                withContext(Dispatchers.Main) {
                    requireActivity().onBackPressed()
                }
            }
        }
    }

    private fun initViewsData() {
        val note: Note = args.note
        oldTitle = note.noteTitle
        titleInput.setText(note.noteTitle)
        contentInput.setText(note.noteContent)
        titleInput.isEnabled = false
        contentInput.isEnabled = false

    }

}