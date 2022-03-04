package com.lemondo.spacexmissions.data.models


import com.squareup.moshi.Json

data class LaunchesResponse(
    @Json(name = "details")
    val details: String?,
    @Json(name = "flight_number")
    val flightNumber: Int?,
    @Json(name = "launch_year")
    val launchYear: String?,
    @Json(name = "links")
    val links: Links?,
    @Json(name = "mission_id")
    val missionId: List<Any>?,
    @Json(name = "mission_name")
    val missionName: String?,
    @Json(name = "ships")
    val ships: List<Any>?
)