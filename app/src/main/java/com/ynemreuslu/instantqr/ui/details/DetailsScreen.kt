package com.ynemreuslu.instantqr.ui.details

import android.app.SearchManager
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import com.ynemreuslu.instantqr.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.ynemreuslu.instantqr.model.QRCode



@Composable
fun DetailsScreen(
    qrCode: QRCode?,
) {
    val qrCode by rememberSaveable {
        mutableStateOf(qrCode)
    }
    val qrcodeText by rememberSaveable {
        mutableStateOf(qrCode?.qrCode ?: "")
    }
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current
    val copyMessage = stringResource(id = R.string.qr_code_copy)

    Column {
        DetailsTextField(value = qrCode?.qrCode ?: "")

        Spacer(Modifier.height(16.dp))

        DetailsIconButton(
            onCopyClick = {
                clipboard.setText(AnnotatedString(qrcodeText))
                Toast.makeText(context, copyMessage, Toast.LENGTH_SHORT).show()
            },
            onShareClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, qrcodeText)
                }
                context.startActivity(intent)
            },
            onLinkClick = {
                val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                    putExtra(SearchManager.QUERY, qrcodeText)
                }
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun DetailsTextField(
    value: String,
) {
    TextField(
        value = value,
        onValueChange = {},
        shape = RoundedCornerShape(12.dp),
        readOnly = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background
        ),
        textStyle = TextStyle(fontSize = 18.sp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
fun DetailsIconButton(
    onShareClick: () -> Unit,
    onCopyClick: () -> Unit,
    onLinkClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { onLinkClick.invoke() },
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_link),
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(Modifier.width(16.dp))

        IconButton(
            onClick = { onShareClick.invoke() },
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = null,
                tint = Color.White
            )
        }

        Spacer(Modifier.width(16.dp))

        IconButton(
            onClick = { onCopyClick.invoke() },
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_content_copy),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}