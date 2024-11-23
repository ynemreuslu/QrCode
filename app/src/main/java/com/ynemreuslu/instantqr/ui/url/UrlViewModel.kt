package com.ynemreuslu.instantqr.ui.url

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.model.Url
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UrlViewModel @Inject constructor(
    private val repository: CreatorRepository
) : ViewModel() {

    var url by mutableStateOf("https://")
        private set

    fun onUrlChange(newUrl: String) {
        url = newUrl
    }

    private fun clearTextInput() {
        url = "https://"
    }

    fun isUrlValid(): Boolean {
        return url.length > "https://".length
    }

    fun insertUrl() {
        if (isUrlValid()) {
            viewModelScope.launch {
                val urlEntity = Url(url = url, timestamp = System.currentTimeMillis())
                repository.insertUrl(urlEntity)
                clearTextInput()
            }
        }
    }
}