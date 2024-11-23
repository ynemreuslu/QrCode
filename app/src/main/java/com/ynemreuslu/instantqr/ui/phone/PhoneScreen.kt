package com.ynemreuslu.instantqr.ui.phone

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ynemreuslu.instantqr.R

@Composable
fun PhoneScreen(viewModel: PhoneViewModel = hiltViewModel()) {
    PhoneContent(viewModel = viewModel)
}

@Composable
fun PhoneContent(viewModel: PhoneViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.phone_label),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        PhoneInputField(
            phone = viewModel.phone,
            onPhoneChange = viewModel::onPhoneChange
        )

        Spacer(modifier = Modifier.weight(1f))

        PhoneCreateButton(
            text = stringResource(R.string.saved),
            enabled = viewModel.validatePhoneNumber(viewModel.phone),
            onPhoneButtonClick = { viewModel.insertPhone() }
        )
    }
}

@Composable
fun PhoneInputField(
    phone: String,
    onPhoneChange: (String) -> Unit
) {

    TextField(
        value = phone,
        onValueChange = onPhoneChange,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun PhoneCreateButton(text: String, enabled: Boolean, onPhoneButtonClick: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            onPhoneButtonClick.invoke()
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        },
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(stringResource(R.string.create))
    }
}