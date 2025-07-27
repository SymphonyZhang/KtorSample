package com.francis.ktorsample.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserDataDto(
    val data: UserInfoDto
)
