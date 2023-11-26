package com.deathsdoor.chillbackmusicplayer.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.changeStatusBarColor
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.createChildFragment
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.onEndReachedListener
import com.deathsdoor.chillbackmusicplayer.databinding.FragmentExploreContentBinding

class ExploreContent : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor(R.color.blue)
    }

    private lateinit var _binding:FragmentExploreContentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExploreContentBinding.inflate(inflater,container,false)
        return _binding.root
    }
    /*
    * Shows charts of data
    * Recommendation
    *   - Songs
    *   - Tracks
    * Top / Trending
    *   - Songs
    *   - Tracks
    * Genres
    *   ...
    * */
    private val dataToFetch = mapOf(
        "Recommendation/Songs" to { },
        "Recommendation/Albums" to { },

        "Top/Songs" to {},
        "Top/Albums" to {},

        "Trending/Songs" to {},
        "Trending/Albums" to {},

        "Genres/Pop" to {},
        "Genres/Rock" to {},
        "Genres/Hip Hop/Rap" to {},
        "Genres/Electronic/Dance" to {},
        "Genres/R&B/Soul" to {},
        "Genres/Jazz" to {},
        "Genres/Blues" to {},
        "Genres/Country" to {},

        "Genres/Folk" to {},
        "Genres/Classical" to {},
        "Genres/Latin" to {},
        "Genres/Reggae" to {},

        "Genres/Metal" to {},
        "Genres/Punk" to {},
        "Genres/Funk" to {},

        "Genres/Gospel/Christian" to {},
        "Genres/World Music" to {},
        )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO change everytime it does it
        _binding.rest.onEndReachedListener {
            val textView = TextView(requireContext())
            textView.text = "Recommendations"
            textView.textSize = 32f
            textView.typeface = Typeface.DEFAULT_BOLD

            val framelayout = FrameLayout(requireContext())
            framelayout.id = it.childCount + 1

            val fragment = AlbumRecyclerView.create(arrayListOf())
            createChildFragment(fragment,framelayout.id)
        }
    }
}