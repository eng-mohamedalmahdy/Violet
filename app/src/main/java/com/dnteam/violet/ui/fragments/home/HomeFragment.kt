package com.dnteam.violet.ui.fragments.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dnteam.violet.R
import com.dnteam.violet.databinding.FragmentHomeBinding
import com.dnteam.violet.domain.*
import com.dnteam.violet.data.models.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

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
        showGuide()
    }

    private fun showGuide() {
        viewModel.isShowingGuide.observe(viewLifecycleOwner) {
            if (it) {
                val homeGuideViews = HomeGuideViews(binding, requireActivity())
                homeGuideViews.startGuide { viewModel.firstTimeHome.value = false }
            }
        }
    }

    private fun initListeners() {

        with(binding) {
            addNoteFab.setOnClickListener { clickAdd() }

            search.setOnClickListener { clickSearch() }

            btSecretKey.setOnDragEndedListener { viewModel.keyLocation.value = it.location() }

            btSecretKey.setOnClickListener { clickHiddenKey() }
        }
    }

    private fun clickAdd() =
        findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToAddSecretNoteFragment())

    private fun clickSearch() {
        with(binding) {
            lifecycleScope.launch {
                titleContainer.error = null
                val res = async { viewModel.getNote(noteTitle.stringContent()) }
                respondToSearch(res.await())
            }
        }
    }

    private fun clickHiddenKey() {
        if (binding.btSecretKey.alpha == 1f) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTitlesDialog())
        } else {
            binding.btSecretKey.animate().setDuration(100).alpha(1f).start()
        }
    }

    private fun respondToSearch(note: Note?) {
        if (note == null)
            binding.titleContainer.error = resources.getString(R.string.not_found)
        else findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToVisitNotesFragment(note))
    }

    private fun showDialogIfNoPassword() {
        if (viewModel.getPassword().isEmpty())
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCreatePasswordDialog())
    }

    private fun setupKeyButton() {
        val location = viewModel.keyLocation.value ?: Pair(0f, 0f)
        with(binding) {
            btSecretKey.setLocation(location)
            btSecretKey.alpha = if (location != Pair(0f, 0f)) 0f else 1f
        }
    }
}