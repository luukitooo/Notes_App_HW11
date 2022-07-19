package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.children
import com.example.notesapp.data.id
import com.example.notesapp.data.notesList
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.notesapp.models.Note

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private var withImage = false

    private var notePosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        listeners()

    }

    private fun init() {
        notePosition = intent.getIntExtra("notePosition", -1)
        if (notePosition != -1) {
            val note = notesList[notePosition]
            binding.apply {
                textView.text = "Edit Note"
                titleEditText.setText(note.title)
                bodyEditText.setText(note.body)
                if (note.image != null) {
                    withImageCheckBox.isChecked = true
                    withImage = true
                    switchModes(true)
                    urlEditText.setText(note.image)
                }
            }
        }
    }

    private fun listeners() = with(binding) {
        withImageCheckBox.setOnCheckedChangeListener { _, isChecked ->
            withImage = isChecked
            switchModes(isChecked)
        }
        saveButton.setOnClickListener {
            if (!linesAreEmpty(binding.root)) {
                if (notePosition == -1) {
                    notesList.add(getNote())
                    id++
                } else {
                    notesList[notePosition] = getNote()
                }
                finish()
            }
        }
    }

    private fun switchModes(boolean: Boolean) {
        if (boolean) {
            binding.urlEditText.visibility = View.VISIBLE
        } else {
            binding.urlEditText.visibility = View.GONE
        }
    }

    private fun linesAreEmpty(container: ViewGroup): Boolean {
        container.children.forEach {
            if (it is EditText && it.text.isEmpty() && it.visibility != View.GONE)
                return true
        }
        return false
    }

    private fun getNote(): Note {
        val title = binding.titleEditText.text.toString()
        val body = binding.bodyEditText.text.toString()
        val url = if (withImage) binding.urlEditText.text.toString() else null
        return Note(id, title, body, url)
    }

}