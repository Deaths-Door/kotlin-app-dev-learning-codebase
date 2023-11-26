package com.deathsdoor.chillbackmusicplayer.data.adapters.init

import android.graphics.Canvas
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview.AlbumRvAdapter
import com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview.AlbumRvAdapter.OnItemClickListener
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Album
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.ui.bottomsheets.ExtraAlbumOptions
import com.deathsdoor.chillbackmusicplayer.ui.fragment.AlbumDetailedInfo
import com.deathsdoor.chillbackmusicplayer.ui.fragment.AlbumRecyclerView
import kotlinx.coroutines.launch


fun AlbumRecyclerView.setAlbumAdapter(){
    val orientation = albumRvViewModel.orientation.value ?: Constants.ORIENTATION.VERTICAL
    _binding.root.layoutManager =
        when (orientation) {
            Constants.ORIENTATION.VERTICAL -> LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
            Constants.ORIENTATION.GRID -> GridLayoutManager(this.context,2)
        }

    val adapter = albumRvViewModel.displayedAlbums.value?.let { AlbumRvAdapter(requireContext(),it,orientation,albumRvViewModel.disableCheckAlbumDownload) }
    _binding.root.adapter = adapter

    //TODO hide playbutton when disableForwardNavigationImplementation is used
    if(!this.albumRvViewModel.disableForwardNavigation) {
        defaultImplementation(adapter)
        swipes(adapter)
    } else disableForwardNavigationImplementation(adapter)
}

//TODO implement swipes
fun AlbumRecyclerView.swipes(adapter: AlbumRvAdapter?){
    val touchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int
            = makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT)
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            adapter?.notifyItemMoved(target.bindingAdapterPosition,viewHolder.bindingAdapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if(direction == ItemTouchHelper.RIGHT){

            }
        }
        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
           /* RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(recyclerView.context, R.color.red))
                .addSwipeLeftActionIcon(R.drawable.ic_delete)
                .create()
                .decorate()*/
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    })
    touchHelper.attachToRecyclerView(this._binding.root)
}

fun AlbumRecyclerView.defaultImplementation(adapter: AlbumRvAdapter?) {
    adapter?.setOnItemClickListener(object :OnItemClickListener{
        override fun onItemClick(album: Album) = findNavController().navigate(R.id.action_global_albumDetailedInfo, AlbumDetailedInfo.createBundle(album))
        override fun onLongClick(album: Album) {
            mainViewModel.album.postValue(album)
            mainViewModel.albumMediaId = album.id
            ExtraAlbumOptions().show(parentFragmentManager,ExtraAlbumOptions.TAG)
        }
        override fun onPlayBtnClick(album: Album){
            mainViewModel.musicPlayer.refreshQueue(album.songs)
        }
    })
}

fun AlbumRecyclerView.disableForwardNavigationImplementation(adapter: AlbumRvAdapter?){
    adapter?.setOnItemClickListener(object:OnItemClickListener{
        override fun onItemClick(album: Album){
            val value = mainViewModel.song.value!!
            mainViewModel.song.postValue(null)
            lifecycleScope.launch{
                CoroutineHelper.onBackgroundThread {
                    if(!album.songsIDs.contains(value.mediaID)) userAlbumsDao.update(album.addSong(value))
                }
            }
        }
        override fun onLongClick(album: Album) = Unit
        override fun onPlayBtnClick(album: Album) = Unit
    })
}