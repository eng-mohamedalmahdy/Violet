package com.dnteam.purble.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.dnteam.purble.R
import com.dnteam.purble.database.NotesDatabase
import com.dnteam.purble.ui.dialogs.CreatePasswordDialog
import com.dnteam.purble.ui.dialogs.KeysDialog
import com.dnteam.purble.util.getKeyLocation
import com.dnteam.purble.util.getPassword
import com.dnteam.purble.util.setKeyLocation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeActivity : AppCompatActivity() {

    private lateinit var addNoteFab: FloatingActionButton
    private lateinit var search: ImageButton
    private lateinit var enterTitle: TextInputEditText
    private lateinit var enterTitleContainer: TextInputLayout
    private lateinit var background: ImageView
    private lateinit var container: ConstraintLayout
    private lateinit var secret: ImageButton

    private var dX = 0f
    private var dY = 0f
    private var lastAction = 0
    private var lastClick = -1L
    private var saveDisabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initComponents()
        initListeners()
        setComponents()
        showDialogIfNoPassword()
    }

    private fun showDialogIfNoPassword() {
        val dialog = CreatePasswordDialog(this)
        if (getPassword().isEmpty()) {
            dialog.show()
            val window: Window = dialog.window!!
            window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        }
    }


    private fun initComponents() {
        addNoteFab = findViewById(R.id.add_note_fab)
        enterTitle = findViewById(R.id.note_title)
        enterTitleContainer = findViewById(R.id.title_container)
        search = findViewById(R.id.search)
        secret = findViewById(R.id.bt_secret_key)
        background = findViewById(R.id.background)
        container = findViewById(R.id.container)
    }

    @SuppressLint("ClickableViewAccessibility")
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


        secret.setOnTouchListener { view, event ->
            secret.visibility = View.VISIBLE
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                    lastAction = MotionEvent.ACTION_DOWN
                    saveDisabled = (lastClick != -1L && System.currentTimeMillis() - lastClick < 200)
                    if (saveDisabled) {
                        showKeysList()
                    }

                    lastClick = System.currentTimeMillis()
                }
                MotionEvent.ACTION_MOVE -> {
                    view.y = event.rawY + dY
                    view.x = event.rawX + dX
                    lastAction = MotionEvent.ACTION_MOVE
                    setKeyLocation(Pair(view.x, view.y))

                }

                MotionEvent.ACTION_UP -> {
                }


                else -> return@setOnTouchListener false
            }
            true
        }
    }

    private fun showKeysList() {
        val dialog: KeysDialog = KeysDialog(this)
        dialog.show()
        val window: Window = dialog.window!!
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
    }


    private fun setComponents() {
        val location = getKeyLocation()
        secret.x = location.first
        secret.y = location.second
        if (location != Pair(0f, 0f)) {
            secret.visibility = View.INVISIBLE
        }
    }
}
