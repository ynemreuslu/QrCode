package com.ynemreuslu.instantqr.ui.wifi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.model.Wifi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WifiViewModel @Inject constructor(
    val repository: CreatorRepository
) : ViewModel() {

    var wifiName by mutableStateOf("")
        private set

    var wifiPassword by mutableStateOf("")
        private set

    var selectedWifiType by mutableStateOf(WifiType.WPA2)
        private set

    fun onWifiName(name: String) {
        wifiName = name
    }

    fun onWifiPassword(password: String) {
        wifiPassword = password
    }

    fun onWifiTypeSelection(type: WifiType) {
        selectedWifiType = type
    }

    fun selectedWifiTypeNone(): Boolean = selectedWifiType == WifiType.NONE

    fun clearPassword() {
        wifiPassword = ""
    }

    fun clearField() {
        wifiName = ""
        wifiPassword = ""
        selectedWifiType = WifiType.WPA2
    }

    fun isNotWifiName() = wifiName.isNotBlank()

    fun insertWifi() {
        viewModelScope.launch {
            val wifi = Wifi(
                wifiName = wifiName,
                wifiPassword = wifiPassword,
                wifiType = selectedWifiType.name,
                timestamp = System.currentTimeMillis()
            )
            repository.insertWifi(wifi)
            clearField()
        }
    }
}
