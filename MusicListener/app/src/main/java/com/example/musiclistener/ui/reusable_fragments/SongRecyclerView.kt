package com.example.musiclistener.ui.reusable_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musiclistener.appdatabase.AppDataBase
import com.example.musiclistener.databinding.FragmentSongRecyclerViewBinding
import com.example.musiclistener.music.MusicViewModel


class SongRecyclerView : Fragment() {
    private lateinit var _binding : FragmentSongRecyclerViewBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSongRecyclerViewBinding.inflate(inflater,container,false)
            .apply {
                vm = ViewModelProvider(requireActivity())[MusicViewModel::class.java]
                playlistAppDB = AppDataBase.getDataBase(requireContext()).playlistDao()
                layoutDirection = LinearLayoutManager.VERTICAL
                fragmentManager = requireFragmentManager()
            }
        _binding.lifecycleOwner = viewLifecycleOwner
        return _binding.root
    }
}