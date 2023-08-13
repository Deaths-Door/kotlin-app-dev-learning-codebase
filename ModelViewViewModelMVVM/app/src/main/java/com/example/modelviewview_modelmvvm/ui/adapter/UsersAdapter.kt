package com.example.modelviewview_modelmvvm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modelviewview_modelmvvm.R
import com.example.modelviewview_modelmvvm.data.models.UserItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(val data :List<UserItem>):RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder
        = UsersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false))

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(data[position])
    }
    override fun getItemCount(): Int = data.size
    class UsersViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(item: UserItem) = with(itemView){
            tvUsername.text = item.login
            Picasso.get().load(item.avatarUrl).into(imgViewUser)
        }
    }
}