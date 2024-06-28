package com.codelytical.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Note(
    @Json(name = "_id")
    val id: String,
    val title: String,
    val note: String,
    val created: Long,
    val isPinned: Boolean = false
)
