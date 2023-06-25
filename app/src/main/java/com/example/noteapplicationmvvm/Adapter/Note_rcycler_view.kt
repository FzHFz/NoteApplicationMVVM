package com.example.noteapplicationmvvm.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplicationmvvm.Models.Note
import com.example.noteapplicationmvvm.R
import com.example.noteapplicationmvvm.databinding.NoteItemBinding
import com.example.noteapplicationmvvm.utilitis.note_section

class Note_rcycler_view(private val context: Context, val listener: NotesItemClickListener) : RecyclerView.Adapter<Note_rcycler_view.NoteViewHolder>() {
    private val noteList: ArrayList<Note> = ArrayList()
    private val fullList: ArrayList<Note> = ArrayList()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = noteList[position]
        holder.binding.apply {
            titleBox.text = currentNote.title
            noteContent.text = currentNote.note
            titleBox.isSelected = true
            notesLayout.setOnClickListener {
                listener.onItemClick(currentNote)
            }
            notesLayout.setOnLongClickListener {
                listener.onLongItemClicked(currentNote, notesLayout)
                true
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return noteList.size
    }


    inner class NoteViewHolder(val binding:NoteItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface NotesItemClickListener{
        fun onItemClick(note: Note)
        fun onLongItemClicked(note: Note,cardView: CardView)
    }
    fun updatedList(newList: List<Note>){
        fullList.clear()
        fullList.addAll(newList)
        noteList.clear()
        noteList.addAll(fullList)
        notifyDataSetChanged()
    }
}

