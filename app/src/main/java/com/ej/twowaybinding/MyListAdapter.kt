package com.ej.twowaybinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.twowaybinding.databinding.UserItemBinding
import com.ej.twowaybinding.db.UserEntity

class MyListAdapter : ListAdapter<UserEntity, MyListAdapter.MyListAdapterViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListAdapterViewHolder {
        val viewHolder = MyListAdapterViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyListAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyListAdapterViewHolder(private val binding : UserItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(userEntity: UserEntity){
            binding.name.text = userEntity.name
            binding.age.text = userEntity.age.toString()
        }
    }

    companion object{
        private val DiffCallback = object  : DiffUtil.ItemCallback<UserEntity>(){
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem

            }
        }
    }
}