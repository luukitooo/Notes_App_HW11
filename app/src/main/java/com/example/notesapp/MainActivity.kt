package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.adapters.NoteAdapter
import com.example.notesapp.data.notesList
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.models.Note

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val noteAdapter by lazy { NoteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        onClickListeners()

    }

    private fun init() {
        binding.notesRecyclerView.apply {
            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        binding.root.setOnRefreshListener {
            noteAdapter.submitList(notesList.toList())
            binding.root.isRefreshing = false
        }
    }

    private fun onClickListeners() {
        binding.addButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNoteActivity::class.java))
        }
        noteAdapter.apply {
            onDeleteClickListener = {
                notesList.remove(it).also { this.submitList(notesList.toList()) }
            }
            onEditClickListener = {
                Intent(this@MainActivity, AddNoteActivity::class.java).apply {
                    this.putExtra("notePosition", notesList.indexOf(it))
                    startActivity(this)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        noteAdapter.submitList(notesList.toList())
    }

}