package com.ynemreuslu.instantqr.ui.history.content.card

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


@Composable
fun SwipeToDismissListItem(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        backgroundContent = {
            val animatedColor = animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.primary
                    SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primary
                },
                label = ""
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = animatedColor.value, shape = RectangleShape)
            ) {
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.StartToEnd -> {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp)
                        )
                    }
                    SwipeToDismissBoxValue.EndToStart -> {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 16.dp)
                        )
                    }
                    SwipeToDismissBoxValue.Settled -> {
                        // Nothing to do
                    }
                }
            }
        }
    ) {
        content()
    }

    when (dismissState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            LaunchedEffect(dismissState.currentValue) {
                onDelete.invoke()
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }
        SwipeToDismissBoxValue.StartToEnd -> {
            LaunchedEffect(dismissState.currentValue) {
                onDelete.invoke()
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }
        SwipeToDismissBoxValue.Settled -> {
            // Nothing to do
        }
    }
}
