package com.example.bootesnullassignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bootesnullassignment.databinding.AdapterNewsBinding
import com.example.bootesnullassignment.fragments.FirstFragmentDirections
import com.example.bootesnullassignment.models.News
import com.example.bootesnullassignment.models.Result
import com.squareup.picasso.Picasso

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    var newslist = mutableListOf<Result>()

    fun setNewsList(news: List<Result>){
        newslist = news.toMutableList()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: AdapterNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterNewsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var news = newslist[position]
        holder.binding.tvTitle.text = news.title
        holder.binding.tvDate.text = news.pubDate
        holder.binding.tvSource.text = news.source_id
        val image = news.image_url ?: ""
        if (image != "")
            Picasso.get().load(image).placeholder(R.drawable.placeholder_news)
                .into(holder.binding.ivThumbnail)

        holder.binding.root.setOnClickListener {

            val action = FirstFragmentDirections
                .actionFirstFragmentToSecondFragment(image = image, title = news.title, description = news.description,
                date = news.pubDate, source = news.source_id)
            var navController: NavController = Navigation.findNavController(holder.binding.root)
            navController.navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return newslist.size
    }
}