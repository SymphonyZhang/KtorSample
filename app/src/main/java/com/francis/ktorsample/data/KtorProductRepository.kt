package com.francis.ktorsample.data

import android.util.Log
import com.francis.ktorsample.data.entity.BannersDto
import com.francis.ktorsample.data.entity.CollectsInfoDto
import com.francis.ktorsample.data.entity.ProductsDto
import com.francis.ktorsample.data.entity.UserDataDto
import com.francis.ktorsample.domain.ProductRepository
import com.francis.ktorsample.domain.entity.Banner
import com.francis.ktorsample.domain.entity.Collect
import com.francis.ktorsample.domain.entity.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters

class KtorProductRepository(private val httpClient: HttpClient): ProductRepository {


    override suspend fun getProducts(): List<Product> {
        val response = try{
            httpClient.get("https://dummyjson.com/products")
        }catch (e: Exception){
            return emptyList()
        }

        if(response.status.value >= 400){
            return emptyList()
        }

        val body = try {
            response.body<ProductsDto>()
        }catch (e: Exception){
            return emptyList()
        }

        return body.products.map {
            Product(
                id = it.id,
                title = it.title,
                description = it.description
            )
        }
    }

    override suspend fun login(username: String, password: String): Boolean {
        val response = try {
            httpClient.post("https://www.wanandroid.com/user/login"){
                /*headers{
                    append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                }
                setBody(
                    Parameters.build {
                        append("username",username)
                        append("password",password)
                    }.formUrlEncode()
                )*/

                setBody(
                    FormDataContent(
                        Parameters.build{
                            append("username",username)
                            append("password",password)
                        }
                    )
                )
            }
        }catch (e: Exception){
            return  false
        }

        if(response.status.value >= 400){
            return false
        }

        val body = try {
            response.body<UserDataDto>()
        }catch (e: Exception){
            return false
        }
        return body.data.username == username
    }

    override suspend fun getBanners(): List<Banner> {
        val response = try{
            httpClient.get("https://www.wanandroid.com/banner/json")
        }catch (e: Exception){
            return emptyList()
        }

        if(response.status.value >= 400){
            return emptyList()
        }

        val body = try {
            response.body<BannersDto>()
        }catch (e: Exception){
            return emptyList()
        }

        return body.data.map {
            Banner(
                title = it.title,
                desc = it.desc,
                imagePath = it.imagePath,
                url = it.url
            )
        }
    }

    override suspend fun getCollect(pageIndex: Int): List<Collect> {
        val url = "https://www.wanandroid.com/lg/collect/list/$pageIndex/json"
        val response = try{
            httpClient.get(url)
        }catch (e: Exception){
            return emptyList()
        }

        if(response.status.value >= 400){
            return emptyList()
        }

        val body = try {
            response.body<CollectsInfoDto>()
        }catch (e: Exception){
            return emptyList()
        }

        Log.d("zyx", "getCollect: body body==================>  $body")

        return body.data.datas.map {
            Collect(
                author = it.author,
                title = it.title,
                link = it.link
            )
        }
    }


}