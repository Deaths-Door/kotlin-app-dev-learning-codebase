package com.example.musiclistener

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore.Images.Media.getBitmap
import android.widget.ImageView
import com.bumptech.glide.Glide


object ImageFunctions {
    @Deprecated("User databinding idiot")
    fun ImageView.loadImg(imgURL: String)
        =  Glide.with(this).load(imgURL)
            .placeholder(R.drawable.ic_launcher_background) //Default img
            .skipMemoryCache(true)//Caching img if phone offline
            .into(this)
    @Deprecated("User databinding idiot")
    fun ImageView.loadImg(imgURL: Uri)
        =  Glide.with(this).load(imgURL)
            .placeholder(R.drawable.ic_launcher_background) //Default img
            .skipMemoryCache(true)//Caching img if phone offline
            .into(this)
    @Deprecated("User databinding idiot")
    fun ImageView.loadImg(img: Int)
        = Glide.with(this).load(img)
            .placeholder(R.drawable.ic_launcher_background) //Default img
            .skipMemoryCache(true)//Caching img if phone offline
            .into(this)
    @Deprecated("User databinding idiot")
    fun ImageView.loadImg( bitmap: Bitmap)
        = Glide.with(this).load(bitmap)
            .placeholder(R.drawable.ic_launcher_background) //Default img
            .skipMemoryCache(true)//Caching img if phone offline
            .into(this)

    fun bitmapFromUrl(context: Context, url:String?): Bitmap = getBitmap(context.contentResolver,Uri.parse(url))
    fun bitmapFromUri(context: Context, uri: Uri?): Bitmap = getBitmap(context.contentResolver,uri)

    //TODO check if works gives some bogus error
    fun Context.generatePlaylistCoverImg(playlist: List<Playlists>): List<Playlists> {
       /* playlist.forEach loop@ {
            if(it.img == Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888)) return@loop
            if(it.songs.size in 1..3) it.img = bitmapFromUri(context,it.songs[0].imgURL)
            else it.img = imageCombineVertically(
                imageCombineHorizontally(bitmapFromUri(context,it.songs[0].imgURL),bitmapFromUri(context,it.songs[1].imgURL)),
                imageCombineHorizontally(bitmapFromUri(context,it.songs[3].imgURL),bitmapFromUri(context,it.songs[4].imgURL))
            )
        }*/
        return playlist
    }
    private fun imageCombineVertically(bm1: Bitmap, bm2: Bitmap): Bitmap {
        val finalBitmap = Bitmap.createBitmap(bm1.width,bm1.height + bm2.height,Bitmap.Config.ARGB_8888)
        val finalCanvas = Canvas(finalBitmap)
        finalCanvas.drawBitmap(bm2, Matrix(),null)
        finalCanvas.drawBitmap(bm1, 0F, bm2.height.toFloat(), null)
        return finalBitmap
    }
    private fun imageCombineHorizontally(bm1: Bitmap, bm2: Bitmap): Bitmap {
        val finalBitmap = Bitmap.createBitmap(bm1.width + bm2.width,bm1.height,Bitmap.Config.ARGB_8888)
        val finalCanvas = Canvas(finalBitmap)
        finalCanvas.drawBitmap(bm1,0f,0f,null)
        finalCanvas.drawBitmap(bm1, bm1.width.toFloat(),0f, null)
        return finalBitmap
    }
}