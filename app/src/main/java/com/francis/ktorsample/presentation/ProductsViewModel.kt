package com.francis.ktorsample.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francis.ktorsample.data.network.HttpClientFactory
import com.francis.ktorsample.data.KtorProductRepository
import com.francis.ktorsample.domain.entity.Product
import com.francis.ktorsample.domain.ProductRepository
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val repository: ProductRepository = KtorProductRepository(
        HttpClientFactory.create(OkHttp.create())
    )
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())

    val products = _products
        .onStart {
            _products.value = repository.getProducts()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _products.value,
        )

    fun login(){
        viewModelScope.launch {
            repository.login("a1a1a3","a1a1a1")
        }
    }

    fun getCollects(){
        viewModelScope.launch {
            val collects = repository.getCollect(0)
            collects.forEachIndexed { index,it ->
                Log.d("zyx", "getCollects: index = $index , collect => $it")
            }
        }
    }

    fun getBanners(){
        viewModelScope.launch {
            val banners = repository.getBanners()
            banners.forEachIndexed { index,it ->
                Log.d("zyx", "getBanners: index = $index , banners => $it")
            }
        }
    }
}