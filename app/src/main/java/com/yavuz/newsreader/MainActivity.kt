package com.yavuz.newsreader

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.Gson
import com.yavuz.newsreader.`interface`.NewsService
import com.yavuz.newsreader.adapter.ListSourceAdapter
import com.yavuz.newsreader.common.Common
import com.yavuz.newsreader.model.Website
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Paper.init(this)
        mService=Common.newsService
        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteSource(true)
        }
        recycler_view_source_news.setHasFixedSize(true)
        layoutManager= LinearLayoutManager(this)
        recycler_view_source_news.layoutManager=layoutManager

        dialog= SpotsDialog(this)
        loadWebSiteSource(false)


    }

    private fun loadWebSiteSource(isRefresh: Boolean) {
        if (!isRefresh){
            val cache = Paper.book().read<String>("cache")
            if (cache!=null && !cache.isBlank() && cache!="null"){
                  val webSite = Gson().fromJson<Website>(cache,Website::class.java)
                  adapter= ListSourceAdapter(baseContext,webSite)
                  adapter.notifyDataSetChanged()
                  recycler_view_source_news.adapter = adapter
            }
            else {
                dialog.show()
                mService.sources.enqueue(object : retrofit2.Callback<Website> {
                    override fun onFailure(call: Call<Website>?, t: Throwable?) {
                        Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<Website>?, response: Response<Website>?) {
                        adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter=adapter

                        Paper.book().write("cache",Gson().toJson(response!!.body()!!))
                        dialog.dismiss()

                    }
                })
            }
        }
        else
        {
            swipe_to_refresh.isRefreshing=true

            mService.sources.enqueue(object : retrofit2.Callback<Website> {

                override fun onResponse(call: Call<Website>?, response: Response<Website>?) {
                    adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter=adapter

                    Paper.book().write("cache",Gson().toJson(response!!.body()!!))
                    swipe_to_refresh.isRefreshing=false

                }
                override fun onFailure(call: Call<Website>?, t: Throwable?) {
                    Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
