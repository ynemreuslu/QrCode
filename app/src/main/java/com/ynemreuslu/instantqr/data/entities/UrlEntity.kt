package com.ynemreuslu.instantqr.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "url")
data class UrlEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String,
    val timestamp: Long,
)