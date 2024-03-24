package com.touhidapps.retrofitwithcoroutine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.touhidapps.retrofitwithcoroutine.databinding.RowUserBinding
import com.touhidapps.retrofitwithcoroutine.databinding.RowUserUploadBinding
import com.touhidapps.retrofitwithcoroutine.model.UserModel
import com.touhidapps.retrofitwithcoroutine.model.UserUploadResponse
import com.touhidapps.retrofitwithcoroutine.networkService.AllApi

class UserUploadAdapter(var items: ArrayList<UserUploadResponse>): RecyclerView.Adapter<UserUploadAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RowUserUploadBinding = RowUserUploadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = items[position]
        val binding = holder.binding

        binding.tvName.text = "${item.name}"
        binding.tvTitle.text = "${item.title}"
        binding.ivUser.load("${AllApi.BASE_URL_IMAGE}${item.imageUrl}")

    }

    inner class MyViewHolder(var binding: RowUserUploadBinding): ViewHolder(binding.root) {
        init {


        }
    }

}