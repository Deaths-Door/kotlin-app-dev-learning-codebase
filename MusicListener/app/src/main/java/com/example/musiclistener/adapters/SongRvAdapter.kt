package com.example.musiclistener.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.musiclistener.ImageFunctions.loadImg
import com.example.musiclistener.R
import com.example.musiclistener.Song
import com.example.musiclistener.music.MusicViewModel
import kotlinx.android.synthetic.main.item_rv_song.view.*

class SongRvAdapter(private val playlist:List<Song>, private val vm: MusicViewModel, private val addBtnShow: Boolean): RecyclerView.Adapter<SongRvAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_song,parent,false),mListener,addBtnShow)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(playlist[position],vm)
    override fun getItemCount(): Int = playlist.size
    //clicks
    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener{
        fun OnItemClick(position: Int)
        fun OnLikeBtnClick(position: Int, liked: Boolean)
        fun OnItemLongClicked(position: Int)
        fun OnAddBtnClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){ mListener = listener }
    class ViewHolder(itemView: View, listener: OnItemClickListener, private val addBtnShow: Boolean):RecyclerView.ViewHolder(itemView) {
        fun bind(item: Song, vm: MusicViewModel) =
            with(itemView){
                item_song_img.loadImg(item.imgURL)
                item_song_name.text = item.title
                item_song_artist.text = item.artist
                //if liked song
                if(item.mediaID in vm.userPlaylist.value!![0].songsIDs) item_song_likebtn.isLiked = true
                if(!addBtnShow) item_song_add.visibility = View.GONE

            }
        //clicks
        init {
            itemView.setOnClickListener {
                listener.OnItemClick(adapterPosition)
                itemView.item_song_likebtn.setOnClickListener { listener.OnLikeBtnClick(adapterPosition, itemView.item_song_likebtn.isLiked) }
                itemView.item_song_add.setOnClickListener { listener.OnAddBtnClick(adapterPosition) }
            }
            itemView.setOnLongClickListener {
                listener.OnItemLongClicked(adapterPosition)
                return@setOnLongClickListener true
            }
        }

    }
}