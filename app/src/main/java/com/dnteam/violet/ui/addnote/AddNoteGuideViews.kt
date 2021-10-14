package com.dnteam.violet.ui.addnote

import android.app.Activity
import com.dnteam.violet.R
import com.dnteam.violet.databinding.FragmentAddSecretNoteBinding
import com.dnteam.violet.domain.guide
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity

class AddNoteGuideViews(val binding: FragmentAddSecretNoteBinding, val activity: Activity) {


    private fun showNoteTitleGuide(onGuide: () -> Unit) =
        binding.titleContainer.guide(activity.getString(R.string.type_note_title), onGuide)


    private fun showNoteContentGuide(onGuide: () -> Unit) =
        binding.contentContainer.guide(activity.getString(R.string.type_note_content), onGuide)


    private fun showNoteSaveGuide(onGuide: () -> Unit) =
        binding.save.guide(activity.getString(R.string.click_to_save), onGuide)


    fun showAddNoteGuide(onGuide: () -> Unit) {
        showNoteTitleGuide {
            showNoteContentGuide {
                showNoteSaveGuide(onGuide)
            }
        }
    }

}