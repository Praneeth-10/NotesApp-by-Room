package com.lucifer.notesapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.lucifer.notesapp.R

class UpdateActivity : AppCompatActivity() {

    lateinit var Uptitle : EditText
    lateinit var Upnote : EditText
    lateinit var btnUpCancel : Button
    lateinit var btnUpSave : Button
    var curId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        supportActionBar?.title = "Update Note"

        Uptitle = findViewById(R.id.textUpdateNoteTitle)
        Upnote = findViewById(R.id.textUpdateNote)
        btnUpCancel = findViewById(R.id.buttonUpdateCancel)
        btnUpSave = findViewById(R.id.buttonUpdateSave)

        getAndSetData()

        btnUpCancel.setOnClickListener {
            finish()
        }

        btnUpSave.setOnClickListener {
            updateNote()
        }
    }

    fun updateNote(){
        val intent = Intent()
        intent.putExtra("newTitle",Uptitle.text.toString())
        intent.putExtra("newNote",Upnote.text.toString())
        if (curId != -1)
        {
            intent.putExtra("noteId",curId)
            setResult(RESULT_OK,intent)
            finish()
        }
    }

    fun getAndSetData(){
        val curTitle = intent.getStringExtra("curTitle")
        val curNote = intent.getStringExtra("curNote")
        curId = intent.getIntExtra("curId",-1)

        Uptitle.setText(curTitle)
        Upnote.setText(curNote)
    }
}