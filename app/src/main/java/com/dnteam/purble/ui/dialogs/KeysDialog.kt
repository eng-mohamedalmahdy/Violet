package com.dnteam.purble.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnteam.purble.R
import com.dnteam.purble.adapters.KeysListAdapter
import com.dnteam.purble.database.NotesDatabase
import com.dnteam.purble.util.getPassword
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
        keysList.layoutManager = LinearLayoutManager(ownerActivity, LinearLayoutManager.VERTICAL, false)

        CoroutineScope(Dispatchers.Main).launch {
            val res = withContext(Dispatchers.IO) {
                val notes = NotesDatabase.getDatabase(context)!!.notesDao()!!.getAllKeys()
                withContext(Dispatchers.Main) {
                    keysList.adapter = KeysListAdapter(notes)
                    (keysList.adapter as KeysListAdapter).notifyDataSetChanged()
                }
            }

        }

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == context.getPassword() && context.getPassword().isNotEmpty()) keysList.visibility = View.VISIBLE

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == context.getPassword() && context.getPassword().isNotEmpty()) keysList.visibility = View.VISIBLE
            }

        })
    }

}