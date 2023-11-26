package com.deathsdoor.chillbackmusicplayer.data.adapters.init

import android.graphics.Canvas
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview.SongRvAdapter
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.daos.LikedSongsDao
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.daos.UserAlbumsDao
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.ui.fragment.SongRecyclerView
import kotlinx.coroutines.launch

//TOOO update this
fun SongRecyclerView.adapter() {
    val orientation = songRvViewModel.orientation.value ?: Constants.ORIENTATION.VERTICAL
    _binding.root.layoutManager =
        when (orientation) {
            Constants.ORIENTATION.VERTICAL -> LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            Constants.ORIENTATION.GRID -> GridLayoutManager(requireContext(),2)
        }

    val adapter = songRvViewModel.displayedSongs.value?.let { SongRvAdapter(it, orientation,requireContext()) }
    _binding.root.adapter = adapter

    if(!songRvViewModel.disableForwardNavigation) {
        adapter?.defaultImplementation(this, mainViewModel,likedSongDao)
        swipes(adapter)
    }
    else adapter?.disableForwardNavigationImplementation(this,mainViewModel,userAlbumDao)
}
private fun SongRvAdapter.defaultImplementation(fragmentSongRecyclerView: SongRecyclerView, mainViewModel: MainViewModel, dao: LikedSongsDao) {
    clickListener = object: SongRvAdapter.OnItemClickListener {
        override fun onItemClick(data: Song) {
            mainViewModel.musicPlayer.addSongToQueue(data)
            mainViewModel.musicPlayer.exoplayer.seekToNext()
        }
        override fun onLongClick(data: Song) {
            mainViewModel.song.postValue(data)
            fragmentSongRecyclerView._binding.root.findNavController().navigate(R.id.actionNachExtraSongOptions)
       //     ExtraSongOptions().show(FragmentManager.findFragment<SongRecyclerView>(recyclerView).parentFragmentManager, ExtraSongOptions.TAG)
        }
        override fun onLike(data: Song) {
            fragmentSongRecyclerView.lifecycleScope.launch{
                CoroutineHelper.onBackgroundThread {
                    dao.insert(data.toMediaIDSong())
                }
            }
        }
        override fun onDislike(data: Song) {
            fragmentSongRecyclerView.lifecycleScope.launch{
                CoroutineHelper.onBackgroundThread {
                    dao.delete(data.mediaID)
                }
            }
        }
    }
}
private fun SongRvAdapter.disableForwardNavigationImplementation(fragmentSongRecyclerView: SongRecyclerView ,mainViewModel: MainViewModel, userAlbumDao: UserAlbumsDao) {
    clickListener = object: SongRvAdapter.OnItemClickListener {
        override fun onItemClick(data: Song){
            fragmentSongRecyclerView.lifecycleScope.launch {
                CoroutineHelper.onBackgroundThread {
                    userAlbumDao.update(userAlbumDao.get(mainViewModel.albumMediaId).addSong(data))
                }
            }
        }
        override fun onLongClick(data: Song) = TODO("Not yet implemented")
    }
}

//Swipe right remove from playlist and left is either pin or unpin
//TODO remove from playlist
//TODO add flag to know if its part of playlist and which one
private fun SongRecyclerView.swipes(adapter: SongRvAdapter?){
    val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            adapter?.notifyItemMoved(target.bindingAdapterPosition,viewHolder.bindingAdapterPosition)
            return true
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if(direction == ItemTouchHelper.RIGHT){

            }
            else {

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