package com.plcoding.tracker_data.remote.dto

import com.squareup.moshi.Json

data class Product(
    @field:Json(name = "image_front_thumb_url")
    val photo: String?,
    val nutriments: Nutriments,
    @field:Json(name = "product_name")
    val name: String?
)