package com.yavuz.newsreader.`interface`

import com.yavuz.newsreader.model.News
import com.yavuz.newsreader.model.Website
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Yavuz on 19.3.2018.
 */
interface NewsService{

    @get:GET("v2/sources?apiKey=0eb5a3e4b24b4cddb101e534b5ca2187")
    val sources: Call<Website>

    @GET
    fun getNewsFromSource(@Url url:String):Call<News>

}