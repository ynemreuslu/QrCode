package com.ynemreuslu.instantqr.ui.sms

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ynemreuslu.instantqr.R
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SmsScreen(viewModel: SmsViewModel = hiltViewModel()) {
    SmsContent(viewModel)
}

@Composable
fun SmsContent(
    viewModel: SmsViewModel,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        SmsInputField(
            label = stringResource(R.string.phone_label),
            value = viewModel.phone,
            onValueChange = viewModel::onTextChange,
            keyboardType = KeyboardType.Phone
        )
        SmsInputField(
            label = stringResource(R.string.message_label),
            value = viewModel.message,
            onValueChange = viewModel::onMessageChange,
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.weight(1f))

        SendSmsButton(
            text = stringResource(R.string.saved),
            enabled = viewModel.isFormValid(),
            onSmsClick = {
                viewModel.insertSms()
            }
        )
    }
}

@Composable
fun SmsInputField(
    label: String, value: String, onValueChange: (String) -> Unit, keyboardType: KeyboardType
) {
    Text(
        text = label, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),

        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background
        )
    )

}

@Composable
fun SendSmsButton(
    text: String,
    enabled: Boolean,
    onSmsClick: () -> Unit
) {
    val context = LocalContext.current
    Button(
        onClick = {
            onSmsClick()
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, bottom = 16.dp, end = 8.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled
    ) {
        Text(stringResource(R.string.create))
    }
}