package com.francis.ktorsample.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CollectDto(
    val author:String,
    val title:String,
    val link:String
)
