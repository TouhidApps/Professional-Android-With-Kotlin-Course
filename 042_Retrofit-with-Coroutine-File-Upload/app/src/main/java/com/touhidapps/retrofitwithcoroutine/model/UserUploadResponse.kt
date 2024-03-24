package com.touhidapps.retrofitwithcoroutine.model

import com.google.gson.annotations.SerializedName

data class UserUploadResponse (

  @SerializedName("id"       ) var id       : String? = null,
  @SerializedName("name"     ) var name     : String? = null,
  @SerializedName("title"    ) var title    : String? = null,
  @SerializedName("imageUrl" ) var imageUrl : String? = null

)