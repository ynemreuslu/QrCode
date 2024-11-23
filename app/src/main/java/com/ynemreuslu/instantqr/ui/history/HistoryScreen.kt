package com.ynemreuslu.instantqr.ui.history

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ynemreuslu.instantqr.R
import com.ynemreuslu.instantqr.ui.history.content.creator.CreatorContent
import com.ynemreuslu.instantqr.ui.history.content.history.HistoryContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    Column {
        TabScreen(
            selectedIndex = uiState.selectedTab,
            onTabSelected = viewModel::onTabSelected,
            historyContent = stringResource(id = R.string.history_content),
            creatorContent = stringResource(id = R.string.creator_content)
        )
        when (uiState.selectedTab) {
            0 ->  HistoryContent()
            1 ->  CreatorContent()
        }
    }
}

@Composable
fun TabScreen(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    historyContent: String,
    creatorContent: String
) {
    val tabs = listOf(historyContent, creatorContent)

    TextSwitch(
        selectedIndex = selectedIndex,
        items = tabs,
        onSelectionChange = onTabSelected
    )
}

@Composable
fun TextSwitch(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    onSelectionChange: (Int) -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    BoxWithConstraints(
        modifier
            .padding(8.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        if (items.isNotEmpty()) {
            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
                label = "indicator offset"
            )

            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .width(tabWidth)
                    .fillMaxHeight()
            )

            Row(modifier = Modifier.fillMaxWidth().drawWithContent {
                val padding = 8.dp.toPx()
                drawRoundRect(
                    topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                    size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                    color = Color.Black,
                    cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                )

                drawWithLayer {
                    drawContent()
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                        size = Size(size.width / 2, size.height),
                        color = backgroundColor,
                        cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                        blendMode = BlendMode.SrcOut
                    )
                }
            }) {
                items.forEachIndexed { index, text ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    onSelectionChange(index)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            fontSize = 20.sp,
                        )
                    }
                }
            }
        }
    }
}

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}