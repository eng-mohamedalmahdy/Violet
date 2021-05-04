package com.dnteam.violet.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.dnteam.violet.R
import com.dnteam.violet.util.setPassword

class CreatePasswordDialog(context: Context) : Dialog(context) {

    private lateinit var password: EditText
    private lateinit var save: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_create_password)

        password = findViewById(R.id.password)
        save = findViewById(R.id.confirm_button)
        save.setOnClickListener {
            context.setPassword(password.text.toString())
            hide()
        }
    }
}