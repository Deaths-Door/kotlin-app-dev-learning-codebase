package com.deathsdoor.chillbackmusicplayer.data.extensions

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object UI {
    //total itemcount - itemposition is when fun is tiggered
    inline fun RecyclerView.onEndReachedListener(itemPosition: Int, crossinline action:(RecyclerView) -> Unit){
        this.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                (recyclerView.layoutManager as LinearLayoutManager).also {
                    if(it.findLastCompletelyVisibleItemPosition() != it.itemCount - itemPosition) return

                    action(recyclerView)
                }
            }
        })
    }
    inline fun NestedScrollView.onEndReachedListener(crossinline action: (NestedScrollView) -> Unit){
        this.setOnScrollChangeListener { view, _, scrollY, _, _ ->
            if (scrollY + this.height >= this.getChildAt(0).height) action(view as NestedScrollView)
        }
    }

    fun <T: TextView> T.text() = this.text.toString()

    fun <T: View> T.changeVisibility() {
        if(this.isVisible) this.visibility = View.GONE
        else this.visibility = View.VISIBLE
    }

    fun <T : Fragment> Fragment.createChildFragment(fragment:T, containerID:Int) {
        this.childFragmentManager.beginTransaction().replace(containerID,fragment).commit()
    }
}