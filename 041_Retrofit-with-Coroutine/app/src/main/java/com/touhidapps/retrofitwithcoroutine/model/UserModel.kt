package com.touhidapps.retrofitwithcoroutine.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserModel (

  @SerializedName("userId"      ) var userId      : Int?                = null,
  @SerializedName("name"        ) var name        : String?             = null,
  @SerializedName("phoneNumber" ) var phoneNumber : String?             = null,
  @SerializedName("amount"      ) var amount      : Double?             = null,
  @SerializedName("address"     ) var address     : Address?            = Address(),
  @SerializedName("location"    ) var location    : ArrayList<Location> = arrayListOf()

): Parcelable