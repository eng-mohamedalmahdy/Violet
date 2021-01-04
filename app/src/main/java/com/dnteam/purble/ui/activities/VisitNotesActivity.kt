package com.dnteam.purble.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.ImageButton
import com.dnteam.purble.R
import com.dnteam.purble.database.NotesDatabase
import com.dnteam.purble.models.Note
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VisitNotesActivity : AppCompatActivity() {


    private lateinit var saveOrEdit: ImageButton
    private lateinit var delete: ImageButton
    private lateinit var titleContainer: TextInputLayout
    private lateinit var titleInput: TextInputEditText
    private lateinit var contentContainer: TextInputLayout
    private lateinit var contentInput: TextInputEditText
    private var inEditMode = false
    private lateinit var oldTitle: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit_notes)
        initComponents()
        initListeners()
        initViewsData()
    }


    private fun initComponents() {
        saveOrEdit = findViewById(R.id.edit_save)
        delete = findViewById(R.id.delete)
        titleContainer = findViewById(R.id.title_container)
        titleInput = findViewById(R.id.note_title)
        contentContainer = findViewById(R.id.content_container)
        contentInput = findViewById(R.id.note_content)
    }

    private fun initListeners() {
        saveOrEdit.setOnClickListener {
            if (!inEditMode) {
                saveOrEdit.setImageResource(R.drawable.ic_baseline_check_24)
                titleInput.isEnabled = true
                contentInput.isEnabled = true
                delete.isEnabled = false
                inEditMode = true

            } else {
                saveOrEdit.setImageResource(R.drawable.ic_baseline_edit_24)
                delete.isEnabled = true
                titleInput.isEnabled = false
                contentInput.isEnabled = false
                CoroutineScope(Dispatchers.IO).launch {
                    val res = NotesDatabase.getDatabase(this@VisitNotesActivity)?.notesDao()?.updateNote(oldTitle,
                            titleInput.text.toString(), contentInput.text.toString())

                    withContext(Dispatchers.Main) {
                        oldTitle = titleInput.text.toString()
                        inEditMode = false
                    }
                }

            }
        }
        delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                with(Dispatchers.IO) {
                    NotesDatabase.getDatabase(this@VisitNotesActivity)?.notesDao()?.deleteNote(titleInput.text.toString())
                }
                finish()
            }
        }
    }

    private fun initViewsData() {
        val note: Note = intent.getSerializableExtra("NOTE") as Note
        oldTitle = note.noteTitle
        titleInput.setText(note.noteTitle)
        contentInput.setText(note.noteContent)
        titleInput.isEnabled = false
        contentInput.isEnabled = false

    }

}