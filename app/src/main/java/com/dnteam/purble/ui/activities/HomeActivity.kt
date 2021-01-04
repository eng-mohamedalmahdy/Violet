package com.dnteam.purble.ui.activities

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dnteam.purble.R
import com.dnteam.purble.database.NotesDatabase
import com.dnteam.purble.models.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*

class HomeActivity : AppCompatActivity() {

    private lateinit var addNoteFab: FloatingActionButton
    private lateinit var search: ImageButton
    private lateinit var enterTitle: TextInputEditText
    private lateinit var enterTitleContainer: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initComponents()
        initListeners()

    }

    private fun initComponents() {
        addNoteFab = findViewById(R.id.add_note_fab)
        enterTitle = findViewById(R.id.note_title)
        enterTitleContainer = findViewById(R.id.title_container)
        search = findViewById(R.id.search)
    }

    private fun initListeners() {

        addNoteFab.setOnClickListener {
            startActivity(Intent(this@HomeActivity, AddSecretNoteActivity::class.java))
        }

        search.setOnClickListener {
            enterTitleContainer.error = null
            CoroutineScope(Dispatchers.IO).launch {
                val res = withContext(Dispatchers.IO) {
                    val title = enterTitle.text.toString()
                    NotesDatabase.getDatabase(this@HomeActivity)?.notesDao()?.getNote(title)
                }
                withContext(Dispatchers.Main) {
                    if (res == null) enterTitleContainer.error = resources.getString(R.string.not_found)
                    else {
                        val intent = Intent(this@HomeActivity, VisitNotesActivity::class.java)
                        intent.putExtra("NOTE", res)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}


//addNoteFab.setOnClickListener {
//    CoroutineScope(Dispatchers.IO).launch {
//        val res = withContext(Dispatchers.IO) {
//            try {
//                val note = Note("test", "test2")
//                NotesDatabase.getDatabase(this@HomeActivity)?.notesDao()?.insertNote(note)
//            } catch (e: SQLiteConstraintException) {
//                -1
//            }
//        }
//    }
//}