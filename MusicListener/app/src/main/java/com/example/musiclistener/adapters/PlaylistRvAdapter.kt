package com.example.musiclistener.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musiclistener.ImageFunctions.loadImg
import com.example.musiclistener.Playlists
import com.example.musiclistener.R
import kotlinx.android.synthetic.main.item_rv_playlist.view.*

class PlaylistRvAdapter(private val playlists:List<Playlists>): RecyclerView.Adapter<PlaylistRvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_playlist,parent,false),mListener)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bind(playlists[position]) }
    override fun getItemCount(): Int = playlists.size
    //clicks
    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener{
        fun OnItemClick(position: Int)
        fun OnLongClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){ mListener = listener }
    class ViewHolder(itemView: View,listener: OnItemClickListener):RecyclerView.ViewHolder(itemView) {
        fun bind(item: Playlists) {
            with(itemView){
                item_playlist_name.text = item.name
                item_playlist_main_artists.text = item.mainArtists
                if(item.pinned) item_playlist_pinned.loadImg(R.drawable.ic_baseline_push_pin_24)
            }
        }
        //clicks
        init {
            itemView.setOnClickListener{ listener.OnItemClick(adapterPosition) }
            itemView.setOnLongClickListener { listener.OnLongClick(adapterPosition); return@setOnLongClickListener false }
        }
    }
}