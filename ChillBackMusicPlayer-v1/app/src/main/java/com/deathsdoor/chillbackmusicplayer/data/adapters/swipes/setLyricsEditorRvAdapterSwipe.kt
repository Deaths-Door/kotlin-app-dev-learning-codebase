package com.deathsdoor.chillbackmusicplayer.data.adapters.swipes

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview.LyricEditorRvAdapter

//TODO add swipes etc
fun RecyclerView.swipes(adapter: LyricEditorRvAdapter){
    val touchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, ): Int =  ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder, ): Boolean {
            adapter.notifyItemMoved(target.bindingAdapterPosition,viewHolder.bindingAdapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            TODO("Not yet implemented")
        }
    })
    touchHelper.attachToRecyclerView(this)
}