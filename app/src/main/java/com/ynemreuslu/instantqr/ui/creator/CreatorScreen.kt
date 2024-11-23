package com.ynemreuslu.instantqr.ui.creator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ynemreuslu.instantqr.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatorScreen(
    onUrlSelected: () -> Unit,
    onTextSelected: () -> Unit,
    onContactSelected: () -> Unit,
    onPhoneSelected: () -> Unit,
    onSmsSelected: () -> Unit,
    onEmailSelected: () -> Unit,
    onLocationSelected: () -> Unit,
    onWifiSelected: () -> Unit,
    onCalendarSelected: () -> Unit,
) {
    LazyColumn {
        item {
            TextAndButtonCard(stringResource(R.string.url_label), onClick = onUrlSelected)
        }
        item {
            TextAndButtonCard(stringResource(R.string.text_label), onClick = onTextSelected)
        }
        item {
            TextAndButtonCard(stringResource(R.string.contact_label), onClick = onContactSelected)
        }
        item {
            TextAndButtonCard(stringResource(R.string.phone_label), onClick = onPhoneSelected)
        }
        item {
            TextAndButtonCard(stringResource(R.string.sms_label), onClick = onSmsSelected)
        }
        item {
            TextAndButtonCard(stringResource(R.string.email_label), onClick = onEmailSelected)
        }
        item {
            TextAndButtonCard(stringResource(R.string.location_label), onClick = onLocationSelected)
        }
        item {
            TextAndButtonCard(stringResource(R.string.wifi_label), onClick = onWifiSelected)
        }
        item {
            TextAndButtonCard(stringResource(R.string.calendar_label), onClick = onCalendarSelected)
        }
    }
}

@Composable
fun TextAndButtonCard(text: String, onClick: () -> Unit) {
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
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            IconButton(onClick = { onClick.invoke() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}