package com.lemondo.spacexmissions.data.models


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mission(
    @Json(name = "flight")
    val flight: Int?,
    @Json(name = "name")
    val name: String?
) : Parcelable