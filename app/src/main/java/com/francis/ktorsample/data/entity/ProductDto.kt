package com.francis.ktorsample.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val description: String
)
