package com.ynemreuslu.instantqr.ui.history.content.creator

data class CreatorContentUiState(
    val items: List<CreatorItem> = emptyList(),
    val isLoading: Boolean = false
)