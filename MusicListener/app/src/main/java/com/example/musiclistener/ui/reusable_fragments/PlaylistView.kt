package com.example.musiclistener.ui.reusable_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musiclistener.CommonFunctions.setBackgroundAsTheme
import com.example.musiclistener.adapters.OnClicks
import com.example.musiclistener.databinding.FragmentPlaylistViewBinding
import com.example.musiclistener.music.MusicViewModel

class PlaylistView : Fragment() {
    private lateinit var _binding :FragmentPlaylistViewBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState) ; setBackgroundAsTheme() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlaylistViewBinding.inflate(inflater,container,false)
            .apply {
                vm = ViewModelProvider(requireActivity())[MusicViewModel::class.java]
                onClick = OnClicks()
                context = requireContext()
            }
        _binding.lifecycleOwner = viewLifecycleOwner
        return _binding.root
    }
}

