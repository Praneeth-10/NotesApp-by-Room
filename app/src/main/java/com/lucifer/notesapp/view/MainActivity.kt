package com.lucifer.notesapp.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucifer.notesapp.NoteApplication
import com.lucifer.notesapp.R
import com.lucifer.notesapp.adapters.NoteAdapter
import com.lucifer.notesapp.model.Note
import com.lucifer.notesapp.viewModel.NoteViewModel
import com.lucifer.notesapp.viewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(this)
        recyclerView.adapter = noteAdapter

        //register activity for result
        registerActivityResultLauncher()

        val noteViewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)

        noteViewModel = ViewModelProvider(this@MainActivity,noteViewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.myAllNotes.observe(this, Observer { notes ->

            //update the UI
            noteAdapter.setNote(notes)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Toast.makeText(this@MainActivity,
                    "Note '${(noteAdapter.getNote(viewHolder.adapterPosition)).title}' has been deleted",
                Toast.LENGTH_SHORT).show()
                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.item_add_note ->{
                val intent = Intent(this@MainActivity,NoteAddActivity::class.java)
                addActivityResultLauncher.launch(intent)
            }

            R.id.item_delete_all_note ->{
                showDialogMessage()
            }

        }
        return true
    }

    fun registerActivityResultLauncher(){

        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { resultAddNote ->
            val resultCode = resultAddNote.resultCode
            val data = resultAddNote.data

            if(resultCode == RESULT_OK && data != null){
                val noteTitle : String = data.getStringExtra("title").toString()
                val noteDesc : String = data.getStringExtra("description").toString()

                val note = Note(noteTitle,noteDesc)
                noteViewModel.insert(note)
            }
        })

        updateActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { updateAddNote ->
            val resultCode = updateAddNote.resultCode
            val data = updateAddNote.data

            if(resultCode == RESULT_OK && data != null){
                val upTitle = data.getStringExtra("newTitle").toString()
                val upNote = data.getStringExtra("newNote").toString()
                val upId = data.getIntExtra("noteId",-1)

                val newNote = Note(upTitle,upNote)
                newNote.id = upId

                noteViewModel.update(newNote)
            }
        })

    }

    fun showDialogMessage(){
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setMessage("Are you sure you want to delete all the nodes? \n If not please swipe Left or Right the specific")
            .setTitle("Deleting All Nodes")
            .setCancelable(false)
            .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, which ->
                dialogInterface.cancel()
            })
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                noteViewModel.deleteAllNotes()
            })
            .setIcon(R.drawable.baseline_warning_amber_24)
            .create().show()

    }
}