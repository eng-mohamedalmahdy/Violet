package com.lightfeather.violet.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lightfeather.violet.R
import com.lightfeather.violet.data.models.Note
import com.lightfeather.violet.databinding.FragmentAddSecretNoteBinding
import com.lightfeather.violet.domain.stringContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddSecretNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddSecretNoteBinding
    private val viewModel: AddSecretNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSecretNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        if (viewModel.firstTimeHome.value != false) AddNoteGuideViews(
            binding,
            requireActivity()
        ).showAddNoteGuide {
            viewModel.firstTimeHome.value = false
        }
    }

    private fun initListeners() {
        with(binding) {
            save.setOnClickListener {
                val note = Note(noteTitle.stringContent(), noteContent.stringContent())
                lifecycleScope.launch {

                    titleContainer.error = null

                    val res = async { viewModel.insertNote(note) }

                    if (res.await() == -1L) {
                        titleContainer.error = resources.getString(R.string.invalid_title)
                    } else {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }
}