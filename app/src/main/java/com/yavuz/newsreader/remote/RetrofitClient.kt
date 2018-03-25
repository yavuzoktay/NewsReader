package com.yavuz.newsreader.remote

import com.yavuz.newsreader.common.Common
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Yavuz on 19.3.2018.
 */
object RetrofitClient {
    private var retrofit:Retrofit?=null

    fun getClient(baseUrl:String?):Retrofit{
        if (retrofit==null){
            retrofit=Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }

}