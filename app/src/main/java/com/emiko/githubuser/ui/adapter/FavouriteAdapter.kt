package com.emiko.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emiko.githubuser.R
import com.emiko.githubuser.data.models.UsersItem
import kotlinx.android.synthetic.main.item_users.view.*

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.FavouritesViewHolder>()  {

    inner class FavouritesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<UsersItem>() {
        override fun areItemsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        return FavouritesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_users,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(user.avatar_url).into(item_user_image)
            item_user_txt.text = user.login
            setOnClickListener {
                onItemClickListener?.let { it(user) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private var onItemClickListener: ((UsersItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (UsersItem) -> Unit) {
        onItemClickListener = listener
    }

}