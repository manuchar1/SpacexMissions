package com.lemondo.spacexmissions.data.network


import com.lemondo.spacexmissions.data.models.LaunchesResponse
import com.lemondo.spacexmissions.data.models.ShipsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Manuchar Zakariadze on 2/28/22
 */
interface SpacexService {
    @GET("ships")
    suspend fun getShips(): Response<List<ShipsResponse>>

    @GET("launches")
    suspend fun getLaunches(): Response<List<LaunchesResponse>>

    @GET("launches")
    suspend fun getLaunchesByMission(
        @Query("flight_number") flight_number: Int? = null
    ): Response<List<LaunchesResponse>>
}

