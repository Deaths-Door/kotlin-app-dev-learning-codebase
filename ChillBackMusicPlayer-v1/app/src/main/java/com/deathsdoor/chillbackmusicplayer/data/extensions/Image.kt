package com.deathsdoor.chillbackmusicplayer.data.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.deathsdoor.chillbackmusicplayer.R
import java.io.File
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.request.RequestOptions
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request.Image
import com.google.android.material.button.MaterialButton
import java.io.ByteArrayOutputStream

object Image {
    fun ImageView.loadImage(imgURL: String)
        =  Glide.with(context).load(imgURL)
            .placeholder(R.drawable.ic_launcher_background) //Default img
            .skipMemoryCache(true)//Caching img if phone offline
            .into(this)
    fun ImageView.loadImage(uri: Uri)
        =  Glide.with(context).load(uri)
            .placeholder(R.drawable.ic_launcher_background) //Default img
            .skipMemoryCache(true)//Caching img if phone offline
            .into(this)
    fun Context.loadImage(@DrawableRes drawable:Int): Drawable? {
        return ContextCompat.getDrawable(this,drawable)
    }

    fun List<Image>.highestResolutionImage(): Image {
        return this.sortedWith(compareByDescending<Image> { it.width * it.height }.thenByDescending { it.width }).first()
    }
}