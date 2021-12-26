package com.example.newsreaderapp;

import java.util.ArrayList;

public class NewsModel {

    private int totalResults;
    private String status;
    ArrayList<News> articles;

    public NewsModel(int totalResults, String status, ArrayList<News> articles) {
        this.totalResults = totalResults;
        this.status = status;
        this.articles = articles;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<News> getNews() {
        return articles;
    }

    public void setNews(ArrayList<News> news) {
        this.articles = news;
    }
}
