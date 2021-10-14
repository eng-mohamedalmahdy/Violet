package com.dnteam.violet.ui.home

import android.app.Activity
import com.dnteam.violet.R
import com.dnteam.violet.databinding.FragmentHomeBinding
import com.dnteam.violet.domain.guide

class HomeGuideViews(val binding: FragmentHomeBinding, private val activity: Activity) {

    private fun showKeyGuide(onGuide: () -> Unit) =
        binding.btSecretKey.guide(activity.getString(R.string.move_key_guide_content),onGuide)


    private fun showFabGuide(onGuide: () -> Unit) =
        binding.addNoteFab.guide(activity.getString(R.string.click_to_add_note),onGuide)


    private fun showTitleGuide(onGuide: () -> Unit) =
        binding.titleContainer.guide(activity.getString(R.string.search_note_title),onGuide)


    private fun showSearchButtonGuide(onGuide: () -> Unit) =
        binding.search.guide(activity.getString(R.string.click_to_search),onGuide)


    fun startGuide(onGuide: () -> Unit) {
        showKeyGuide {
            showFabGuide {
                showTitleGuide {
                    showSearchButtonGuide(onGuide)
                }
            }
        }
    }

}

