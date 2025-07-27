package com.francis.ktorsample.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CollectsDto(
    val curPage:Int,
    val datas:List<CollectDto>,
    val pageCount:Int,
    val total:Int,
)