package com.example.calculator30.fragments.pages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculator30.NewNote
import com.example.calculator30.NotesRecyclerViewAdapter
import com.example.calculator30.R
import com.example.calculator30.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Notes : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_notes, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //new note
        btnNewNote.setOnClickListener{ startActivity(Intent(requireContext(), NewNote::class.java))}
        rvNotes.layoutManager = LinearLayoutManager(requireContext())
       // val adapter = HistoryViewModel().allNotes
        //rvNotes.adapter = NotesRecyclerViewAdapter(adapter)
    }
}