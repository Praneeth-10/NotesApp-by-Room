package com.lucifer.notesapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lucifer.notesapp.R
import com.lucifer.notesapp.model.Note
import com.lucifer.notesapp.view.MainActivity
import com.lucifer.notesapp.view.UpdateActivity

class NoteAdapter(private val activity : MainActivity) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    var notes : List<Note> = ArrayList()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tViewTitle : TextView = itemView.findViewById(R.id.textViewTitle)
        val tViewDescription : TextView = itemView.findViewById(R.id.textViewDescription)
        val cardView : CardView = itemView.findViewById(R.id.card_notes)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item,parent,false)

        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var currentNote : Note = notes[position]

        holder.tViewTitle.text = currentNote.title
        holder.tViewDescription.text = currentNote.description

        holder.cardView.setOnClickListener {
            val intent = Intent(activity,UpdateActivity::class.java)
            intent.putExtra("curTitle",currentNote.title)
            intent.putExtra("curNote",currentNote.description)
            intent.putExtra("curId",currentNote.id)

            activity.updateActivityResultLauncher.launch(intent)
        }
    }

    fun setNote(myNotes : List<Note>){
        this.notes = myNotes
        notifyDataSetChanged()
    }

    fun getNote(position: Int):Note{
        return notes[position]
    }

}