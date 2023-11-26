package com.example.musiclistener.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerCategoryAdapter(fm:FragmentManager):FragmentStatePagerAdapter(fm) {
    private val data:ArrayList<Fragment> = arrayListOf()
    private val titles:ArrayList<String> = arrayListOf()
    fun addFragment(fragment: Fragment, s:String) { data.add(fragment);titles.add(s) }
    override fun getCount(): Int = data.size
    override fun getPageTitle(position: Int): CharSequence = titles[position]
    override fun getItem(position: Int): Fragment = data[position]
}
