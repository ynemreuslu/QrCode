package com.ynemreuslu.instantqr.ui.calendar

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.ynemreuslu.instantqr.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun CalendarScreen(viewModel: CalenderViewModel = hiltViewModel()) {
    CalendarContent(viewModel = viewModel)
}


@Composable
fun CalendarContent(viewModel: CalenderViewModel) {
    var isTimePickerStartVisible by remember { mutableStateOf(false) }
    var isTimePickerFinishVisible by remember { mutableStateOf(false) }
    var selectedStartTime by remember { mutableStateOf(viewModel.getFormattedTimeStart()) }
    var selectedFinishTime by remember { mutableStateOf(viewModel.getFormattedTimeFinish()) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(all = 8.dp)
    ) {
        InputTextCalendar(
            "Calendar Title",
            Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        )

        InputTextFieldCalender(viewModel.calenderTitle) {
            viewModel.onCalendarTitle(it)
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputTextCalendar(
                stringResource(R.string.start_date_label),
                Modifier.weight(1.5f)
            )
            InputTextCalendar(
                stringResource(R.string.start_time_label),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DateStartDate(
                modifier = Modifier.weight(1.5f),
                viewModel = viewModel
            )

            InputTextFieldTimer(
                selectedFinishTime = selectedStartTime,
                isTimePickerFinishVisible = { isTimePickerStartVisible = true },
                value = isTimePickerStartVisible,
                modifier = Modifier.weight(1f)
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputTextCalendar(
                stringResource(R.string.end_date_label),
                modifier = Modifier.weight(1.5f)
            )
            InputTextCalendar(
                stringResource(R.string.end_time_label),
                Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DateFinishDate(
                modifier = Modifier.weight(1.5f),
                viewModel = viewModel
            )

            InputTextFieldTimer(
                selectedFinishTime = selectedFinishTime,
                isTimePickerFinishVisible = { isTimePickerFinishVisible = true },
                value = isTimePickerFinishVisible,
                modifier = Modifier.weight(1f)
            )
        }


        InputTextCalendar(
            text = stringResource(R.string.location_label),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        )

        InputTextFieldCalender(viewModel.location) {
            viewModel.onLocation(it)
        }


        InputTextCalendar(
            text = stringResource(R.string.explanation_label),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        )

        InputTextFieldCalender(viewModel.explanation) {
            viewModel.onExplanation(it)
        }

        Spacer(Modifier.weight(1f))


        CalenderCreateButton(
            text = stringResource(R.string.saved),
            enabled = viewModel.isCreateButtonEnabled(),
            onCalenderClick = {
                viewModel.insertCalender()
            }
        )
    }


    if (isTimePickerStartVisible) {
        TimePickerStart(
            viewModel = viewModel,
            onConfirm = {
                selectedStartTime = viewModel.getFormattedTimeStart()
                isTimePickerStartVisible = false
            },
            onDismiss = {
                isTimePickerStartVisible = false
            }
        )
    }


    if (isTimePickerFinishVisible) {
        TimePickerFinish(
            viewModel = viewModel,
            onConfirm = {
                selectedFinishTime = viewModel.getFormattedTimeFinish()
                isTimePickerFinishVisible = false
            },
            onDismiss = {
                isTimePickerFinishVisible = false
            }
        )
    }
}


@Composable
fun InputTextCalendar(text: String, modifier: Modifier) {
    Text(
        text,
        modifier = modifier
    )
}

@Composable
fun CalenderCreateButton(
    text: String,
    enabled: Boolean,
    onCalenderClick: () -> Unit,
) {
    val context = LocalContext.current
    Button(
        onClick = {
            onCalenderClick.invoke()
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

@Composable
fun InputTextFieldCalender(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        shape = RoundedCornerShape(12),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    )
}

@Composable
fun InputTextFieldTimer(
    selectedFinishTime: String,
    isTimePickerFinishVisible: (Boolean) -> Unit,
    value: Boolean,
    modifier: Modifier
) {
    TextField(
        value = selectedFinishTime,
        onValueChange = { },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background
        ),
        readOnly = true,
        modifier = modifier,
        trailingIcon = {
            IconButton(onClick = { isTimePickerFinishVisible(value) }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Select time"
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateStartDate(
    modifier: Modifier = Modifier,
    viewModel: CalenderViewModel,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    viewModel.onSelectedStartDate(datePickerState.selectedDateMillis ?: 0)

    val selectedDate = datePickerState.selectedDateMillis?.let {
        viewModel.convertMillisToDate(it)
    } ?: ""

    Row(modifier = modifier) {
        TextField(
            value = selectedDate,
            onValueChange = {},
            placeholder = { Text(viewModel.getCurrentFormattedDate()) },
            readOnly = true,
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { selectedMillis ->
                            viewModel.onSelectedStartDate(selectedMillis)
                        }
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFinishDate(
    modifier: Modifier = Modifier,
    viewModel: CalenderViewModel,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    viewModel.onSelectedFinishDate(datePickerState.selectedDateMillis ?: 0)

    val selectedDate = datePickerState.selectedDateMillis?.let {
        viewModel.convertMillisToDate(it)
    } ?: ""

    Row(modifier = modifier) {
        TextField(
            value = selectedDate,
            onValueChange = {},
            placeholder = { Text(viewModel.getCurrentFormattedDate()) },
            readOnly = true,
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            viewModel.onSelectedFinishDate(it)
                        }
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerStart(
    viewModel: CalenderViewModel,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = viewModel.selectedStartHour,
        initialMinute = viewModel.selectedStartMinute,
        is24Hour = true,
    )

    AdvancedTimePickerDialog(
        onDismiss = onDismiss,
        onConfirm = {
            viewModel.updateStartTime(
                timePickerState.hour,
                timePickerState.minute
            )
            onConfirm()
        }
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerFinish(
    viewModel: CalenderViewModel,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = viewModel.selectedFinishHour,
        initialMinute = viewModel.selectedFinishMinute,
        is24Hour = true,
    )

    AdvancedTimePickerDialog(
        onDismiss = onDismiss,
        onConfirm = {
            viewModel.updateFinishTime(
                timePickerState.hour,
                timePickerState.minute
            )
            onConfirm()
        }
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}

@Composable
fun AdvancedTimePickerDialog(
    title: String = "Select Time",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    TextButton(onClick = onConfirm) { Text("OK") }
                }
            }
        }
    }
}