package com.example.project.assistant.zone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project.assistant.model.Local
import com.example.project.databinding.ItemzoneBinding

class LocalAdapter(val clickListener: (Local) -> Unit) : ListAdapter<Local, LocalViewHolder>(Diff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemzoneBinding.inflate(layoutInflater, parent, false)
        binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return LocalViewHolder(binding)
    }
    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.textViewZone.text = currentItem.designation
        holder.itemView.setOnClickListener {
            clickListener(currentItem)
        }
    }
}
class LocalViewHolder(val binding: ItemzoneBinding) : RecyclerView.ViewHolder(binding.root)

class Diff : DiffUtil.ItemCallback<Local>() {
    override fun areItemsTheSame(oldItem: Local, newItem: Local) = oldItem.designation == newItem.designation

    override fun areContentsTheSame(oldItem: Local, newItem: Local) = oldItem == newItem
}
