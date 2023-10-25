package com.touhidapps.listview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.touhidapps.listview.databinding.BottomLoadingBinding
import com.touhidapps.listview.databinding.RowCountryBinding

class CountryAdapter(var items: ArrayList<CountryModel>): RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LOADING: Int = 0
        private const val VIEW_TYPE_NORMAL: Int = 1
        private var isLoaderVisible = false
    }


    lateinit var onItemClick: ((CountryModel) -> Unit)

    fun setItemClick(action: (CountryModel) -> Unit) {
        onItemClick = action
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun getItemViewType(position: Int): Int {

        if (isLoaderVisible) {
            return if(position == (items.size-1)) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            return VIEW_TYPE_NORMAL
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = if (viewType == VIEW_TYPE_LOADING) {
            BottomLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            RowCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (holder.binding is RowCountryBinding) {
            val item = items[position]
            val binding = holder.binding as RowCountryBinding

            binding.tvCountryName.text = item.countryName
            binding.tvCountryCode.text = item.countryCode
        } else {
            // do if necessary
        }

    }

    fun updateItems(newItems: ArrayList<CountryModel>) {

        val diffCallBack = CountryDiffUtil(this.items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        this.items.clear()
        this.items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)

    }

    fun addLoading() {
        isLoaderVisible = true
        this.items.add(CountryModel())
        notifyItemInserted(this.items.size-1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val pos = this.items.size - 1
        this.items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    inner class MyViewHolder(var binding: ViewBinding) : ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(items[adapterPosition])
            }
        }

    }

}