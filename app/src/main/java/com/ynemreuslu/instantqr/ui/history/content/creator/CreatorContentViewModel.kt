package com.ynemreuslu.instantqr.ui.history.content.creator

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType
import com.ynemreuslu.instantqr.model.Calender
import com.ynemreuslu.instantqr.model.Contact
import com.ynemreuslu.instantqr.model.Email
import com.ynemreuslu.instantqr.model.Location
import com.ynemreuslu.instantqr.model.Phone
import com.ynemreuslu.instantqr.model.Sms
import com.ynemreuslu.instantqr.model.Text
import com.ynemreuslu.instantqr.model.Url
import com.ynemreuslu.instantqr.model.Wifi
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreatorContentViewModel @Inject constructor(
    private val repository: CreatorRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreatorContentUiState())
    val uiState: StateFlow<CreatorContentUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchAllData()
        }
    }

    private suspend fun fetchAllData() {
        _uiState.update { it.copy(isLoading = true) }

        val items = coroutineScope {
            listOf(
                async { fetchAllUrl() },
                async { fetchAllWifi() },
                async { fetchAllSms() },
                async { fetchAllCalendar() },
                async { fetchAllEmail() },
                async { fetchAllLocation() },
                async { fetchAllText() },
                async { fetchAllPhone() },
                async { fetchAllContact() }
            ).awaitAll().flatten()
        }

        _uiState.update { it.copy(items = items, isLoading = false) }
    }

    private suspend fun fetchAllText() = repository.fetchAllTexts().first().map { CreatorItem.TextItem(it) }
    private suspend fun fetchAllUrl() = repository.fetchAllUrl().first().map { CreatorItem.UrlItem(it) }
    private suspend fun fetchAllContact() = repository.fetchAllContacts().first().map { CreatorItem.ContactItem(it) }
    private suspend fun fetchAllPhone() = repository.fetchAllPhones().first().map { CreatorItem.PhoneItem(it) }
    private suspend fun fetchAllSms() = repository.fetchAllSms().first().map { CreatorItem.SmsItem(it) }
    private suspend fun fetchAllEmail() = repository.fetchAllEmails().first().map { CreatorItem.EmailItem(it) }
    private suspend fun fetchAllLocation() = repository.fetchAllLocations().first().map { CreatorItem.LocationItem(it) }
    private suspend fun fetchAllWifi() = repository.fetchAllWifi().first().map { CreatorItem.WifiItem(it) }
    private suspend fun fetchAllCalendar() = repository.fetchAllCalender().first().map { CreatorItem.CalendarItem(it) }

    private fun removeItem(filter: (CreatorItem) -> Boolean) {
        _uiState.update { it.copy(items = it.items.filterNot(filter)) }
    }

    fun removeText(text: Text) = viewModelScope.launch {
        repository.removeText(text)
        removeItem { it is CreatorItem.TextItem && it.text == text }
    }

    fun removeUrls(url: Url) = viewModelScope.launch {
        repository.removeUrl(url)
        removeItem { it is CreatorItem.UrlItem && it.url == url }
    }

    fun removeContact(contact: Contact) = viewModelScope.launch {
        repository.removeContact(contact)
        removeItem { it is CreatorItem.ContactItem && it.contact == contact }
    }

    fun removePhone(phone: Phone) = viewModelScope.launch {
        repository.removePhone(phone)
        removeItem { it is CreatorItem.PhoneItem && it.phone == phone }
    }

    fun removeSms(sms: Sms) = viewModelScope.launch {
        repository.removeSms(sms)
        removeItem { it is CreatorItem.SmsItem && it.sms == sms }
    }

    fun removeEmail(email: Email) = viewModelScope.launch {
        repository.removeEmail(email)
        removeItem { it is CreatorItem.EmailItem && it.email == email }
    }

    fun removeLocation(location: Location) = viewModelScope.launch {
        repository.removeLocation(location)
        removeItem { it is CreatorItem.LocationItem && it.location == location }
    }

    fun removeWifi(wifi: Wifi) = viewModelScope.launch {
        repository.removeWifi(wifi)
        removeItem { it is CreatorItem.WifiItem && it.wifi == wifi }
    }

    fun removeCalender(calender: Calender) = viewModelScope.launch {
        repository.removeCalender(calender)
        removeItem { it is CreatorItem.CalendarItem && it.calendar == calender }
    }


    fun generateQRCode(
        content: String,
        width: Int = QR_CODE_WIDTH,
        height: Int = QR_CODE_HEIGHT
    ): Bitmap {
        val bitMatrix: BitMatrix =
            MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height)
        return Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565).apply {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    setPixel(x, y, if (bitMatrix[x, y]) -0x1000000 else -0x1)
                }
            }
        }
    }

    companion object {
        private const val QR_CODE_WIDTH = 600
        private const val QR_CODE_HEIGHT = 600
    }

    fun handleShare(sheet: BottomSheetType) {
        val qrContent = extractContentForQRCode(sheet)
        qrContent?.let {
            val bitmap = generateQRCode(it)
            shareBitmap(bitmap)
        }
    }

    private fun extractContentForQRCode(sheet: BottomSheetType): String? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        return when (sheet) {
            is BottomSheetType.TextSheet -> sheet.text.text
            is BottomSheetType.UrlSheet -> sheet.url.url
            is BottomSheetType.PhoneSheet -> sheet.phone.number
            is BottomSheetType.SmsSheet -> "${sheet.sms.number},${sheet.sms.message}"
            is BottomSheetType.EmailSheet -> "${sheet.email.address} ${sheet.email.subject},${sheet.email.body}"
            is BottomSheetType.LocationSheet -> "${sheet.location.latitude},${sheet.location.longitude}"
            is BottomSheetType.ContactSheet -> "${sheet.contact.name},${sheet.contact.number},${sheet.contact.email}"
            is BottomSheetType.CalenderSheet -> {
                val formattedStartDate = dateFormat.format(sheet.calender.startDate)
                val formattedFinishDate = dateFormat.format(sheet.calender.finishDate)
                val formattedStartTime = timeFormat.format(sheet.calender.startTime)
                val formattedFinishTime = timeFormat.format(sheet.calender.finishTime)

                "${sheet.calender.calenderTitle}, $formattedStartDate, $formattedFinishDate, " +
                        "$formattedStartTime, $formattedFinishTime, ${sheet.calender.location}, ${sheet.calender.explanation}"
            }

            is BottomSheetType.WifiSheet -> "${sheet.wifi.wifiName},${sheet.wifi.wifiPassword},${sheet.wifi.wifiType}"
            else -> null
        }
    }

    fun shareBitmap(bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "QR_Code_${System.currentTimeMillis()}.png"
            )
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                "${Environment.DIRECTORY_PICTURES}/QR Codes"
            )
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
            ?.let { uri ->
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                context.contentResolver.update(uri, contentValues, null, null)

                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, uri)
                    type = "image/png"
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(
                    Intent.createChooser(shareIntent, "Share QR Code").apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
            }
    }
}