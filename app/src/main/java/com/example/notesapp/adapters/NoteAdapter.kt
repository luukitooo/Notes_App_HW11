package com.example.notesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notesapp.R
import com.example.notesapp.databinding.NoteItemBinding
import com.example.notesapp.databinding.NoteItemWithImageBinding
import com.example.notesapp.models.Note

class NoteAdapter : ListAdapter<Note, RecyclerView.ViewHolder>(NoteItemCallback()) {

    var onDeleteClickListener: ((Note) -> Unit)? = null
    var onEditClickListener: ((Note) -> Unit)? = null

    companion object {
        const val STANDARD_NOTE = 1
        const val NOTE_WITH_IMAGE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            STANDARD_NOTE -> NoteViewHolder(
                NoteItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> ImageNoteViewHolder(
                NoteItemWithImageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoteViewHolder -> holder.bind()
            is ImageNoteViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).image) {
            null -> STANDARD_NOTE
            else -> NOTE_WITH_IMAGE
        }
    }

    private inner class NoteViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val note = getItem(adapterPosition)
            binding.apply {
                titleTextView.text = note.title
                bodyTextView.text = note.body
                deleteButton.setOnClickListener { onDeleteClickListener?.invoke(note) }
                editButton.setOnClickListener { onEditClickListener?.invoke(note) }
            }
        }
    }

    private inner class ImageNoteViewHolder(private val binding: NoteItemWithImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val note = getItem(adapterPosition)
            binding.apply {
                titleTextView.text = note.title
                bodyTextView.text = note.body
                Glide.with(this.root)
                    .load(note.image)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView)
                deleteButton.setOnClickListener { onDeleteClickListener?.invoke(note) }
                editButton.setOnClickListener { onEditClickListener?.invoke(note) }
            }
        }
    }

    private class NoteItemCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

}