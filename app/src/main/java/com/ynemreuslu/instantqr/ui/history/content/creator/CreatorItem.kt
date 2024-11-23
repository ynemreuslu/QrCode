package com.ynemreuslu.instantqr.ui.history.content.creator

import com.ynemreuslu.instantqr.model.Calender
import com.ynemreuslu.instantqr.model.Contact
import com.ynemreuslu.instantqr.model.Email
import com.ynemreuslu.instantqr.model.Location
import com.ynemreuslu.instantqr.model.Phone
import com.ynemreuslu.instantqr.model.Sms
import com.ynemreuslu.instantqr.model.Text
import com.ynemreuslu.instantqr.model.Url
import com.ynemreuslu.instantqr.model.Wifi

sealed class CreatorItem(val timestamp: Long) {
    data class TextItem(val text: Text) : CreatorItem(text.timestamp)
    data class UrlItem(val url: Url) : CreatorItem(url.timestamp)
    data class ContactItem(val contact: Contact) : CreatorItem(contact.timestamp)
    data class PhoneItem(val phone: Phone) : CreatorItem(phone.timestamp)
    data class SmsItem(val sms: Sms) : CreatorItem(sms.timestamp)
    data class EmailItem(val email: Email) : CreatorItem(email.timestamp)
    data class LocationItem(val location: Location) : CreatorItem(location.timestamp)
    data class WifiItem(val wifi: Wifi) : CreatorItem(wifi.timestamp)
    data class CalendarItem(val calendar: Calender) : CreatorItem(calendar.timestamp)
}