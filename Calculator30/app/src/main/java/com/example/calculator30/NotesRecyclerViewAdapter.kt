package com.example.calculator30

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*

class NotesRecyclerViewAdapter(private val list:List<NoteInfo>): RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder>() {
    override fun getItemCount(): Int = list.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder((parent.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.item_note,parent,false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }
    class NoteViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(noteInfo: NoteInfo) {
            itemView.itemNoteTitle.text = noteInfo.title
            itemView.itemNoteTopic.text = noteInfo.topics
            itemView.itemNoteIMG.setImageURI(Uri.parse(noteInfo.imgURL))
        }
    }
}