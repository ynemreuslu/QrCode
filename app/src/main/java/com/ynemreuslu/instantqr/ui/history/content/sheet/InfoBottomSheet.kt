package com.ynemreuslu.instantqr.ui.history.content.sheet

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import com.ynemreuslu.instantqr.R

@Composable
fun SingleSectionBottomSheet(
    sectionTitle: String,
    sectionDescription: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    bitmap: Bitmap
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "QR Code Logo",
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally),

            )

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = sectionTitle,
                modifier = Modifier
                    .weight(1f)
                    .padding(all = 16.dp),
                fontFamily = FontFamily.Cursive,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraLight,
                textAlign = TextAlign.Start
            )

            Text(
                text = sectionDescription,
                modifier = Modifier
                    .weight(1f)
                    .padding(all = 16.dp),
                fontFamily = FontFamily.Serif,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = buttonText)
        }
    }
}

@Composable
fun TwoSectionBottomSheet(
    firstSectionTitle: String,
    firstSectionDescription: String,
    secondSectionTitle: String,
    secondSectionDescription: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    bitmap: Bitmap
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = stringResource(R.string.qr_code_logo),
            modifier = Modifier
                .size(300.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(all = 16.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = firstSectionTitle,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = firstSectionDescription,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )

            Text(
                text = secondSectionTitle,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = secondSectionDescription,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )

            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(buttonText)
            }
        }
    }
}

@Composable
fun LocationSectionBottomSheet(
    lat: String,
    long: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    bitmap: Bitmap
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = stringResource(R.string.qr_code_logo),
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally),
        )

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.latitude_label),
                    fontFamily = FontFamily.Cursive,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = stringResource(R.string.longitude_label),
                    fontFamily = FontFamily.Cursive,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Start
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = lat,
                    fontFamily = FontFamily.Serif,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = long,
                    fontFamily = FontFamily.Serif,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }

        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = stringResource(R.string.open_map))
        }
    }
}


@Composable
fun ThreeSectionBottomSheet(
    firstSectionTitle: String,
    firstSectionDescription: String,
    secondSectionTitle: String,
    secondSectionDescription: String,
    thirdSectionTitle: String,
    thirdSectionDescription: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    bitmap: Bitmap
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = stringResource(R.string.qr_code_logo),
            modifier = Modifier
                .size(300.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(all = 16.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = firstSectionTitle,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = firstSectionDescription,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )


            Text(
                text = secondSectionTitle,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = secondSectionDescription,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )


            Text(
                text = thirdSectionTitle,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = thirdSectionDescription,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )


            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(buttonText)
            }
        }
    }
}


@Composable
fun CalenderSectionBottomSheet(
    title: String,
    startDate: Long,
    finishDate: Long,
    startTime: Long,
    finishTime: Long,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    bitmap: Bitmap
) {
    val localTimeZone = TimeZone.getDefault()


    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).apply {
        timeZone = localTimeZone
    }
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
        timeZone = localTimeZone
    }

    val formattedStartDate = dateFormat.format(Date(startDate))
    val formattedFinishDate = dateFormat.format(Date(finishDate))
    val formattedStartTime = timeFormat.format(Date(startTime))
    val formattedFinishTime = timeFormat.format(Date(finishTime))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = stringResource(R.string.qr_code_logo),
            modifier = Modifier
                .size(250.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(all = 16.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = stringResource(R.string.calendar_label),
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )


            Text(
                text = stringResource(R.string.start_date_label),
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = formattedStartDate,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )


            Text(
                text = stringResource(R.string.end_time_label),
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = formattedFinishDate,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )


            Text(
                text = stringResource(R.string.start_time_label),
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 4.dp)
            )
            Text(
                text = formattedStartTime,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )


            Text(
                text = stringResource(R.string.end_time_label),
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = formattedFinishTime,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )


            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(buttonText)
            }
        }
    }
}

@Composable
fun HistoryContentBottomSheet(
    sectionTitle: String,
    sectionDescription: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    bitmap: Bitmap
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = stringResource(R.string.qr_code_logo),
            modifier = Modifier
                .size(300.dp)
                .fillMaxWidth()
                .padding(all = 16.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = sectionTitle,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = sectionDescription,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )


            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(buttonText)
            }
        }
    }
}

@Composable
fun WifiSectionBottomSheet(
    wifiName: String,
    wifiPassword: String,
    wifiType: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    bitmap: Bitmap
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = stringResource(R.string.qr_code_logo),
            modifier = Modifier
                .size(300.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(all = 16.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = stringResource(R.string.wifi_label),
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )

            Text(
                text = wifiName,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )

            if (wifiPassword.isNotBlank()) {
                Text(
                    text = stringResource(R.string.wifi_password_label),
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.ExtraLight,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
                )
                Text(
                    text = wifiPassword,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
                )

            }

            Text(
                text = stringResource(R.string.wifi_type_label),
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = wifiType,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            )


            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(stringResource(R.string.open_wifi))
            }
        }
    }
}


@Composable
fun DragHandle(onClose: () -> Unit, onShare: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { onClose() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close),
                tint = Color.Black
            )
        }

        IconButton(onClick = { onShare() }) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = stringResource(R.string.share),
                tint = Color.Black
            )
        }
    }
}