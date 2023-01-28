package com.lightfeather.violet.ui.createpassword

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.lightfeather.violet.data.sharedpreference.setPassword
import com.lightfeather.violet.databinding.DialogCreatePasswordBinding
import com.lightfeather.violet.domain.stringContent
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import com.lightfeather.violet.ui.home.HomeViewModel

class CreatePasswordDialog : DialogFragment() {

    private lateinit var binding: DialogCreatePasswordBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, instance: Bundle?
    ): View {
        binding = DialogCreatePasswordBinding.inflate(layoutInflater)

        with(binding) {
            confirmButton.setOnClickListener {
                context?.setPassword(password.stringContent())
                findNavController().navigateUp()
                viewModel.isShowingGuide.value = true
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}