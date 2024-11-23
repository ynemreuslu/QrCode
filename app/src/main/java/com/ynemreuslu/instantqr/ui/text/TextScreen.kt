package com.ynemreuslu.instantqr.ui.text

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
fun TextScreen(viewModel: TextViewModel = hiltViewModel()) {
    TextContent(viewModel)
}

@Composable
fun TextContent(
    viewModel: TextViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle(stringResource(R.string.text_label))
        TextInputField(viewModel.text) {
            viewModel.onTextChange(it)
        }
        Spacer(Modifier.weight(1f))
        TextCreateButton(
            stringResource(R.string.saved), viewModel.isTextValid()
        ) {
            viewModel.insertText()
        }
    }
}

@Composable
fun TextTitle(text: String) {
    Text(
        text,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    )
}

@Composable
fun TextInputField(textInput: String, onValueChange: (String) -> Unit) {
    TextField(
        value = textInput,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
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
fun TextCreateButton(textAdd: String, enabled: Boolean, onTextSaveClick: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            onTextSaveClick.invoke()
            Toast.makeText(context, textAdd, Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled
    ) {
        Text(stringResource(R.string.create))
    }
}