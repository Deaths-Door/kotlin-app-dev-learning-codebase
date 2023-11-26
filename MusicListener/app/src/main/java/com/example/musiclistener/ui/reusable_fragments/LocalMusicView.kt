package com.example.musiclistener.ui.reusable_fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musiclistener.CommonFunctions.setBackgroundAsTheme
import com.example.musiclistener.SETTINGS
import com.example.musiclistener.adapters.ViewPagerCategoryAdapter
import com.example.musiclistener.databinding.FragmentLocalMusicViewBinding
import java.util.*

class LocalMusicView : Fragment() {
    private lateinit var _binding:FragmentLocalMusicViewBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLocalMusicViewBinding.inflate(inflater,container,false)
            .apply {  }
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackgroundAsTheme()
        val adapter = ViewPagerCategoryAdapter(requireFragmentManager())
            .apply {
                addFragment(SongRecyclerView(),"All")
                addFragment(UserPlaylists(),"Albums")
            }
        _binding.fragLocalMusicViewViewpager.adapter = adapter
        _binding.fragLocalMusicViewTab.setupWithViewPager(_binding.fragLocalMusicViewViewpager)
    }
}