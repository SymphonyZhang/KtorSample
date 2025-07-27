package com.francis.ktorsample.domain

import com.francis.ktorsample.domain.entity.Banner
import com.francis.ktorsample.domain.entity.Collect
import com.francis.ktorsample.domain.entity.Product

interface ProductRepository {
    suspend fun getProducts():List<Product>

    suspend fun login(username:String,password:String): Boolean

    suspend fun getBanners():List<Banner>

    suspend fun getCollect(pageIndex:Int):List<Collect>
}