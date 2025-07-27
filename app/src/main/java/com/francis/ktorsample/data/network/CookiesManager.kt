package com.francis.ktorsample.data.network

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.francis.ktorsample.util.AppDataStore
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.client.plugins.cookies.fillDefaults
import io.ktor.client.plugins.cookies.matches
import io.ktor.http.Cookie
import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/**
 * 自定义 Cookie 保存
 */
class CookiesManager() : CookiesStorage {

    // 互斥锁确保多线程安全
    private val mutex = Mutex()

    //从DataStore中获取保存的Cookies信息
    private suspend fun loadCookies(key: String): MutableList<Cookie> {
        // 通过DataStore获取到cookie
        val cookiesString = AppDataStore.dataStore.data
            .map { prefs ->
                prefs[stringPreferencesKey(key)] ?: ""
            }.first()

        return if (cookiesString.isNotEmpty()) {
            //把通过String格式保存的cookie重新转换为Cookie类
            cookiesString.split(";").map {
                parseCookieFromString(it)
            }.toMutableList()
        } else mutableListOf()
    }

    //真正做保存的地方
    private suspend fun saveCookies(key: String, cookies: List<Cookie>) {
        // 把cookie拼接起来   name|value;name|value  的形式
        val serialized = cookies.joinToString(";") {
            cookieToString(it)
        }
        // 通过datastore保存
        AppDataStore.dataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = serialized
        }
    }

    private fun cookieToString(cookie: Cookie): String {
        // 至少需要下面4个字段，如果有期限限制的也要加上期限限制用于判断cookie是否过期了
        return listOf(
            cookie.name,
            cookie.value,
            cookie.domain,
            cookie.path
        ).joinToString("|")
    }

    private fun parseCookieFromString(str: String): Cookie {
        val parts = str.split("|")
        return Cookie(parts[0], parts[1], domain = parts[2], path = parts[3])
    }

    //获取匹配指定URL的Cookie(核心方法)
    override suspend fun get(requestUrl: Url): List<Cookie> = mutex.withLock {
        val cookies = loadCookies(requestUrl.host)
        return@withLock withContext(Dispatchers.Default) {
            cookies.filter { it.matches(requestUrl) }
        }

    }

    // 添加/更新 Cookie (核心方法)
    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        Log.d("zyx", "addCookie: ==> cookie==> $cookie")
        // 跳过无效Cookie
        with(cookie) {
            if (name.isBlank()) return
        }
        mutex.withLock {
            // 根据url获取当前已经存下的cookies
            val cookies = loadCookies(requestUrl.host)
            // 判断是否已经存在这个cookie name
            val existingIndex = cookies.indexOfFirst {
                it.name == cookie.name
            }
            if (existingIndex >= 0) {// 如果存在就替换掉value
                cookies[existingIndex] = cookie.fillDefaults(requestUrl)
            } else {// 如果不存在就新增到这个url对应的cookies中
                cookies.add(cookie.fillDefaults(requestUrl))
            }
            saveCookies(requestUrl.host, cookies)
        }
    }

    //关闭存储(清理资源)
    override fun close() {
        // DataStore 会自动管理，这里可留空
    }
}