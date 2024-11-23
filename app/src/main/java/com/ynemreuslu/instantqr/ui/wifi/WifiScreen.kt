package com.ynemreuslu.instantqr.ui.wifi

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ynemreuslu.instantqr.R

@Composable
fun WifiScreen(
    viewModel: WifiViewModel = hiltViewModel()
) {
    WifiContent(viewModel)
}


@Composable
fun WifiContent(viewModel: WifiViewModel) {
    val isPasswordVisible = viewModel.selectedWifiType != WifiType.NONE

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        WifiNameField(
            wifiName = viewModel.wifiName,
            onValueChange = viewModel::onWifiName
        )

        AnimatedVisibility(visible = isPasswordVisible) {
            WifiPasswordField(
                password = viewModel.wifiPassword,
                onPasswordChange = viewModel::onWifiPassword
            )
        }

        WifiTypeSelector(
            selectedWifiType = viewModel.selectedWifiType,
            onTypeSelected = viewModel::onWifiTypeSelection
        )

        Spacer(Modifier.weight(1f))

        WifiCreateButton(
            textToast = stringResource(R.string.saved),
            enabled = viewModel.isNotWifiName(),
            onWifiClick = {
                if (viewModel.selectedWifiTypeNone()) {
                    viewModel.clearPassword()
                }
                viewModel.insertWifi()
            }
        )
    }
}

@Composable
fun WifiNameField(wifiName: String, onValueChange: (String) -> Unit) {
    InputField(
        label = stringResource(R.string.wifi_name_label),
        value = wifiName,
        onValueChange = onValueChange
    )
}

@Composable
fun WifiPasswordField(password: String, onPasswordChange: (String) -> Unit) {
    var showPassword by remember { mutableStateOf(false) }

    InputField(
        label = stringResource(R.string.wifi_password_label),
        value = password,
        onValueChange = onPasswordChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    painter = painterResource(id = if (showPassword) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                    contentDescription = if (showPassword) "Hide Password" else "Show Password"
                )
            }
        }
    )
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )
        TextField(
            value = value,
            shape = RoundedCornerShape(12.dp),
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background
            ),
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions
        )
    }
}

@Composable
fun WifiTypeSelector(
    selectedWifiType: WifiType,
    onTypeSelected: (WifiType) -> Unit
) {
    SegmentedControl(
        items = WifiType.entries,
        selectedWifiType = selectedWifiType,
        onItemSelection = onTypeSelected
    )
}

@Composable
fun WifiCreateButton(textToast: String, enabled: Boolean, onWifiClick: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            onWifiClick()
            Toast.makeText(context, textToast, Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(stringResource(R.string.create))
    }
}

@Composable
fun SegmentedControl(
    items: List<WifiType>,
    selectedWifiType: WifiType,
    onItemSelection: (WifiType) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEach { wifiType ->
                val isSelected = wifiType == selectedWifiType
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp),
                    onClick = { onItemSelection(wifiType) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected)
                            MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                        contentColor = if (isSelected)
                            MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                    ),
                    shape = when (wifiType) {
                        items.first() -> RoundedCornerShape(
                            topStartPercent = 50,
                            bottomStartPercent = 50
                        )
                        items.last() -> RoundedCornerShape(
                            topEndPercent = 50,
                            bottomEndPercent = 50
                        )
                        else -> RoundedCornerShape(0)
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = wifiType.type,
                            textAlign = TextAlign.Center,
                            style = LocalTextStyle.current.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}
