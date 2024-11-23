package com.ynemreuslu.instantqr.ui.history.content.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ynemreuslu.instantqr.R
import com.ynemreuslu.instantqr.model.Calender
import com.ynemreuslu.instantqr.model.Contact
import com.ynemreuslu.instantqr.model.Email
import com.ynemreuslu.instantqr.model.Location
import com.ynemreuslu.instantqr.model.Phone
import com.ynemreuslu.instantqr.model.QRCode
import com.ynemreuslu.instantqr.model.Sms
import com.ynemreuslu.instantqr.model.Text
import com.ynemreuslu.instantqr.model.Url
import com.ynemreuslu.instantqr.model.Wifi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun BaseCard(
    iconRes: Int,
    contentDescription: String?,
    content: @Composable () -> Unit,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .padding(bottom = 0.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(0.dp, Color.LightGray),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )
            content()
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun TextCard(text: Text, onTextClick: (text: Text) -> Unit) {
    BaseCard(
        iconRes = R.drawable.ic_text,
        contentDescription = stringResource(R.string.image_not_found),
        content = {
            Text(
                text = text.text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        },
        onClick = { onTextClick(text) }
    )
}

@Composable
fun UrlCard(url: Url, onUrlClick: (url: Url) -> Unit) {
    BaseCard(
        iconRes = R.drawable.ic_link,
        contentDescription = stringResource(R.string.image_not_found),
        content = {
            Text(
                text = url.url,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        },
        onClick = { onUrlClick(url) }
    )
}

@Composable
fun QrCodeCard(qrCode: QRCode, onQrCodeClick: (qrCode: QRCode) -> Unit) {
    BaseCard(
        iconRes = R.drawable.ic_share,
        contentDescription = stringResource(R.string.image_not_found),
        content = {
            Text(
                text = qrCode.qrCode,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

        },
        onClick = { onQrCodeClick(qrCode) }
    )
}

@Composable
fun QrCodeVsCreatorNot(notMessage: String) {
    OutlinedCard(
        modifier = Modifier
            .padding()
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(0.dp, Color.LightGray),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.empty),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            Text(
                text = notMessage,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.ExtraLight,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PhoneCard(phone: Phone, onPhoneClick: (phone: Phone) -> Unit) {
    BaseCard(
        iconRes = R.drawable.ic_phone,
        contentDescription = stringResource(R.string.image_not_found),
        content = {
            Text(
                text = phone.number,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

        },
        onClick = { onPhoneClick(phone) }
    )
}

@Composable
fun SmsCard(sms: Sms, onSmsClick: (sms: Sms) -> Unit) {
    BaseCard(
        iconRes = R.drawable.ic_message,
        contentDescription = stringResource(R.string.image_not_found),
        content = {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text(
                    text = "Phone: ${sms.number}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Message: ${sms.message}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
        onClick = { onSmsClick(sms) }
    )
}

@Composable
fun EmailCard(email: Email, onEmailClick: (email: Email) -> Unit) {
    BaseCard(
        iconRes = R.drawable.ic_email,
        contentDescription = stringResource(R.string.image_not_found),
        content = {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text(
                    text = "Email: ${email.address}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Subject: ${email.subject}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Body: ${email.body}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
        onClick = { onEmailClick(email) }
    )
}

@Composable
fun LocationCard(location: Location, onLocationClick: (location: Location) -> Unit) {
    BaseCard(
        iconRes = R.drawable.ic_location,
        contentDescription = stringResource(R.string.image_not_found),
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(
                    text = "Latitude: ${location.latitude}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Longitude: ${location.longitude}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
        onClick = { onLocationClick(location) }
    )
}

@Composable
fun WifiCard(wifi: Wifi, onWifiClick: (wifi: Wifi) -> Unit) {
    BaseCard(
        iconRes = R.drawable.ic_wifi,
        contentDescription = stringResource(R.string.image_not_found),
        content = {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text(
                    text = wifi.wifiName,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (!wifi.wifiPassword.isNullOrBlank()) {
                    Text(
                        text = wifi.wifiPassword.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = wifi.wifiType,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        onClick = { onWifiClick(wifi) }
    )
}

@Composable
fun ContactCard(contact: Contact, onContactClick: (contact: Contact) -> Unit) {
    BaseCard(
        iconRes = R.drawable.ic_contacts,
        contentDescription = stringResource(R.string.image_not_found),
        content = {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text(
                    text = "Full Name: ${contact.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Phone Number: ${contact.number}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (!contact.email.isNullOrBlank()) {
                    Text(
                        text = "E-Mail: ${contact.email}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        onClick = { onContactClick(contact) }
    )
}

@Composable
fun CalenderCard(calender: Calender, onCalenderClick: (calender: Calender) -> Unit) {
    BaseCard(
        iconRes = R.drawable.ic_calendar,
        contentDescription = stringResource(R.string.image_not_found),
        content = {
            val localTimeZone = TimeZone.getDefault()
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).apply {
                timeZone = localTimeZone
            }
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
                timeZone = localTimeZone
            }

            val formattedStartDate = dateFormat.format(Date(calender.startDate))
            val formattedFinishDate = dateFormat.format(Date(calender.finishDate))
            val formattedStartTime = timeFormat.format(Date(calender.startTime))
            val formattedFinishTime = timeFormat.format(Date(calender.finishTime))

            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text(
                    text = "Title: ${calender.calenderTitle}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Start Date: $formattedStartDate",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Finish Date: $formattedFinishDate",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Start Time: $formattedStartTime",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Finish Time: $formattedFinishTime",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (!calender.explanation.isNullOrBlank()) {
                    Text(
                        text = "Explanation: ${calender.explanation}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (!calender.location.isNullOrBlank()) {
                    Text(
                        text = "Location: ${calender.location}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        onClick = { onCalenderClick(calender) }
    )
}