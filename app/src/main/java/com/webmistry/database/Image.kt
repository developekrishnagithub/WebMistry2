package com.webmistry.database

import android.net.Uri

data class Image(
    val uri: Uri?, val visibilityMode: Int, val imageErrorText: String,
    val textColor:Int
)