package com.lightfeather.violet.ui.shownote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lightfeather.violet.R
import com.lightfeather.violet.databinding.FragmentShowNoteBinding
import com.lightfeather.violet.data.models.Note
import com.lightfeather.violet.domain.stringContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class ShowNoteFragment : Fragment() {

    private lateinit var binding: FragmentShowNoteBinding
    private lateinit var args: ShowNoteFragmentArgs
    private val noteViewModel: ShowNoteViewModel by viewModels()
    private var inEditMode = false
    private lateinit var oldTitle: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowNoteBinding.inflate(layoutInflater)
        binding.viewModel = noteViewModel
        binding.lifecycleOwner = this
        args = ShowNoteFragmentArgs.fromBundle(requireArguments())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initViewsData()
        if (noteViewModel.firstTime.value != false) ShowNoteGuide(
            binding,
            requireActivity()
        ).showOpenedNotesGuide {
            noteViewModel.firstTime.value = false
        }
    }


    private fun initListeners() {
        with(binding) {
            editSave.setOnClickListener {

                noteTitle.isEnabled = !inEditMode
                noteContent.isEnabled = !inEditMode
                delete.isEnabled = inEditMode

                editSave.setImageResource(
                    if (inEditMode) R.drawable.ic_baseline_edit_24 else
                        R.drawable.ic_baseline_check_24
                )

                if (inEditMode) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            noteViewModel.updateNote(oldTitle)
                        }
                        oldTitle = noteTitle.text.toString()
                        inEditMode = false
                    }
                } else {
                    inEditMode = true
                }
            }

            delete.setOnClickListener {
                lifecycleScope.launch {
                    noteViewModel.deleteNote(noteTitle.stringContent())
                    withContext(Dispatchers.Main) {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun initViewsData() {
        val note: Note = args.note
        oldTitle = note.noteTitle
        with(binding) {
            noteViewModel.noteTitle.postValue(note.noteTitle)
            noteViewModel.noteBody.postValue(note.noteContent)
            noteTitle.isEnabled = inEditMode
            noteContent.isEnabled = inEditMode
        }
    }
}