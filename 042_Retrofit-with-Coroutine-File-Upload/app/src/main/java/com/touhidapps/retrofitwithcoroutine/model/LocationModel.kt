package com.touhidapps.retrofitwithcoroutine.model

import com.google.gson.annotations.SerializedName


data class LocationModel (

  @SerializedName("id"           ) var id           : String? = null,
  @SerializedName("locationName" ) var locationName : String? = null

)