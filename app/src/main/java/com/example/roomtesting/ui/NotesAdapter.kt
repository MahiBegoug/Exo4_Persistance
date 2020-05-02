package com.example.roomtesting.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtesting.R
import com.example.roomtesting.db.Note
import kotlinx.android.synthetic.main.fragment_add_note.view.*
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(private val notes:List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder
    {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_layout,parent,false)
        )
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int)
    {
        holder.view.text_view_title.text = notes[position].title
        holder.view.text_view_body.text = notes[position].body + "\n" + notes[position]._date
        holder.view.relative.setBackgroundColor(notes[position].codecolor)

        holder.view.setOnClickListener {

            val action = HomeFragmentDirections.actionAddNote()
            action.note = notes[position]
            Navigation.findNavController(it).navigate(action)

        }
    }

    class NoteViewHolder(val view:View):RecyclerView.ViewHolder(view)
    {

    }
}