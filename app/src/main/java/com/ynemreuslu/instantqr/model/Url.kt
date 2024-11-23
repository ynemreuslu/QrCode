package com.ynemreuslu.instantqr.model


import com.ynemreuslu.instantqr.data.entities.UrlEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Url(
    val id: Int = 0,
    val url: String,
    val timestamp: Long
)

fun UrlEntity.asUrlModel() = Url(id, url,timestamp)

fun Url.asUrlEntityModel() = UrlEntity(id, url,timestamp)

fun Flow<List<UrlEntity>>.mapToUrlFlow(): Flow<List<Url>> {
    return this.map { urlList ->
        urlList.map { it.asUrlModel() }
    }
}