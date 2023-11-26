package com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Album
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.extensions.Image.loadImage
import com.deathsdoor.chillbackmusicplayer.databinding.ItemAlbumGridBinding
import com.deathsdoor.chillbackmusicplayer.databinding.ItemAlbumVerticalBinding
import com.deathsdoor.chillbackmusicplayer.data.Constants.SETTING.*
import com.deathsdoor.chillbackmusicplayer.data.extensions.File.externalMusicFileUri
import com.deathsdoor.chillbackmusicplayer.data.extensions.File.internalFileExists
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.changeVisibility
import com.deathsdoor.ui_core.public.PreferenceExtensions.sharedPreference
import com.deathsdoor.ui_core.public.PreferenceExtensions.getPreferenceValue

class AlbumRvAdapter(private val context:Context, private val data:List<Album>, private val orientation: Constants.ORIENTATION = Constants.ORIENTATION.VERTICAL, private val disableCheckAlbumDownload:Boolean):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(orientation == Constants.ORIENTATION.VERTICAL) Vertical(ItemAlbumVerticalBinding.inflate(LayoutInflater.from(parent.context),parent,false),clickListener)
        else Grid(ItemAlbumGridBinding.inflate(LayoutInflater.from(parent.context),parent,false),clickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO use holder is Vertical instead
        if(orientation == Constants.ORIENTATION.VERTICAL) (holder as Vertical).bind(data[position])
        else (holder as Grid).bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    lateinit var clickListener: OnItemClickListener
    fun setOnItemClickListener(listener: OnItemClickListener){ clickListener = listener }
    interface OnItemClickListener{
        fun onItemClick(album: Album)
        fun onLongClick(album: Album)
        fun onPlayBtnClick(album: Album)
    }

    private val MIN_DOWNLOADED : Int = context.sharedPreference("SETTING")?.getPreferenceValue(MIN_DOWNLOADED_FILES_CLASSIFY_ALBUM_AS_DOWNLOADED.name,MIN_DOWNLOADED_FILES_CLASSIFY_ALBUM_AS_DOWNLOADED.defaultValue as Int)!!

    private fun Album.countedAsDownloaded(): Boolean {
        var downloaded = 0
        this.songs.forEach {
            if(it.mediaID.internalFileExists(context,Constants.FILE_EXTENSIONS.MP3) || it.mediaID.externalMusicFileUri(context) != null) downloaded++;
        }

        if(downloaded == 0) return false
        return (downloaded / this.songsIDs.size) * 100 > MIN_DOWNLOADED
    }

    inner class Vertical(private val _binding:ItemAlbumVerticalBinding, private val clickListener:OnItemClickListener) : RecyclerView.ViewHolder(_binding.root){
        fun bind(album: Album) {

            album.also {
                _binding.title.text = it.title
                _binding.artist.text = it.albumArtist

                _binding.image.loadImage(it.imgURL)

                if(!it.pinned) _binding.pinned.changeVisibility()

                if(disableCheckAlbumDownload) return@also
                if(!it.countedAsDownloaded()) _binding.downloaded.changeVisibility()
            }
        }
        init {
            _binding.root.setOnClickListener{
                clickListener.onItemClick(data[bindingAdapterPosition])
            }
            _binding.root.setOnLongClickListener {
                clickListener.onLongClick(data[bindingAdapterPosition])
                return@setOnLongClickListener false
            }
        }
    }
    inner class Grid(private val _binding:ItemAlbumGridBinding, private val clickListener:OnItemClickListener) : RecyclerView.ViewHolder(_binding.root){
        fun bind(album: Album) {
            album.also {
                _binding.title.text = it.title
                _binding.artist.text = it.albumArtist

                _binding.image.loadImage(it.imgURL)

                if(!it.pinned) _binding.pinned.changeVisibility()

                if(!it.countedAsDownloaded()) _binding.downloaded.changeVisibility()
            }
        }
        init {
            _binding.root.setOnClickListener{
                clickListener.onItemClick(data[bindingAdapterPosition])
            }
            _binding.root.setOnLongClickListener {
                clickListener.onLongClick(data[bindingAdapterPosition])
                return@setOnLongClickListener false
            }
            _binding.play.setOnClickListener {
                clickListener.onPlayBtnClick(data[bindingAdapterPosition])
            }
        }
    }
}