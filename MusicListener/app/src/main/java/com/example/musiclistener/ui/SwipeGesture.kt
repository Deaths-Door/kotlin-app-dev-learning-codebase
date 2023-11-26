package com.example.musiclistener.ui

import android.graphics.*
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.musiclistener.R.drawable
import com.example.musiclistener.appdatabase.PlaylistDao
import com.example.musiclistener.music.MusicViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object SwipeGestures{
    @OptIn(DelicateCoroutinesApi::class)
    fun RecyclerView.swipeRvPlaylist(vm: MusicViewModel, playlistAppDB: PlaylistDao){
        val simpleItemTouchCallBack = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //Liked / Local music playlists
           //     if(viewHolder.adapterPosition == 0 || viewHolder.adapterPosition == 1) return

                when(direction){
                    ItemTouchHelper.LEFT -> {
                        GlobalScope.launch { playlistAppDB.deletePlaylist(vm.userPlaylist.value!![viewHolder.adapterPosition].id)}
                    }
                    ItemTouchHelper.RIGHT ->{
                        val data = vm.userPlaylist.value!![viewHolder.adapterPosition]
                        data.pinned = !data.pinned
                        GlobalScope.launch { playlistAppDB.updatePlaylist(data) }
                    }
                }
            }
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val icon: Bitmap
                val paint = Paint()
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView: View = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 5
                    viewHolder.itemView.translationX = dX / 5
                    if(dX > 0){
                        paint.color = Color.GREEN
                        icon = ContextCompat.getDrawable(context, drawable.ic_baseline_push_pin_24)?.toBitmap()!!
                        val background = RectF(itemView.left.toFloat() + dX / 5, itemView.top.toFloat(), itemView.left.toFloat(), itemView.bottom.toFloat())
                        val iconDest = RectF((itemView.left + dX / 7), itemView.top.toFloat() + width - icon.height.toFloat(), itemView.right.toFloat() + dX / 20, itemView.bottom.toFloat()+ width)
                        c.drawRect(background, paint)
                        c.drawBitmap(icon, null, iconDest, paint)
                    }
                    else{
                        paint.color = Color.RED
                        icon = ContextCompat.getDrawable(context, drawable.ic_baseline_delete_24)?.toBitmap()!!
                        val background = RectF(itemView.right.toFloat() + dX / 5, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        val iconDest = RectF((itemView.right + dX / 7), itemView.top.toFloat() + width, itemView.right.toFloat() + dX / 20, itemView.bottom.toFloat() - width)
                        c.drawRect(background, paint)
                        c.drawBitmap(icon, null, iconDest, paint)
                    }
                }
                else super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(this)
    }
}