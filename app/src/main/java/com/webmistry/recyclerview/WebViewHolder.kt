package com.webmistry.recyclerview

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.webmistry.callback.ItemClick
import com.webmistry.database.Browser
import com.webmistry.databinding.WebItemBinding

class WebViewHolder(private val binding: WebItemBinding,private val  itemClick: ItemClick) :ViewHolder(binding.root){


    fun bindData(browser: Browser){
        binding.webIconImage.setImageResource(browser.webImage)
        binding.webName.text=browser.webName
        binding.webName.setOnClickListener {
            itemClick.ItemClick(browser)
        }

        binding.webIconImage.setOnClickListener {
            itemClick.ItemClick(browser)
        }
    }
}