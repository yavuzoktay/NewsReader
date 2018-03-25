package com.yavuz.newsreader.common

import com.yavuz.newsreader.`interface`.NewsService
import com.yavuz.newsreader.remote.RetrofitClient

/**
 * Created by Yavuz on 19.3.2018.
 */
object Common{
    val BASE_URL="https://newsapi.org/"
    val API_KEY="0eb5a3e4b24b4cddb101e534b5ca2187"

    val newsService:NewsService
    get()=RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun newsAPI(source:String):String{
        val apiUrl=StringBuilder("https://newsapi.org/v2/top-headlines?sources")
                .append(source)
                .append("&apiKey=")
                .append(API_KEY)
                .toString()
        return apiUrl
    }

}