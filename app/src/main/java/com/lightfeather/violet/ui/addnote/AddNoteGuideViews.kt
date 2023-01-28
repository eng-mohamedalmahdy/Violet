package com.lightfeather.violet.ui.addnote

import android.app.Activity
import com.lightfeather.violet.R
import com.lightfeather.violet.databinding.FragmentAddSecretNoteBinding
import com.lightfeather.violet.domain.guide

class AddNoteGuideViews(val binding: FragmentAddSecretNoteBinding, val activity: Activity) {


    private fun showNoteTitleGuide(onGuide: () -> Unit) =
        binding.titleContainer.guide(activity.getString(R.string.type_note_title), onGuide)


    private fun showNoteContentGuide(onGuide: () -> Unit) =
        binding.contentContainer.guide(activity.getString(R.string.type_note_content), onGuide)


    private fun showNoteSaveGuide(onGuide: () -> Unit) =
        binding.save.guide(activity.getString(R.string.click_to_save), onGuide)


    fun showAddNoteGuide(onGuide: () -> Unit) {
        showNoteTitleGuide {
                showNoteSaveGuide(onGuide)
        }
    }
}