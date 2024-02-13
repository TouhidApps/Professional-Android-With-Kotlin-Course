package com.touhidapps.retrofitwithcoroutine.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address (

  @SerializedName("addressId" ) var addressId : Int?    = null,
  @SerializedName("city"      ) var city      : String? = null,
  @SerializedName("country"   ) var country   : String? = null

): Parcelable