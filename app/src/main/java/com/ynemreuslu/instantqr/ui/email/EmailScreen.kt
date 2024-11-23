package com.ynemreuslu.instantqr.ui.email

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
fun EmailScreen(viewModel: EmailViewModel = hiltViewModel()) {
    EmailContent(viewModel)
}

@Composable
fun EmailContent(viewModel: EmailViewModel) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        TextEmail(
            text = stringResource(R.string.email_label),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        EmailTextField(
            value = viewModel.emailAddress,
            onValueChange = viewModel::updateEmailAddress,
            keyboardType = KeyboardType.Email
        )

        TextEmail(
            text = stringResource(R.string.subject_label),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        EmailTextField(
            value = viewModel.emailSubject,
            onValueChange = viewModel::updateEmailSubject,
            keyboardType = KeyboardType.Text
        )

        TextEmail(
            text = stringResource(R.string.contact_label),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        EmailTextField(
            value = viewModel.emailBody,
            onValueChange = viewModel::updateEmailBody,
            keyboardType = KeyboardType.Text
        )

        Spacer(Modifier.weight(1f))

        EmailCreateButton(
            textToast = stringResource(R.string.saved),
            enabled = viewModel.isValidEmail(),
            onEmailClick = {
                viewModel.insertEmail()
            }
        )
    }
}

@Composable
fun TextEmail(text: String, modifier: Modifier) {
    Text(text = text, modifier = modifier)
}

@Composable
fun EmailTextField(
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
            .padding(vertical = 8.dp)
    )
}

@Composable
fun EmailCreateButton(textToast: String, enabled: Boolean, onEmailClick: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            onEmailClick.invoke()
            Toast.makeText(context, textToast, Toast.LENGTH_SHORT).show()
        },
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = stringResource(R.string.create))
    }
}