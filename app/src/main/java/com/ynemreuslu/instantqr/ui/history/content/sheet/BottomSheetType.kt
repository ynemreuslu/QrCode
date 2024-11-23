package com.ynemreuslu.instantqr.ui.history.content.sheet

import com.ynemreuslu.instantqr.model.Calender
import com.ynemreuslu.instantqr.model.Contact
import com.ynemreuslu.instantqr.model.Email
import com.ynemreuslu.instantqr.model.Location
import com.ynemreuslu.instantqr.model.Phone
import com.ynemreuslu.instantqr.model.Sms
import com.ynemreuslu.instantqr.model.Text
import com.ynemreuslu.instantqr.model.Url
import com.ynemreuslu.instantqr.model.Wifi

sealed class BottomSheetType {
    data class TextSheet(val text: Text) : BottomSheetType()
    data class UrlSheet(val url: Url) : BottomSheetType()
    data class PhoneSheet(val phone: Phone) : BottomSheetType()
    data class SmsSheet(val sms: Sms) : BottomSheetType()
    data class EmailSheet(val email: Email) : BottomSheetType()
    data class LocationSheet(val location: Location) : BottomSheetType()
    data class ContactSheet(val contact: Contact) : BottomSheetType()
    data class CalenderSheet(val calender: Calender) : BottomSheetType()
    data class WifiSheet(val wifi: Wifi) : BottomSheetType()
}