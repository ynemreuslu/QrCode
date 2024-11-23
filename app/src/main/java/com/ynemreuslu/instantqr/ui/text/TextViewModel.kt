package com.ynemreuslu.instantqr.ui.text

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.model.Text
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextViewModel @Inject constructor(
    private val repository: CreatorRepository
) : ViewModel() {

    var text by mutableStateOf("")
        private set

    fun onTextChange(newText: String) {
        text = newText
    }

    private fun resetTextInput() {
        text = ""
    }

    fun isTextValid(): Boolean {
        return text.isNotBlank()
    }

    fun insertText() {
        if (isTextValid()) {
            viewModelScope.launch {
                val text = Text(text = text, timestamp = System.currentTimeMillis())
                repository.insertText(text)
                resetTextInput()
            }
        }
    }
}