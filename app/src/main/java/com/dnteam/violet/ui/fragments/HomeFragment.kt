package com.dnteam.violet.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dnteam.violet.R
import com.dnteam.violet.database.NotesDatabase
import com.dnteam.violet.databinding.FragmentHomeBinding
import com.dnteam.violet.ui.dialogs.CreatePasswordDialog
import com.dnteam.violet.ui.dialogs.KeysDialog
import com.dnteam.violet.util.getKeyLocation
import com.dnteam.violet.util.getPassword
import com.dnteam.violet.util.setKeyLocation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var addNoteFab: FloatingActionButton
    private lateinit var search: ImageButton
    private lateinit var enterTitle: TextInputEditText
    private lateinit var enterTitleContainer: TextInputLayout
    private lateinit var background: ImageView
    private lateinit var container: ConstraintLayout
    private lateinit var secret: ImageButton

    private var dX = 0f
    private var dY = 0f
    private var lastAction = 0
    private var lastClick = -1L
    private var saveDisabled = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initListeners()
        setComponents()
        showDialogIfNoPassword()
    }

    private fun showDialogIfNoPassword() {
        val dialog = CreatePasswordDialog(requireContext())
        if (requireActivity().getPassword().isEmpty()) {
            dialog.show()
            val window: Window = dialog.window!!
            window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        }
    }


    private fun initComponents() {
        addNoteFab = binding.addNoteFab
        enterTitle = binding.noteTitle
        enterTitleContainer = binding.titleContainer
        search = binding.search
        secret = binding.btSecretKey
        background = binding.background
        container = binding.container
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {

        addNoteFab.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddSecretNoteFragment()) }

        search.setOnClickListener {
            enterTitleContainer.error = null
            CoroutineScope(Dispatchers.IO).launch {
                val res = withContext(Dispatchers.IO) {
                    val title = enterTitle.text.toString()
                    NotesDatabase.getDatabase(requireContext())?.notesDao()?.getNote(title)
                }
                withContext(Dispatchers.Main) {
                    if (res == null) enterTitleContainer.error = resources.getString(R.string.not_found)
                    else {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToVisitNotesFragment(res))
                    }
                }
            }
        }



        secret.setOnTouchListener { view, event ->
            Log.d("TAG", "initListeners: found")
            secret.animate().setDuration(2000).alpha(1f).start()
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                    lastAction = MotionEvent.ACTION_DOWN
                    saveDisabled = (lastClick != -1L && System.currentTimeMillis() - lastClick < 200)
                    if (saveDisabled) {
                        showKeysList()
                    }

                    lastClick = System.currentTimeMillis()
                }
                MotionEvent.ACTION_MOVE -> {
                    view.y = event.rawY + dY
                    view.x = event.rawX + dX
                    lastAction = MotionEvent.ACTION_MOVE
                    requireContext().setKeyLocation(Pair(view.x, view.y))

                }

                MotionEvent.ACTION_UP -> {
                }


                else -> return@setOnTouchListener false
            }
            false
        }
    }

    private fun showKeysList() {
        val dialog = KeysDialog(requireContext())
        dialog.show()
        val window: Window = dialog.window!!
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
    }

    private fun setComponents() {
        val location = requireContext().getKeyLocation()
        secret.x = location.first
        secret.y = location.second
        if (location != Pair(0f, 0f)) {
            secret.alpha = 0f
        }
    }
}
