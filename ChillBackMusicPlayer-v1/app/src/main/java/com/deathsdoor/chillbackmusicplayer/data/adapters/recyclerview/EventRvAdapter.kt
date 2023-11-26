package com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.UserEventInfo
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.Lyrics
import com.deathsdoor.chillbackmusicplayer.data.extensions.Image.loadImage
import com.deathsdoor.chillbackmusicplayer.databinding.ItemEventsBinding
import com.deathsdoor.chillbackmusicplayer.databinding.ItemLyricEditorBinding

class EventRvAdapter(val data:List<UserEventInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var clickListener:OnItemClickListener
    interface OnItemClickListener{

    }
    inner class Default(private val _binding: ItemEventsBinding, private val clickListener: OnItemClickListener): RecyclerView.ViewHolder(_binding.root){
        fun bind(userEventInfo: UserEventInfo) {
            userEventInfo.data?.let {
                _binding.image.loadImage(it.image)
                _binding.title.text = it.name
                val t = it.venues?.get(0)
                _binding.location.text = "${t?.address},${t?.country?.countryCode}"
                _binding.date.text = it.dates.start.localTime + it.dates.start.localDate
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
        = Default(ItemEventsBinding.inflate(LayoutInflater.from(parent.context),parent,false),clickListener)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = (holder as Default).bind(data[position])
    override fun getItemCount(): Int = data.size
}