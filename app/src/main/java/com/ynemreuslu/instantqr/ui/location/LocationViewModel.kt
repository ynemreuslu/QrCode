package com.ynemreuslu.instantqr.ui.location

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.model.Location

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: CreatorRepository
) : ViewModel() {

    var latitude by mutableStateOf("")
        private set

    var longitude by mutableStateOf("")
        private set

    fun updateLatitude(latitude: String) {
        this.latitude = latitude
    }

    fun updateLongitude(longitude: String) {
        this.longitude = longitude
    }

    fun clearTextInput() {
        latitude = ""
        longitude = ""
    }

    fun isValid(): Boolean = latitude.isNotBlank() && longitude.isNotBlank()

    fun createLocation() {
        viewModelScope.launch {
            val location = Location(
                latitude = latitude.toDouble(),
                longitude = longitude.toDouble(),
                timestamp = System.currentTimeMillis()
            )
            repository.insertLocation(location)
            clearTextInput()
        }
    }
}