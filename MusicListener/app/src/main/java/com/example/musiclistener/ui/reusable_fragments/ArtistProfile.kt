package com.example.musiclistener.ui.reusable_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musiclistener.CommonFunctions.setBackgroundAsTheme
import com.example.musiclistener.adapters.OnClicks
import com.example.musiclistener.databinding.FragmentArtistProfileBinding
import com.example.musiclistener.music.MusicViewModel

class ArtistProfile : Fragment() {
    private lateinit var _binding : FragmentArtistProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState); setBackgroundAsTheme() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentArtistProfileBinding.inflate(inflater,container,false)
            .apply {
                vm = ViewModelProvider(requireActivity())[MusicViewModel::class.java]
                onClick = OnClicks()
            }
        _binding.lifecycleOwner =viewLifecycleOwner
        return _binding.root
    }
}