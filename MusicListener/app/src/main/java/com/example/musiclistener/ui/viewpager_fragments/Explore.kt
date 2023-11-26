package com.example.musiclistener.ui.viewpager_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musiclistener.R
import com.example.musiclistener.databinding.FragmentExploreBinding

class Explore : Fragment() {
    private lateinit var _binding: FragmentExploreBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentExploreBinding.inflate(inflater,container,false)
            .apply {  }
        _binding.lifecycleOwner = viewLifecycleOwner
        return _binding.root
    }
}