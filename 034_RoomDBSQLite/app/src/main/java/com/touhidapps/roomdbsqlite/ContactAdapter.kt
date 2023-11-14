package com.touhidapps.roomdbsqlite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.touhidapps.roomdbsqlite.databinding.RowContactBinding
import com.touhidapps.roomdbsqlite.db.ContactEntity

class ContactAdapter(var items: ArrayList<ContactEntity>): RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    lateinit var onEditClick: ((ContactEntity) -> Unit)
    lateinit var onDeleteClick: ((ContactEntity) -> Unit)

    fun setEditClick(action: (ContactEntity) -> Unit) {
        onEditClick = action
    }
    fun setDeleteClick(action: (ContactEntity) -> Unit) {
        onDeleteClick = action
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RowContactBinding = RowContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        val binding = holder.binding

        binding.tvTitle.text = "${item.firstName} ${item.lastName}"
        binding.tvSubTitle.text = item.phoneNumber

    }

    inner class MyViewHolder(var binding: RowContactBinding): ViewHolder(binding.root) {
        init {
            // Click listeners
            binding.btnEdit.setOnClickListener {
                onEditClick(items[adapterPosition])
            }
            binding.btnDelete.setOnClickListener {
                onDeleteClick(items[adapterPosition])
            }
        }
    }

}