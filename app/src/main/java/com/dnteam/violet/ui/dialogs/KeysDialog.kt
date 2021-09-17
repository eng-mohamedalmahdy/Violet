package com.dnteam.violet.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnteam.violet.R
import com.dnteam.violet.ui.adapters.KeysListAdapter
import com.dnteam.violet.data.database.NotesDatabase
import com.dnteam.violet.domain.getPassword
import com.dnteam.violet.models.Note
import kotlinx.coroutines.*

class KeysDialog(context: Context) : Dialog(context) {

    private lateinit var password: EditText
    private lateinit var keysList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_keys_list)
        window!!.setGravity(Gravity.TOP)

        password = findViewById(R.id.password)
        keysList = findViewById(R.id.keys_list)
        keysList.layoutManager =
            LinearLayoutManager(ownerActivity, LinearLayoutManager.VERTICAL, false)

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val notes: List<String> =
                    NotesDatabase.getDatabase(context)?.notesDao()?.getAllKeys() ?: emptyList()
                withContext(Dispatchers.Main) { keysList.adapter = KeysListAdapter(notes) }
            }
        }

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == context.getPassword() && context.getPassword().isNotEmpty())
                    keysList.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == context.getPassword() && context.getPassword().isNotEmpty())
                    keysList.visibility = View.VISIBLE
            }

        })
    }

}