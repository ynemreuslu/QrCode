package com.ynemreuslu.instantqr.ui.contact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.model.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: CreatorRepository
) : ViewModel() {

    var fullName by mutableStateOf("")
        private set

    var phoneNumber by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    fun onFullNameChange(newFullName: String) {
        fullName = newFullName
    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        phoneNumber = newPhoneNumber
    }


    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun resetContact() {
        fullName = ""
        phoneNumber = ""
        email = ""
    }

    fun isInputEmpty(): Boolean {
        return fullName.isNotBlank() && phoneNumber.isNotBlank()
    }

    fun insertContact() {
        viewModelScope.launch {
            val contact = Contact(
                name = fullName,
                number = phoneNumber,
                email = email,
                timestamp = System.currentTimeMillis()
            )

            repository.insertContact(contact)
            resetContact()
        }

    }
}