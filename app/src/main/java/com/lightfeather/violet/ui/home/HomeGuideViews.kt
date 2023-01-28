package com.lightfeather.violet.ui.home

import android.app.Activity
import com.lightfeather.violet.R
import com.lightfeather.violet.databinding.FragmentHomeBinding
import com.lightfeather.violet.domain.guide

class HomeGuideViews(val binding: FragmentHomeBinding, private val activity: Activity) {

    private fun showKeyGuide(onGuide: () -> Unit) =
        binding.btSecretKey.guide(activity.getString(R.string.move_key_guide_content), onGuide)


    private fun showFabGuide(onGuide: () -> Unit) =
        binding.addNoteFab.guide(activity.getString(R.string.click_to_add_note), onGuide)


    private fun showTitleGuide(onGuide: () -> Unit) =
        binding.titleContainer.guide(activity.getString(R.string.search_note_title), onGuide)


    private fun showSearchButtonGuide(onGuide: () -> Unit) =
        binding.search.guide(activity.getString(R.string.click_to_search), onGuide)

    private fun showChangeLanguageGuide(onGuide: () -> Unit) =
        binding.language.guide(activity.getString(R.string.select_language_here), onGuide)


    fun startGuide(onGuide: () -> Unit) {
        showKeyGuide {
            showFabGuide {
                showTitleGuide {
                    showSearchButtonGuide {
                        showChangeLanguageGuide(onGuide)
                    }
                }
            }
        }
    }

}

