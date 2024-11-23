package com.ynemreuslu.instantqr.ui.email

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.model.Email
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val repository: CreatorRepository
) : ViewModel() {

    var emailAddress by mutableStateOf("")
        private set

    var emailSubject by mutableStateOf("")
        private set

    var emailBody by mutableStateOf("")
        private set

    fun updateEmailAddress(newEmail: String) {
        emailAddress = newEmail
    }

    fun updateEmailSubject(newSubject: String) {
        emailSubject = newSubject
    }

    fun updateEmailBody(newBody: String) {
        emailBody = newBody
    }

    fun clearTextInput() {
        emailAddress = ""
        emailSubject = ""
        emailBody = ""
    }

    fun isValidEmail(): Boolean = emailAddress.isNotBlank() && emailSubject.isNotBlank()

    fun insertEmail() {
        val email = Email(
            address = emailAddress,
            subject = emailSubject,
            body = emailBody,
            timestamp = System.currentTimeMillis()
        )
        viewModelScope.launch {
            repository.insertEmail(email)
            clearTextInput()
        }
    }

}