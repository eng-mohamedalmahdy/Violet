package com.dnteam.violet.ui.shownote

import android.app.Activity
import com.dnteam.violet.R
import com.dnteam.violet.databinding.FragmentShowNoteBinding
import com.dnteam.violet.domain.guide
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity

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