package com.plcoding.tracker_data.remote.dto

import com.squareup.moshi.Json


data class SearchDto(
    @field:Json(name = "products") val response: List<Product>
)




