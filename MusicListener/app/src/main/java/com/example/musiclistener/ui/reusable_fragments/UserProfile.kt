package com.example.musiclistener.ui.reusable_fragments
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musiclistener.CommonFunctions.setBackgroundAsTheme
import com.example.musiclistener.SETTINGS
import com.example.musiclistener.adapters.OnClickSettings
import com.example.musiclistener.databinding.FragmentUserProfileBinding
import com.example.musiclistener.music.MusicViewModel

class UserProfile : Fragment() {
    private lateinit var _binding: FragmentUserProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState); setBackgroundAsTheme() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUserProfileBinding.inflate(inflater,container,false)
            .apply {
                vm = ViewModelProvider(requireActivity())[MusicViewModel::class.java]
                onClickSettings = OnClickSettings()
                activity = requireActivity()
                context = requireContext()
                preference = requireContext().getSharedPreferences(SETTINGS.preferenceName(), Context.MODE_PRIVATE)
            }
        return _binding.root
    }}