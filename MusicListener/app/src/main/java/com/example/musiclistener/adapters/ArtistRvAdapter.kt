package com.example.musiclistener.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musiclistener.Artist
import com.example.musiclistener.ImageFunctions.loadImg
import com.example.musiclistener.R
import kotlinx.android.synthetic.main.item_rv_artist.view.*

class ArtistRvAdapter(private val artistsList:List<Artist>): RecyclerView.Adapter<ArtistRvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_artist,parent,false),mListener)
    override fun getItemCount(): Int = artistsList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bind(artistsList[position]) }
    class ViewHolder(itemView: View,listener: OnItemClickListener):RecyclerView.ViewHolder(itemView) {
        fun bind(artist: Artist) =
            with(itemView) { item_artist_img.loadImg(artist.profilePic) }
        //clicks
        init { itemView.setOnClickListener{ listener.OnItemClick(adapterPosition) } }
    }
    //clicks
    lateinit var mListener: OnItemClickListener
    interface OnItemClickListener{ fun OnItemClick(position: Int) }
    fun setOnItemClickListener(listener: OnItemClickListener){ mListener = listener }
}