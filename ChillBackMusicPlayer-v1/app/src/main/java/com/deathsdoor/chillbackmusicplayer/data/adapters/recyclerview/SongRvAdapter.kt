package com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.extensions.Image.loadImage
import com.deathsdoor.chillbackmusicplayer.databinding.ItemSongGridBinding
import com.deathsdoor.chillbackmusicplayer.databinding.ItemSongVerticalBinding
import com.like.LikeButton
import android.content.Context
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.AppDataBase
import com.like.OnLikeListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SongRvAdapter(private val data:List<Song>, private val orientation: Constants.ORIENTATION, val context:Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val likedDao by lazy { AppDataBase.dataBase(context).likedSongDao() }

    lateinit var clickListener: OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(data: Song)
        fun onLongClick( data: Song)
        fun onLike(data: Song) = Unit
        fun onDislike(data: Song) = Unit
        fun onArtistNameClicked(data: Song) = Unit
        fun onSongNameClicked(data: Song) = Unit
    }
    inner class VerticalViewHolder(val _binding: ItemSongVerticalBinding, private val clickListener: OnItemClickListener) : RecyclerView.ViewHolder(_binding.root) {
        @OptIn(DelicateCoroutinesApi::class)
        fun bind(song: Song) {
            song.also {
                _binding.title.text = it.title
                _binding.artist.text = it.artist
                _binding.image.loadImage(it.imageSource)
                GlobalScope.launch { _binding.btnLike.isLiked = likedDao.isLiked(it.mediaID) }
            }
        }

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(data[bindingAdapterPosition])
                _binding.artist.setOnClickListener {
                    clickListener.onArtistNameClicked(data[bindingAdapterPosition])
                }
                _binding.title.setOnClickListener {
                    clickListener.onSongNameClicked(data[bindingAdapterPosition])
                }
                _binding.btnLike.setOnLikeListener(object: OnLikeListener {
                    override fun liked(likeButton: LikeButton?) = clickListener.onLike(data[bindingAdapterPosition])
                    override fun unLiked(likeButton: LikeButton?) = clickListener.onDislike(data[bindingAdapterPosition])
                })
            }
            itemView.setOnLongClickListener {
                clickListener.onLongClick(data[bindingAdapterPosition])
                return@setOnLongClickListener false
            }
        }
    }
    inner class GridViewHolder(private val _binding:ItemSongGridBinding, private val clickListener: OnItemClickListener) : RecyclerView.ViewHolder(_binding.root) {
        @OptIn(DelicateCoroutinesApi::class)
        fun bind(song: Song) {
            song.also {
                _binding.title.text = it.title
                _binding.artist.text = it.artist
                _binding.image.loadImage(it.imageSource)
                GlobalScope.launch { _binding.btnLike.isLiked = likedDao.isLiked(it.mediaID) }
            }
        }
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(data[bindingAdapterPosition])
                _binding.artist.setOnClickListener {
                    clickListener.onArtistNameClicked(data[bindingAdapterPosition])
                }
                _binding.title.setOnClickListener {
                    clickListener.onSongNameClicked(data[bindingAdapterPosition])
                }
                _binding.btnLike.setOnLikeListener(object: OnLikeListener {
                    override fun liked(likeButton: LikeButton?) = clickListener.onLike(data[bindingAdapterPosition])
                    override fun unLiked(likeButton: LikeButton?) = clickListener.onDislike(data[bindingAdapterPosition])
                })
            }
            itemView.setOnLongClickListener {
                clickListener.onLongClick(data[bindingAdapterPosition])
                return@setOnLongClickListener false
            }
        }
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is VerticalViewHolder) holder.bind(data[position])
        else if(holder is GridViewHolder) holder.bind(data[position])
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(orientation){
            Constants.ORIENTATION.VERTICAL -> VerticalViewHolder(ItemSongVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false),clickListener)
            Constants.ORIENTATION.GRID -> GridViewHolder(ItemSongGridBinding.inflate(LayoutInflater.from(parent.context), parent, false), clickListener)
        }
    }
}