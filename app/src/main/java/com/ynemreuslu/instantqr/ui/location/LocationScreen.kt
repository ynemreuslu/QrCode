package com.ynemreuslu.instantqr.ui.location

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
fun LocationScreen(viewModel: LocationViewModel = hiltViewModel()) {
    LocationContent(viewModel)
}

@Composable
fun LocationContent(viewModel: LocationViewModel) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        LocationInputFields(
            viewModel.latitude,
            viewModel.longitude,
            { viewModel.updateLatitude(it) },
            { viewModel.updateLongitude(it) })
        Spacer(Modifier.weight(1f))
        LocationCreateButton(
            text = stringResource(R.string.saved),
            enabled = viewModel.isValid(),
            onLocationClick = {
                viewModel.createLocation()
            })
    }
}

@Composable
fun LocationInputFields(
    lat: String,
    long: String,
    onLatClick: (String) -> Unit,
    onLongClick: (String) -> Unit
) {
    Text(
        text = stringResource(R.string.latitude_label),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
    TextField(
        value = lat,
        onValueChange = onLatClick,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    Text(
        text = stringResource(R.string.longitude_label),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
    TextField(
        value = long,
        onValueChange = onLongClick,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
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
fun LocationCreateButton(text: String, enabled: Boolean, onLocationClick: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            onLocationClick.invoke()
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
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