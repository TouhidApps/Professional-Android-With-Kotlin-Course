package com.touhidapps.firebasetutorial.model

import com.google.gson.annotations.SerializedName


data class PushNotificationObject (

  @SerializedName("pushImage" ) var pushImage : String? = null,
  @SerializedName("pushTitle" ) var pushTitle : String? = null,
  @SerializedName("pushBody"  ) var pushBody  : String? = null,
  @SerializedName("pushType"  ) var pushType  : String? = null

)