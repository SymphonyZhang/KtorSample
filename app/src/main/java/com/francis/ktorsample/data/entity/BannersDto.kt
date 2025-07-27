package com.francis.ktorsample.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class BannersDto(
    val data:List<BannerDto>
)
