package com.dnteam.violet.ui.dialogs.titlesdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dnteam.violet.ui.adapters.KeysListAdapter
import com.dnteam.violet.data.sharedpreference.getPassword
import com.dnteam.violet.databinding.DialogKeysListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import android.view.ViewGroup

@AndroidEntryPoint
class TitlesDialog : DialogFragment() {

    private lateinit var binding: DialogKeysListBinding
    private val titlesViewModel: TitlesDialogViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?)
            : View {
        binding = DialogKeysListBinding.inflate(layoutInflater)
        binding.viewModel = titlesViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            keysList.layoutManager = LinearLayoutManager(context)

            titlesViewModel.allNotesTitles.observe(this@TitlesDialog) {
                keysList.adapter = KeysListAdapter(it)
            }

            titlesViewModel.password.observe(this@TitlesDialog) {
                keysList.visibility = if (it == context?.getPassword()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}
