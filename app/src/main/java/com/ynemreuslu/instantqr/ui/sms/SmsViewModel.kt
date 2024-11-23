package com.ynemreuslu.instantqr.ui.sms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.model.Sms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(
    private val repository: CreatorRepository
) : ViewModel() {

    var phone by mutableStateOf("")
        private set

    var message by mutableStateOf("")
        private set


    fun isFormValid() = phone.isNotBlank() && message.isNotBlank()


    fun onTextChange(newText: String) {
        phone = newText
    }

    fun onMessageChange(newMessage: String) {
        message = newMessage
    }

    private fun clearFields() {
        phone = ""
        message = ""
    }

    fun insertSms() {
        if (isFormValid()) {
            viewModelScope.launch {
                val sms = Sms(number = phone, message = message, timestamp = System.currentTimeMillis())
                repository.insertSms(sms)
                clearFields()
            }
        }
    }
}