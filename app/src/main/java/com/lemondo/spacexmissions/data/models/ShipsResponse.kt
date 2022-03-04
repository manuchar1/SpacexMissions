package com.lemondo.spacexmissions.data.models


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShipsResponse(
    @Json(name = "home_port")
    val homePort: String?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "missions")
    val missions: List<Mission>?,
    @Json(name = "ship_id")
    val shipId: String?,
    @Json(name = "ship_model")
    val shipModel: String?,
    @Json(name = "ship_name")
    val shipName: String?,
    @Json(name = "ship_type")
    val shipType: String?,
    @Json(name = "year_built")
    val yearBuilt: Int?
) : Parcelable