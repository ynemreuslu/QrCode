package com.ynemreuslu.instantqr.model


import com.ynemreuslu.instantqr.data.entities.TextEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Text(
    val id: Long = 0,
    val text: String,
    val timestamp: Long
)

fun TextEntity.asTextModel() = Text(id, text,timestamp)

fun Text.asTextEntity() = TextEntity(id, text,timestamp)

fun Flow<List<TextEntity>>.mapToTextFlow(): Flow<List<Text>> {
    return this.map { textList ->
        textList.map { it.asTextModel() }
    }
}