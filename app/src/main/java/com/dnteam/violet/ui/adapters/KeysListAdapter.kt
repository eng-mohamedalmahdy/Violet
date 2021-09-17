package com.dnteam.violet.ui.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dnteam.violet.R


class KeysListAdapter(private val keys: List<String>) :
    RecyclerView.Adapter<KeysListAdapter.KeysListViewHolder>() {


    class KeysListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val keyEditText: EditText = itemView.findViewById(R.id.key)
        private val copyToClipboard: ImageButton = itemView.findViewById(R.id.copy_to_clipboard)

        fun bind(key: String) {
            keyEditText.setText(key)
            copyToClipboard.setOnClickListener {
                val clipboard =
                    itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Secret note title", keyEditText.text.toString())
                clipboard.setPrimaryClip(clip)
                Toast.makeText(
                    itemView.context,
                    itemView.context.getString(R.string.copied_to_clipboard),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        KeysListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_key, parent, false)
        )

    override fun getItemCount() = keys.size

    override fun onBindViewHolder(holder: KeysListViewHolder, position: Int) =
        holder.bind(keys[position])

}