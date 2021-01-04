package com.dnteam.purble.ui.activities

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.dnteam.purble.R
import com.dnteam.purble.database.NotesDatabase
import com.dnteam.purble.models.Note
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddSecretNoteActivity : AppCompatActivity() {

    private lateinit var save: ImageButton
    private lateinit var titleContainer: TextInputLayout
    private lateinit var titleInput: TextInputEditText
    private lateinit var contentContainer: TextInputLayout
    private lateinit var contentInput: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_secret_note)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        save = findViewById(R.id.save)
        titleContainer = findViewById(R.id.title_container)
        titleInput = findViewById(R.id.note_title)
        contentContainer = findViewById(R.id.content_container)
        contentInput = findViewById(R.id.note_content)
    }

    private fun initListeners() {
        save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val res = withContext(Dispatchers.IO) {
                    try {
                        titleContainer.error = null
                        val note = Note(titleInput.text.toString(), contentInput.text.toString())
                        NotesDatabase.getDatabase(this@AddSecretNoteActivity)?.notesDao()?.insertNote(note)
                    } catch (e: SQLiteConstraintException) {
                        -1L
                    }
                }
                withContext(Dispatchers.Main) {
                    if (res == -1L) {
                        titleContainer.error = resources.getString(R.string.invalid_title);
                    } else {
                        finish()
                    }
                }
                withContext(Dispatchers.Main){
                    Handler().postDelayed({ titleContainer.error = null},1000)
                }
            }
        }
    }
}