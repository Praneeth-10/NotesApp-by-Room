package com.lucifer.notesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucifer.notesapp.NoteApplication
import com.lucifer.notesapp.R
import com.lucifer.notesapp.adapters.NoteAdapter
import com.lucifer.notesapp.viewModel.NoteViewModel
import com.lucifer.notesapp.viewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val noteAdapter = NoteAdapter()

        recyclerView.adapter = noteAdapter

        val noteViewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)

        noteViewModel = ViewModelProvider(this@MainActivity,noteViewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.myAllNotes.observe(this, Observer { notes ->

            //update the UI
            noteAdapter.setNote(notes)
        })
    }
}