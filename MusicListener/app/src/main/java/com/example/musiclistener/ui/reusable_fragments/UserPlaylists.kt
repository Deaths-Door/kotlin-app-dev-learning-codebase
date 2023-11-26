package com.example.musiclistener.ui.reusable_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musiclistener.CommonFunctions.setBackgroundAsTheme
import com.example.musiclistener.Constants
import com.example.musiclistener.Constants.USE_USER_PLAYLIST
import com.example.musiclistener.appdatabase.AppDataBase
import com.example.musiclistener.databinding.FragmentUserPlaylistsBinding
import com.example.musiclistener.music.MusicViewModel

class UserPlaylists : Fragment() {
    private lateinit var _binding : FragmentUserPlaylistsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState); setBackgroundAsTheme() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUserPlaylistsBinding.inflate(inflater,container,false)
            .apply {
                vm = ViewModelProvider(requireActivity())[MusicViewModel::class.java]
                appDB = AppDataBase.getDataBase(requireContext())
                layoutDirection = LinearLayoutManager.VERTICAL
            }
        _binding.lifecycleOwner = viewLifecycleOwner
        return _binding.root
    }
}