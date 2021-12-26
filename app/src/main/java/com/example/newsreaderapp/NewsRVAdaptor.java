package com.example.newsreaderapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdaptor extends RecyclerView.Adapter<NewsRVAdaptor.ViewHolder> {
    private ArrayList<News> newsArrayList;
    private Context context;

    public NewsRVAdaptor(ArrayList<News> newsArrayList, Context context) {
        this.newsArrayList = newsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_items, parent, false);
        return new NewsRVAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdaptor.ViewHolder holder, int position) {
        News news = newsArrayList.get(position);
        holder.subTitleTv.setText(news.getDescription());
        holder.titleTv.setText(news.getTitle());
        Picasso.get().load(news.getUrlToImage()).into(holder.newsTv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, NewsDetailActivity.class);
                intent.putExtra("title", news.getTitle());
                intent.putExtra("content", news.getContent());
                intent.putExtra("description", news.getDescription());
                intent.putExtra("imageUrl", news.getUrlToImage());
                intent.putExtra("url", news.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTv, subTitleTv;
        private ImageView newsTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.idTVNewsHeading);
            subTitleTv = itemView.findViewById(R.id.idTVSubTitle);
            newsTv = itemView.findViewById(R.id.idIVNews);

        }
    }
}
