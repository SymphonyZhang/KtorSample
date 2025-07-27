package com.francis.ktorsample.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class BannerDto(
    val title:String,
    val desc:String,
    val imagePath:String,
    val url:String
)
