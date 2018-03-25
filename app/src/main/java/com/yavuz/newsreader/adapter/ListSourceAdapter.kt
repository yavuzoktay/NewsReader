package com.yavuz.newsreader.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yavuz.newsreader.ListNews
import com.yavuz.newsreader.R
import com.yavuz.newsreader.`interface`.ItemClickListener
import com.yavuz.newsreader.adapter.viewHolder.ListSourceViewHolder
import com.yavuz.newsreader.model.Website

/**
 * Created by Yavuz on 19.3.2018.
 */
class ListSourceAdapter(private val context: Context, private val website: Website):RecyclerView.Adapter<ListSourceViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListSourceViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView=inflater.inflate(R.layout.source_news_layout,parent,false)
        return ListSourceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder?, position: Int) {
        holder!!.source_title.text = website.sources!![position].name
        holder.setItemClickListener(object : ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val intent=Intent(context,ListNews::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("source",website.sources!![position].id)
                context.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return website.sources!!.size

    }

}