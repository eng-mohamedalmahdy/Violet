package com.dnteam.violet.ui.addnote

import android.app.Activity
import com.dnteam.violet.R
import com.dnteam.violet.databinding.FragmentAddSecretNoteBinding
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity

class AddNoteGuideViews(val binding: FragmentAddSecretNoteBinding, val activity: Activity) {
    private fun showNoteTitleGuide(onGuide: () -> Unit) {
        GuideView.Builder(activity).apply {
            setTitle(activity.getString(R.string.type_note_title))
            setDismissType(DismissType.anywhere)
            setTargetView(binding.titleContainer)
            setGravity(Gravity.center)
            setGuideListener { onGuide() }
        }.build().show()
    }

    private fun showNoteContentGuide(onGuide: () -> Unit) {
        GuideView.Builder(activity).apply {
            setTitle(activity.getString(R.string.type_note_content))
            setDismissType(DismissType.anywhere)
            setTargetView(binding.contentContainer)
            setGravity(Gravity.center)
            setGuideListener { onGuide() }
        }.build().show()
    }

    private fun showNoteSaveGuide(onGuide: () -> Unit) {
        GuideView.Builder(activity).apply {
            setTitle(activity.getString(R.string.click_to_save))
            setDismissType(DismissType.anywhere)
            setTargetView(binding.save)
            setGravity(Gravity.center)
            setGuideListener { onGuide() }
        }.build().show()
    }

    fun showAddNoteGuide(onGuide: () -> Unit) {
        showNoteTitleGuide {
            showNoteContentGuide {
                showAddNoteGuide {
                    onGuide()
                }
            }
        }
    }

}