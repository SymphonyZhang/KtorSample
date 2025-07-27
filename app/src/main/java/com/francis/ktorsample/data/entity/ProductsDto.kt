package com.francis.ktorsample.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProductsDto(
    val products: List<ProductDto>
)
