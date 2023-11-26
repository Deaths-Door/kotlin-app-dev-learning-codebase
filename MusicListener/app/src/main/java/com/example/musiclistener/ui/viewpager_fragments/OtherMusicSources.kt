package com.example.musiclistener.ui.viewpager_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musiclistener.WEBPAGE
import com.example.musiclistener.adapters.OnClicks
import com.example.musiclistener.databinding.FragmentOtherMusicSourcesBinding
import com.example.musiclistener.music.MusicViewModel

class OtherMusicSources : Fragment() {
    private lateinit var _binding: FragmentOtherMusicSourcesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        _binding = FragmentOtherMusicSourcesBinding.inflate(inflater,container,false)
            .apply {
                vm = ViewModelProvider(requireActivity())[MusicViewModel::class.java]
                onClick = OnClicks()
            }
        _binding.vm!!.webPageURl.postValue(WEBPAGE.SPOTIFY.url)
        _binding.lifecycleOwner = viewLifecycleOwner
        return _binding.root
    }
}