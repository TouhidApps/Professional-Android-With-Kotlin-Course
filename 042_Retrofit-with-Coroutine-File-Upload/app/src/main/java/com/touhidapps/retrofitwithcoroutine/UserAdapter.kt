package com.touhidapps.retrofitwithcoroutine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.touhidapps.retrofitwithcoroutine.databinding.RowUserBinding
import com.touhidapps.retrofitwithcoroutine.model.UserModel

class UserAdapter(var items: ArrayList<UserModel>): RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private lateinit var onItemClick:  ((UserModel) -> Unit)
    private lateinit var onEditClick:  ((UserModel) -> Unit)
    private lateinit var onDeleteClick:  ((UserModel) -> Unit)

    fun setItemClick(action: (UserModel) -> Unit) {
        onItemClick = action
    }

    fun setEditClick(action: (UserModel) -> Unit) {
        onEditClick = action
    }

    fun setDeleteClick(action: (UserModel) -> Unit) {
        onDeleteClick = action
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RowUserBinding = RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = items[position]
        val binding = holder.binding

        binding.tvName.text = "${item.name}"
        binding.tvPhone.text = "${item.phoneNumber}"

    }

    inner class MyViewHolder(var binding: RowUserBinding): ViewHolder(binding.root) {
        init {

            binding.root.setOnClickListener {
                onItemClick(items[adapterPosition])
            }
            binding.btnEdit.setOnClickListener {
                onEditClick(items[adapterPosition])
            }
            binding.btnDelete.setOnClickListener {
                onDeleteClick(items[adapterPosition])
            }


        }
    }

}