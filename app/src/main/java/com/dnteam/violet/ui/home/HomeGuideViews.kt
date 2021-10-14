package com.dnteam.violet.ui.home

import android.app.Activity
import com.dnteam.violet.R
import com.dnteam.violet.databinding.FragmentHomeBinding
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity

class HomeGuideViews(val binding: FragmentHomeBinding, private val activity: Activity) {

    private fun showKeyGuide(onGuide: () -> Unit) =
        GuideView.Builder(activity).apply {
            setTitle(activity.getString(R.string.move_key_guide_title))
            setContentText(activity.getString(R.string.move_key_guide_content))
            setDismissType(DismissType.anywhere)
            setTargetView(binding.btSecretKey)
            setGravity(Gravity.center)
            setGuideListener { onGuide() }
        }.build().show()

    private fun showFabGuide(onGuide: () -> Unit) =
        GuideView.Builder(activity).apply {
            setTitle(activity.getString(R.string.add_note_title))
            setContentText(activity.getString(R.string.add_note_body))
            setDismissType(DismissType.anywhere)
            setTargetView(binding.addNoteFab)
            setGravity(Gravity.center)
            setGuideListener { onGuide() }
        }.build().show()

    private fun showTitleGuide(onGuide: () -> Unit) =
        GuideView.Builder(activity).apply {
            setTitle(activity.getString(R.string.search_note_title))
            setContentText(activity.getString(R.string.search_note_body))
            setDismissType(DismissType.anywhere)
            setTargetView(binding.titleContainer)
            setGravity(Gravity.center)
            setGuideListener { onGuide() }
        }.build().show()


    private fun showSearchButtonGuide(onGuide: () -> Unit) =
        GuideView.Builder(activity).apply {
            setTitle(activity.getString(R.string.click_to_search))
            setDismissType(DismissType.anywhere)
            setTargetView(binding.search)
            setGravity(Gravity.center)
            setGuideListener { onGuide() }
        }.build().show()

    fun startGuide(onGuide: () -> Unit) {
        showKeyGuide {
            showFabGuide {
                showTitleGuide {
                    showSearchButtonGuide { onGuide() }
                }
            }
        }
    }


}

