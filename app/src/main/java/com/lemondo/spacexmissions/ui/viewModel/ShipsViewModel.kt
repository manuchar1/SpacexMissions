package com.lemondo.spacexmissions.ui.viewModel

import androidx.lifecycle.*
import com.lemondo.spacexmissions.data.models.LaunchesResponse
import com.lemondo.spacexmissions.data.models.ShipsResponse
import com.lemondo.spacexmissions.data.repository.RepositoryImpl
import com.lemondo.spacexmissions.utils.Event
import com.lemondo.spacexmissions.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Manuchar Zakariadze on 2/28/22
 */
class ShipsViewModel : ViewModel() {

    private val _shipsLiveData = MutableLiveData<Event<Resource<List<ShipsResponse>>>>()
    val shipsLiveData: LiveData<Event<Resource<List<ShipsResponse>>>> get() = _shipsLiveData

    private val _launchesLiveData = MutableLiveData<Event<Resource<List<LaunchesResponse>>>>()
    val launchesLiveData: LiveData<Event<Resource<List<LaunchesResponse>>>> get() = _launchesLiveData

    fun getShips() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _shipsLiveData.postValue(Event(RepositoryImpl.getShips()))
            }
        }
    }

    fun getLaunches() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _launchesLiveData.postValue(Event(RepositoryImpl.getLaunches()))
            }
        }
    }

    fun getLaunchesByMission(flightNumber: Int?) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _launchesLiveData.postValue(Event(RepositoryImpl.getLaunchByMission(flightNumber)))
            }
        }

    }

}