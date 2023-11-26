package com.example.musiclistener.ui.viewpager_fragments

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.algolia.search.saas.Query
import com.example.musiclistener.Constants.ALOGIA_SONG_INDEX
import com.example.musiclistener.R
import com.example.musiclistener.Song
import com.example.musiclistener.databinding.FragmentDetailedSearchBinding
import com.example.musiclistener.music.MusicViewModel
import com.example.musiclistener.music.UserDataFetching.matchSongIDWithData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_detailed_search.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailedSearch : Fragment() {
    //TODO use fully binding
    private lateinit var _binding: FragmentDetailedSearchBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailedSearchBinding.inflate(inflater,container,false).apply { vm = ViewModelProvider(requireActivity())[MusicViewModel::class.java] }
        _binding.vm!!.showAddBtn = true
        _binding.lifecycleOwner = viewLifecycleOwner
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //default values
        search("")
        detailed_search_search_box.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s1: String?): Boolean { search(s1); return false }
            override fun onQueryTextChange(s1: String?): Boolean { search(s1); return false }
        })
    }
    override fun onDestroyView() { super.onDestroyView(); _binding.vm!!.showAddBtn = false }
    @OptIn(DelicateCoroutinesApi::class)
    fun search(s1: String?) {
        val query = Query(s1).setHitsPerPage(15)
        ALOGIA_SONG_INDEX.searchAsync(query){ jsonObject, exception ->
            //show error
            if(exception?.message != null) Toast.makeText(requireContext(),"Error ${exception.message}",Toast.LENGTH_SHORT).show()
            //search results == nichts
            if(jsonObject == null) detailed_search_error_msg.visibility = View.VISIBLE
            else{
                val list = arrayListOf<Song>()
                detailed_search_error_msg.visibility = View.GONE
                val hits = jsonObject.getJSONArray("hits").toString()
                d("Search","Json Hits Object -> $hits")
                Gson().fromJson<List<Song>>(hits,object : TypeToken<List<Song>>(){}.type).forEach {
                    GlobalScope.launch {
                        val songData = matchSongIDWithData(it.mediaID)
                        if(songData != null) list.add(songData)
                    }
                }
                _binding.vm!!.filteredSongs.postValue(list)
            }
        }

    }
}