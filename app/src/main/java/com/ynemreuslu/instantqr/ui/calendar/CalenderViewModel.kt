package com.ynemreuslu.instantqr.ui.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.model.Calender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalenderViewModel @Inject constructor(
    private val repository: CreatorRepository
) : ViewModel() {

    private val currentTime = Calendar.getInstance()

    var calenderTitle by mutableStateOf("")
        private set
    var location by mutableStateOf("")
        private set
    var explanation by mutableStateOf("")
        private set
    var selectedStartDate by mutableLongStateOf(0)
        private set
    var selectedFinishDate by mutableLongStateOf(0)
    var selectedStartHour by mutableIntStateOf(currentTime.get(Calendar.HOUR_OF_DAY))
        private set
    var selectedStartMinute by mutableIntStateOf(currentTime.get(Calendar.MINUTE))
        private set
    var selectedFinishHour by mutableIntStateOf(currentTime.get(Calendar.HOUR_OF_DAY))
        private set
    var selectedFinishMinute by mutableIntStateOf(currentTime.get(Calendar.MINUTE))
        private set

    private val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    fun isCreateButtonEnabled(): Boolean {
        return calenderTitle.isNotBlank()
    }


    fun onSelectedStartDate(millis: Long) {
        selectedStartDate = millis
    }

    fun onSelectedFinishDate(millis: Long) {
        selectedFinishDate = millis
    }

    fun convertMillisToDate(millis: Long): String {
        return dateFormatter.format(Date(millis))
    }

    fun getCurrentFormattedDate(): String {
        return dateFormatter.format(Date())
    }

    fun onCalendarTitle(title: String) {
        calenderTitle = title
    }

    fun onLocation(location: String) {
        this.location = location
    }

    fun onExplanation(explanation: String) {
        this.explanation = explanation
    }

    fun updateStartTime(hour: Int, minute: Int) {
        selectedStartHour = hour
        selectedStartMinute = minute
    }

    fun updateFinishTime(hour: Int, minute: Int) {
        selectedFinishHour = hour
        selectedFinishMinute = minute
    }

    fun getFormattedTimeStart(): String {
        return formatTime(selectedStartHour, selectedStartMinute)
    }

    fun getFormattedTimeFinish(): String {
        return formatTime(selectedFinishHour, selectedFinishMinute)
    }

    private fun formatTime(hour: Int, minute: Int): String {
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
    }

    fun getStartTimer(): Long {
        return getTimeInMillis(selectedStartHour, selectedStartMinute)
    }

    fun getFinishTimer(): Long {
        return getTimeInMillis(selectedFinishHour, selectedFinishMinute)
    }

    private fun getTimeInMillis(hour: Int, minute: Int): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }.timeInMillis
    }

    fun insertCalender() {
        val calender = Calender(
            calenderTitle = calenderTitle,
            startDate = selectedStartDate,
            finishDate = selectedFinishDate,
            startTime = getStartTimer(),
            finishTime = getFinishTimer(),
            location = location,
            explanation = explanation,
            timestamp = System.currentTimeMillis(),
        )

        viewModelScope.launch {
            repository.insertCalender(calender)
            resetFields()
        }

        resetFields()
    }

    private fun resetFields() {
        calenderTitle = ""
        location = ""
        explanation = ""
        selectedStartDate = 0
        selectedFinishDate = 0
        selectedStartHour = currentTime.get(Calendar.HOUR_OF_DAY)
        selectedStartMinute = currentTime.get(Calendar.MINUTE)
        selectedFinishHour = currentTime.get(Calendar.HOUR_OF_DAY)
        selectedFinishMinute = currentTime.get(Calendar.MINUTE)
    }
}