package com.touhidapps.retrofitwithcoroutine.model

import com.google.gson.annotations.SerializedName


data class PhotoUploadResponse (

  @SerializedName("result"     ) var result     : String? = null,
  @SerializedName("name"       ) var name       : String? = null,
  @SerializedName("photoTitle" ) var photoTitle : String? = null,
  @SerializedName("photoUrl"   ) var photoUrl   : String? = null

)