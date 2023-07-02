package com.lucifer.notesapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.lucifer.notesapp.R

class NoteAddActivity : AppCompatActivity() {
    lateinit var title : EditText
    lateinit var note : EditText
    lateinit var btnCancel : Button
    lateinit var btnSave : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add)

        supportActionBar!!.title = "Add Note"

        title = findViewById(R.id.textAddNoteTitle)
        note = findViewById(R.id.textAddNote)
        btnCancel = findViewById(R.id.buttonCancel)
        btnSave = findViewById(R.id.buttonSave)

        btnCancel.setOnClickListener {
            Toast.makeText(this@NoteAddActivity,"Note not Added!!",Toast.LENGTH_LONG).show()
            finish()
        }

        btnSave.setOnClickListener {
            saveNote()
        }

    }
    private fun saveNote(){
        val ntTitle : String = title.text.toString()
        val ntDesc : String = note.text.toString()

        val intent = Intent()
        intent.putExtra("title",ntTitle)
        intent.putExtra("description",ntDesc)
        setResult(RESULT_OK,intent)
        finish()
    }
}