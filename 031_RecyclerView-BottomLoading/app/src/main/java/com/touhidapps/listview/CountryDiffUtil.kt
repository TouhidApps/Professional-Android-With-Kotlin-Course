package com.touhidapps.listview

import androidx.recyclerview.widget.DiffUtil

class CountryDiffUtil(var itemsOld: ArrayList<CountryModel>, var itemsNew: ArrayList<CountryModel>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return itemsOld.size
    }

    override fun getNewListSize(): Int {
        return itemsNew.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return itemsOld[oldItemPosition].id == itemsNew[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCountry: CountryModel = itemsOld[oldItemPosition]
        val newCountry: CountryModel = itemsNew[newItemPosition]
        return oldCountry.countryName == newCountry.countryName && oldCountry.countryCode == newCountry.countryCode
    }

}