package com.example.musiclistener.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.musiclistener.ImageFunctions.loadImg
import com.example.musiclistener.R
import java.util.*

class ViewPagerIMGAdapter(val context: Context, private val imageList: List<String>) : PagerAdapter() {
    override fun getCount(): Int = imageList.size
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object` as RelativeLayout
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as RelativeLayout)
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = mLayoutInflater.inflate(R.layout.item_view_pager_img, container, false)
        val imageView: ImageView = itemView.findViewById(R.id.item_view_pager_img_img) as ImageView
        imageView.loadImg(imageList[position])
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }
}
