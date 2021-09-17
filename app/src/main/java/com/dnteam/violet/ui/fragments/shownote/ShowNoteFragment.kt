package com.dnteam.violet.ui.fragments.shownote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dnteam.violet.R
import com.dnteam.violet.data.database.NotesDatabase
import com.dnteam.violet.databinding.FragmentShowNoteBinding
import com.dnteam.violet.domain.stringContent
import com.dnteam.violet.models.Note
import kotlinx.coroutines.*

class ShowNoteFragment : Fragment() {

    private lateinit var binding: FragmentShowNoteBinding
    private lateinit var args: ShowNoteFragmentArgs
    private val viewModel: ShowNoteViewModel by viewModels()

    private var inEditMode = false
    private lateinit var oldTitle: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowNoteBinding.inflate(layoutInflater)
        args = ShowNoteFragmentArgs.fromBundle(requireArguments())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initViewsData()
    }


    private fun initListeners() {
        with(binding) {
            editSave.setOnClickListener {
                if (!inEditMode) {
                    editSave.setImageResource(R.drawable.ic_baseline_check_24)

                    noteTitle.isEnabled = true
                    noteContent.isEnabled = true
                    delete.isEnabled = false
                    inEditMode = true

                } else {
                    editSave.setImageResource(R.drawable.ic_baseline_edit_24)

                    delete.isEnabled = true
                    noteTitle.isEnabled = false
                    noteContent.isEnabled = false

                    CoroutineScope(Dispatchers.IO).launch {

                        withContext(this.coroutineContext) {
                            viewModel.updateNote(
                                requireContext(),
                                oldTitle,
                                Note(noteTitle.stringContent(), noteContent.stringContent())
                            )
                        }

                        lifecycleScope.launch {
                            oldTitle = noteTitle.text.toString()
                            inEditMode = false
                        }
                    }
                }
            }

            delete.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.deleteNote(requireContext(), noteTitle.stringContent())
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun initViewsData() {
        val note: Note = args.note
        oldTitle = note.noteTitle
        with(binding) {
            noteTitle.setText(note.noteTitle)
            noteContent.setText(note.noteContent)
            noteTitle.isEnabled = false
            noteContent.isEnabled = false
        }
    }
}