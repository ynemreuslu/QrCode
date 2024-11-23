package com.ynemreuslu.instantqr.ui.phone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.model.Phone

@HiltViewModel
class PhoneViewModel @Inject constructor(
    private val repository: CreatorRepository
) : ViewModel() {

    var phone by mutableStateOf("")
        private set

    fun onPhoneChange(newPhone: String) {
        phone = newPhone
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.isNotBlank()
    }

    private fun clearPhoneInput() {
        phone = ""
    }

    fun insertPhone() {
        if (validatePhoneNumber(phone)) {
            viewModelScope.launch {
                val phone = Phone(number = phone, timestamp = System.currentTimeMillis())
                repository.insertPhone(phone)
                clearPhoneInput()
            }
        }
    }
}
