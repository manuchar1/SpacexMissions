package com.lemondo.spacexmissions.data.models


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Links(
    @Json(name = "article_link")
    val articleLink: @RawValue Any?,
    @Json(name = "video_link")
    val videoLink: @RawValue Any?,
    @Json(name = "wikipedia")
    val wikipedia: @RawValue Any?,
    @Json(name = "youtube_id")
    val youtubeId: @RawValue Any?
) : Parcelable