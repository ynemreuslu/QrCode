package com.ynemreuslu.instantqr.ui.contact

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
import androidx.compose.material3.ButtonDefaults
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
fun ContactScreen(viewModel: ContactViewModel = hiltViewModel()) {
    ContactContent(viewModel)
}

@Composable
fun ContactContent(viewModel: ContactViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TextContact(stringResource(R.string.name_label))
        InputContactField(
            value = viewModel.fullName,
            onValueChange = viewModel::onFullNameChange,
            keyboardType = KeyboardType.Text
        )

        TextContact(stringResource(R.string.phone_label))
        InputContactField(
            value = viewModel.phoneNumber,
            onValueChange = viewModel::onPhoneNumberChange,
            keyboardType = KeyboardType.Phone
        )

        TextContact(stringResource(R.string.email_label))
        InputContactField(
            value = viewModel.email,
            onValueChange = viewModel::onEmailChange,
            keyboardType = KeyboardType.Email
        )

        Spacer(Modifier.weight(1f))

        ContactCreateButton(
            textToast = stringResource(R.string.saved),
            enabled = viewModel.isInputEmpty(),
            onContactClick = { viewModel.insertContact() },
        )
    }
}

@Composable
fun ContactCreateButton(textToast: String, enabled: Boolean, onContactClick: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            onContactClick.invoke()
            Toast.makeText(context, textToast, Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(stringResource(R.string.create))
    }
}

@Composable
fun InputContactField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
fun TextContact(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
}