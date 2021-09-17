package com.dnteam.violet.ui.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dnteam.violet.R
import com.dnteam.violet.data.database.NotesDatabase
import com.dnteam.violet.databinding.FragmentHomeBinding
import com.dnteam.violet.domain.*
import com.dnteam.violet.ui.dialogs.CreatePasswordDialog
import com.dnteam.violet.ui.dialogs.KeysDialog
import com.dnteam.violet.models.Note
import kotlinx.coroutines.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private var d = Pair(0f, 0f)
    private var lastAction = 0
    private var lastClick = -1L
    private var saveDisabled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        setupKeyButton()
        showDialogIfNoPassword()
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {

        with(binding) {
            addNoteFab.setOnClickListener { clickAdd() }

            search.setOnClickListener { clickSearch() }

            btSecretKey.setOnTouchListener { view, event ->

                btSecretKey.animate().setDuration(2000).alpha(1f).start()

                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        d = Pair(view.x - event.rawX, view.y - event.rawY)

                        lastAction = MotionEvent.ACTION_DOWN
                        saveDisabled =
                            (lastClick != -1L && System.currentTimeMillis() - lastClick < 200)
                        if (saveDisabled) {
                            showKeysList()
                        }
                        lastClick = System.currentTimeMillis()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        view.setLocation( event.rawY + d.second,event.rawX + d.first)
                        lastAction = MotionEvent.ACTION_MOVE
                        viewModel.setKeyLocation(requireContext(),view.location())
                    }
                }
                false
            }
        }
    }

    private fun clickAdd() =
        findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToAddSecretNoteFragment())


    private fun clickSearch() {
        with(binding) {
            titleContainer.error = null
            lifecycleScope.launch {
                val res = async {
                    viewModel.getNote(requireContext(), noteTitle.stringContent())
                }
                withContext(Dispatchers.Main) {
                    with(res.await()) { respondToSearch(this) }
                }
            }
        }
    }

    private fun respondToSearch(note: Note?) {
        with(binding) {
            if (note == null)
                titleContainer.error = resources.getString(R.string.not_found)
            else {
                findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToVisitNotesFragment(note))
            }
        }
    }

    private fun showDialogIfNoPassword() {
        val dialog = CreatePasswordDialog(requireContext())
        if (context?.getPassword().isNullOrEmpty()) {
            dialog.show()
            dialog.window?.setLayout(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun setupKeyButton() {
        val location = requireContext().getKeyLocation()
        with(binding) {
            btSecretKey.x = location.first
            btSecretKey.y = location.second
            if (location != Pair(0f, 0f)) {
                btSecretKey.alpha = 0f
            }
        }
    }

    private fun showKeysList() {
        val dialog = KeysDialog(requireContext())
        dialog.show()
        val window: Window = dialog.window!!
        window.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
    }
}

