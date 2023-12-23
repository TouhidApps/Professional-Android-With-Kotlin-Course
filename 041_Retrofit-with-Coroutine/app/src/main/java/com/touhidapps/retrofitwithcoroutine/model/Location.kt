package com.touhidapps.retrofitwithcoroutine.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location (

  @SerializedName("locationId"   ) var locationId   : Int?    = null,
  @SerializedName("locationName" ) var locationName : String? = null

): Parcelable