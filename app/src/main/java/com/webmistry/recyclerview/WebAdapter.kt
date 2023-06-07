package com.webmistry.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.webmistry.callback.ItemClick
import com.webmistry.database.Browser
import com.webmistry.databinding.WebItemBinding
import com.webmistry.fragment.MainActivity

class WebAdapter(private val list: ArrayList<Browser>, private val mainActivity: MainActivity):Adapter<WebViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebViewHolder {
        return WebViewHolder(WebItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),mainActivity as ItemClick)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: WebViewHolder, position: Int) {
       holder.bindData(list[position])
    }
}