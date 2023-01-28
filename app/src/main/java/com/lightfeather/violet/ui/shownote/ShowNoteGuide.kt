package com.lightfeather.violet.ui.shownote

import android.app.Activity
import com.lightfeather.violet.R
import com.lightfeather.violet.databinding.FragmentShowNoteBinding
import com.lightfeather.violet.domain.guide

class ShowNoteGuide(val binding: FragmentShowNoteBinding, val activity: Activity) {


    private fun showDeleteButtonGuide(onGuide: () -> Unit) =
        binding.delete.guide(activity.getString(R.string.click_to_delete), onGuide)

    private fun showEditButtonGuide(onGuide: () -> Unit) =
        binding.editSave.guide(activity.getString(R.string.click_to_edit_or_update),onGuide)


    fun showOpenedNotesGuide(onGuide: () -> Unit) {
        showEditButtonGuide {
            showDeleteButtonGuide(onGuide)
        }
    }

}