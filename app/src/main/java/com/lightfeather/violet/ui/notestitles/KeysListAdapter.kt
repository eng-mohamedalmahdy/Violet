package com.lightfeather.violet.ui.notestitles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightfeather.violet.R
import com.lightfeather.violet.databinding.ListItemKeyBinding
import com.lightfeather.violet.domain.*


class KeysListAdapter(private val keys: List<String>) :
    RecyclerView.Adapter<KeysListAdapter.KeysListViewHolder>() {


    class KeysListViewHolder(private val binding: ListItemKeyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(keyString: String) {
            with(binding) {
                key.setText(keyString)
                copyToClipboard.setOnClickListener {
                    itemView.context.copyToClipBoard(key.stringContent())
                    itemView.context.toast(R.string.copied)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        KeysListViewHolder(
            ListItemKeyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount() = keys.size

    override fun onBindViewHolder(holder: KeysListViewHolder, pos: Int) = holder.bind(keys[pos])

}