package com.ynemreuslu.instantqr.ui.url

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
fun UrlScreen(viewModel: UrlViewModel = hiltViewModel()) {
    UrlContent(viewModel)
}

@Composable
fun UrlContent(viewModel: UrlViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UrlTitle(stringResource(R.string.url_label))
        UrlInputField(viewModel.url) { viewModel.onUrlChange(it) }
        Spacer(modifier = Modifier.weight(1f))
        UrlCreateButton(
            stringResource(R.string.saved),
            viewModel.isUrlValid()
        ) {
            viewModel.insertUrl()
        }
    }
}

@Composable
fun UrlTitle(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    )
}

@Composable
fun UrlInputField(urlText: String, onUrlChange: (String) -> Unit) {
    TextField(
        value = urlText,
        onValueChange = onUrlChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Uri,
            imeAction = ImeAction.Done,
        ),
        shape = RoundedCornerShape(12.dp),
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
fun UrlCreateButton(urlAdd: String, enabled: Boolean, onUrlSaveClick: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            onUrlSaveClick.invoke()
            Toast.makeText(context, urlAdd, Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled
    ) {
        Text(stringResource(R.string.create))
    }
}