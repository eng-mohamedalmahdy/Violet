package com.lightfeather.violet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lightfeather.violet.R
import com.lightfeather.violet.data.models.Note
import com.lightfeather.violet.databinding.FragmentHomeBinding
import com.lightfeather.violet.domain.location
import com.lightfeather.violet.domain.setLocation
import com.lightfeather.violet.domain.stringContent
import com.lightfeather.violet.ui.MainActivity
import com.lightfeather.violet.ui.views.SpinnerOnItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private var spinnerFirstTime = true

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
                homeGuideViews.startGuide {
                    viewModel.firstTimeHome.value = false
                    viewModel.isShowingGuide.value = false
                }
            }
        }
    }

    private fun initListeners() {

        with(binding) {
            addNoteFab.setOnClickListener { clickAdd() }

            search.setOnClickListener { clickSearch() }

            btSecretKey.setOnDragEndedListener { viewModel.keyLocation.value = it.location() }

            btSecretKey.setOnClickListener { clickHiddenKey() }

            language.setOnClickListener { languageSpinner.performClick() }

            languageSpinner.setSelection(viewModel.getSelectedLanguage(activity as MainActivity))

            languageSpinner.onItemSelectedListener =
                object : SpinnerOnItemSelectedListener() {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (!spinnerFirstTime) viewModel.setLanguage(
                            position,
                            activity as MainActivity
                        )
                        spinnerFirstTime = false
                    }

                }
        }
    }

    private fun clickAdd() = findNavController()
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
        if (binding.btSecretKey.alpha == 1f && findNavController().currentDestination?.id == R.id.homeFragment) {
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