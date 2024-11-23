package com.ynemreuslu.instantqr.ui.history.content.creator

import androidx.compose.runtime.Composable
import android.app.SearchManager
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.provider.ContactsContract
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ynemreuslu.instantqr.ui.history.content.card.CalenderCard
import com.ynemreuslu.instantqr.ui.history.content.card.ContactCard
import com.ynemreuslu.instantqr.ui.history.content.card.EmailCard
import com.ynemreuslu.instantqr.ui.history.content.card.LocationCard
import com.ynemreuslu.instantqr.ui.history.content.card.PhoneCard
import com.ynemreuslu.instantqr.ui.history.content.card.SmsCard
import com.ynemreuslu.instantqr.ui.history.content.card.SwipeToDismissListItem
import com.ynemreuslu.instantqr.ui.history.content.card.TextCard
import com.ynemreuslu.instantqr.ui.history.content.card.UrlCard
import com.ynemreuslu.instantqr.ui.history.content.card.WifiCard
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetHandle
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType
import com.ynemreuslu.instantqr.ui.history.content.sheet.CalenderSectionBottomSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.LocationSectionBottomSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.SingleSectionBottomSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.ThreeSectionBottomSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.TwoSectionBottomSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.WifiSectionBottomSheet
import com.ynemreuslu.instantqr.model.Calender
import com.ynemreuslu.instantqr.model.Contact
import com.ynemreuslu.instantqr.model.Email
import com.ynemreuslu.instantqr.model.Location
import com.ynemreuslu.instantqr.model.Phone
import com.ynemreuslu.instantqr.model.Sms
import com.ynemreuslu.instantqr.model.Text
import com.ynemreuslu.instantqr.model.Url
import com.ynemreuslu.instantqr.model.Wifi
import com.ynemreuslu.instantqr.R
import java.util.Calendar
import java.util.TimeZone
import com.ynemreuslu.instantqr.ui.history.content.card.QrCodeVsCreatorNot
import com.ynemreuslu.instantqr.ui.history.content.creator.CreatorItem.PhoneItem
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType.CalenderSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType.ContactSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType.EmailSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType.LocationSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType.PhoneSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType.SmsSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType.TextSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType.UrlSheet
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetType.WifiSheet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatorContent(
    viewModel: CreatorContentViewModel = hiltViewModel(),
) {
    var currentSheet by remember { mutableStateOf<BottomSheetType?>(null) }
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        uiState.items.isEmpty() -> {
            QrCodeVsCreatorNot(notMessage = stringResource(R.string.not_creator))
        }

        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.items.sortedByDescending { it.timestamp }) { item ->
                    when (item) {
                        is CreatorItem.TextItem -> {
                            SwipeToDismissListItem(
                                onDelete = { viewModel.removeText(item.text) },
                                content = { ItemCard(item.text) { currentSheet = TextSheet(it) } }
                            )
                        }

                        is CreatorItem.UrlItem -> {
                            SwipeToDismissListItem(
                                onDelete = { viewModel.removeUrls(item.url) },
                                content = { ItemCard(item.url) { currentSheet = UrlSheet(it) } }
                            )
                        }

                        is CreatorItem.CalendarItem -> {
                            SwipeToDismissListItem(
                                onDelete = { viewModel.removeCalender(item.calendar) },
                                content = {
                                    ItemCard(item.calendar) { currentSheet = CalenderSheet(it) }
                                }
                            )
                        }

                        is CreatorItem.ContactItem -> {
                            SwipeToDismissListItem(
                                onDelete = { viewModel.removeContact(item.contact) },
                                content = {
                                    ItemCard(item.contact) { currentSheet = ContactSheet(it) }
                                }
                            )
                        }

                        is CreatorItem.EmailItem -> {
                            SwipeToDismissListItem(
                                onDelete = { viewModel.removeEmail(item.email) },
                                content = { ItemCard(item.email) { currentSheet = EmailSheet(it) } }
                            )
                        }

                        is CreatorItem.LocationItem -> {
                            SwipeToDismissListItem(
                                onDelete = { viewModel.removeLocation(item.location) },
                                content = {
                                    ItemCard(item.location) { currentSheet = LocationSheet(it) }
                                }
                            )
                        }

                        is PhoneItem -> {
                            SwipeToDismissListItem(
                                onDelete = { viewModel.removePhone(item.phone) },
                                content = { ItemCard(item.phone) { currentSheet = PhoneSheet(it) } }
                            )
                        }

                        is CreatorItem.SmsItem -> {
                            SwipeToDismissListItem(
                                onDelete = { viewModel.removeSms(item.sms) },
                                content = { ItemCard(item.sms) { currentSheet = SmsSheet(it) } }
                            )
                        }

                        is CreatorItem.WifiItem -> {
                            SwipeToDismissListItem(
                                onDelete = { viewModel.removeWifi(item.wifi) },
                                content = { ItemCard(item.wifi) { currentSheet = WifiSheet(it) } }
                            )
                        }
                    }
                }
            }
        }
    }

    currentSheet?.let { sheet ->
        ModalBottomSheet(
            onDismissRequest = { currentSheet = null },
            sheetState = sheetState,
            dragHandle = {
                BottomSheetHandle(
                    onClose = { currentSheet = null },
                    onShare = { viewModel.handleShare(sheet) }
                )
            },
            containerColor = MaterialTheme.colorScheme.surface,
            content = {
                BottomSheetContent(
                    sheet = sheet,
                    bitmap = viewModel.generateQRCode(sheet.toString())
                )
            }
        )
    }
}



@Composable
fun <T> ItemCard(item: T, onItemClick: (T) -> Unit) {
    when (item) {
        is Text -> TextCard(item) { onItemClick.invoke(item) }
        is Url -> UrlCard(item) { onItemClick.invoke(item) }
        is Phone -> PhoneCard(item) { onItemClick.invoke(item) }
        is Sms -> SmsCard(item) { onItemClick.invoke(item) }
        is Email -> EmailCard(item) { onItemClick.invoke(item) }
        is Location -> LocationCard(item) { onItemClick.invoke(item) }
        is Wifi -> WifiCard(item) { onItemClick.invoke(item) }
        is Contact -> ContactCard(item) { onItemClick.invoke(item) }
        is Calender -> CalenderCard(item) { onItemClick.invoke(item) }

    }
}


@Composable
fun BottomSheetContent(sheet: BottomSheetType, bitmap: Bitmap) {
    val context = LocalContext.current
    when (sheet) {
        is BottomSheetType.TextSheet -> SingleSectionBottomSheet(
            sectionTitle = stringResource(R.string.text_label),
            sectionDescription = sheet.text.text,
            buttonText = "Search",
            onButtonClick = {
                val intentText = Intent(Intent.ACTION_WEB_SEARCH).apply {
                    putExtra(SearchManager.QUERY, sheet.text.text)
                }
                context.startActivity(intentText)
            },
            bitmap = bitmap
        )

        is BottomSheetType.UrlSheet -> SingleSectionBottomSheet(
            sectionTitle = "Url",
            sectionDescription = sheet.url.url,
            buttonText = "Open URL",
            onButtonClick = {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(sheet.url.url))
                context.startActivity(browserIntent)
            },
            bitmap = bitmap
        )

        is BottomSheetType.PhoneSheet -> SingleSectionBottomSheet(
            sectionTitle = "Phone Number",
            sectionDescription = sheet.phone.number,
            buttonText = "Call",
            onButtonClick = {
                val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${sheet.phone.number}")
                }
                context.startActivity(phoneIntent)
            },
            bitmap = bitmap
        )

        is BottomSheetType.SmsSheet -> SmsBottomSheetContent(
            sheet.sms, bitmap = bitmap
        )

        is BottomSheetType.EmailSheet -> EmailBottomSheetContent(
            sheet.email, bitmap = bitmap
        )

        is BottomSheetType.LocationSheet -> LocationBottomSheetContent(
            sheet.location, bitmap = bitmap
        )

        is BottomSheetType.ContactSheet -> ContactBottomSheetContent(
            sheet.contact, bitmap = bitmap
        )

        is BottomSheetType.CalenderSheet -> CalenderBottomSheet(
            sheet.calender, bitmap = bitmap
        )

        is BottomSheetType.WifiSheet -> WifiBottomSheet(
            sheet.wifi, bitmap = bitmap
        )

    }
}

@Composable
fun EmailBottomSheetContent(email: Email, bitmap: Bitmap) {
    val context = LocalContext.current
    ThreeSectionBottomSheet(
        firstSectionTitle = stringResource(R.string.email_label),
        firstSectionDescription = email.address,
        secondSectionTitle = stringResource(R.string.subject_label),
        secondSectionDescription = email.subject ?: "",
        thirdSectionTitle = stringResource(R.string.contact_label),
        thirdSectionDescription = email.body ?: "",
        buttonText = "Open Mail",
        onButtonClick = {
            val emailIntent =
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email.address))
                    putExtra(Intent.EXTRA_SUBJECT, email.subject)
                    putExtra(Intent.EXTRA_TEXT, email.body)
                }
            context.startActivity(emailIntent)
        },
        bitmap = bitmap
    )
}


@Composable
fun LocationBottomSheetContent(
    location: Location, bitmap: Bitmap
) {
    val context = LocalContext.current
    LocationSectionBottomSheet(
        lat = "${location.latitude}",
        long = "${location.longitude}",
        onButtonClick = {
            val latitude = location.latitude
            val longitude = location.longitude
            val geoLocation = Uri.parse("geo:$latitude,$longitude")
            val mapIntent = Intent(Intent.ACTION_VIEW).apply {
                data = geoLocation
            }
            context.startActivity(mapIntent)
        },
        bitmap = bitmap
    )

}

@Composable
fun ContactBottomSheetContent(contact: Contact, bitmap: Bitmap) {
    val context = LocalContext.current
    ThreeSectionBottomSheet(
        firstSectionTitle = stringResource(R.string.name_label),
        firstSectionDescription = contact.name ?: "",
        secondSectionTitle = stringResource(R.string.phone_label),
        secondSectionDescription = contact.number.toString(),
        thirdSectionTitle = stringResource(R.string.email_label),
        thirdSectionDescription = contact.email ?: "",
        buttonText = stringResource(R.string.open_contract),
        onButtonClick = {
            val telIntent = Intent(Intent.ACTION_INSERT).apply {
                type = ContactsContract.Contacts.CONTENT_TYPE
                putExtra(ContactsContract.Intents.Insert.NAME, contact.name)
                putExtra(ContactsContract.Intents.Insert.EMAIL, contact.email)
                putExtra(ContactsContract.Intents.Insert.PHONE, contact.number)

            }
            context.startActivity(telIntent)
        },
        bitmap = bitmap
    )
}

@Composable
fun SmsBottomSheetContent(
    sms: Sms, bitmap: Bitmap
) {
    val context = LocalContext.current
    TwoSectionBottomSheet(
        firstSectionTitle = stringResource(R.string.phone_label),
        firstSectionDescription = sms.number,
        secondSectionTitle = stringResource(R.string.message_label),
        secondSectionDescription = sms.message,
        buttonText = stringResource(R.string.open_sms),
        onButtonClick = {
            val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:${sms.number}")
                putExtra("sms_body", sms.message)
            }
            context.startActivity(smsIntent)
        },
        bitmap = bitmap
    )

}


@Composable
fun CalenderBottomSheet(
    calender: Calender, bitmap: Bitmap
) {
    val context = LocalContext.current
    val timeZone = TimeZone.getDefault()

    CalenderSectionBottomSheet(
        title = calender.calenderTitle,
        startDate = calender.startDate,
        finishDate = calender.finishDate,
        startTime = calender.startTime,
        finishTime = calender.finishTime,
        buttonText = stringResource(R.string.open_calendar),
        onButtonClick = {
            val gmtOffset = timeZone.getOffset(System.currentTimeMillis())
            val startCalendar =
                Calendar.getInstance(TimeZone.getTimeZone("GMT")).apply {
                    timeInMillis = calender.startDate - gmtOffset
                    val startHour =
                        (calender.startTime / (1000 * 60 * 60)).toInt() % 24
                    val startMinute =
                        (calender.startTime / (1000 * 60)).toInt() % 60
                    set(Calendar.HOUR_OF_DAY, startHour)
                    set(Calendar.MINUTE, startMinute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

            val finishCalendar =
                Calendar.getInstance(TimeZone.getTimeZone("GMT")).apply {
                    timeInMillis = calender.finishDate - gmtOffset
                    val finishHour =
                        (calender.finishTime / (1000 * 60 * 60)).toInt() % 24
                    val finishMinute =
                        (calender.finishTime / (1000 * 60)).toInt() % 60
                    set(Calendar.HOUR_OF_DAY, finishHour)
                    set(Calendar.MINUTE, finishMinute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

            val calenderIntent = Intent(Intent.ACTION_INSERT).apply {
                data = Events.CONTENT_URI
                putExtra(Events.TITLE, calender.calenderTitle)
                putExtra(Events.EVENT_LOCATION, calender.location)
                putExtra(Events.DESCRIPTION, calender.explanation)
                putExtra(
                    CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    startCalendar.timeInMillis
                )
                putExtra(
                    CalendarContract.EXTRA_EVENT_END_TIME,
                    finishCalendar.timeInMillis
                )
                putExtra(Events.EVENT_TIMEZONE, "GMT")
            }

            context.startActivity(calenderIntent)
        },
        bitmap = bitmap
    )
}


@Composable
fun WifiBottomSheet(wifi: Wifi, bitmap: Bitmap) {
    val context = LocalContext.current
    WifiSectionBottomSheet(
        wifiName = wifi.wifiName,
        wifiPassword = wifi.wifiPassword.toString(),
        wifiType = wifi.wifiType.toString(),
        onButtonClick = {
            val wifiSettingsIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
            context.startActivity(wifiSettingsIntent)
        },
        bitmap = bitmap
    )
}