package com.lemondo.spacexmissions.data.repository

import com.lemondo.spacexmissions.base.safeCall
import com.lemondo.spacexmissions.data.models.LaunchesResponse
import com.lemondo.spacexmissions.data.models.ShipsResponse
import com.lemondo.spacexmissions.data.network.NetworkClient
import com.lemondo.spacexmissions.utils.Resource

/**
 * Created by Manuchar Zakariadze on 2/28/22
 */
object RepositoryImpl {

    suspend fun getShips(): Resource<List<ShipsResponse>> {
        return safeCall {

            val response = NetworkClient.spacexService.getShips()
            if (response.isSuccessful) {
                val body = response.body()!!
                Resource.Success(body)
            } else {
                val error = response.errorBody()
                Resource.Error(error.toString())

            }

        }
    }

    suspend fun getLaunches(): Resource<List<LaunchesResponse>> {
        return safeCall {

            val response = NetworkClient.spacexService.getLaunches()
            if (response.isSuccessful) {
                val body = response.body()!!
                Resource.Success(body)
            } else {
                val error = response.errorBody()
                Resource.Error(error.toString())

            }

        }
    }

    suspend fun getLaunchByMission(flightNumber: Int?): Resource<List<LaunchesResponse>> {

        return safeCall {
            val response = NetworkClient.spacexService.getLaunchesByMission(flightNumber)
            if (response.isSuccessful) {
                val body = response.body()!!
                Resource.Success(body)
            } else {
                val error = response.errorBody()
                Resource.Error(error.toString())

            }

        }

    }
}