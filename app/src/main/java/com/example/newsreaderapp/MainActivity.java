package com.example.newsreaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdaptor.CategoryClickInterface {

    // GET https://newsapi.org/v2/everything?q=Apple&from=2021-12-25&sortBy=popularity&apiKey=API_KEY

    //bb9ba4b20f684786808600456d6349f5

    // https://newsapi.org/v2/top-headlines/sources?category=business&apiKey=bb9ba4b20f684786808600456d6349f5

    // country
    //  https://newsapi.org/v2/top-headlines?country=us&apiKey=bb9ba4b20f684786808600456d6349f5
    // all
    // https://newsapi.org/v2/everything?q=bitcoin&apiKey=bb9ba4b20f684786808600456d6349f5

    private RecyclerView newsRv, categoryRv;
    private ProgressBar idPBLoading;
    private ArrayList<News> newsArrayList;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdaptor categoryRVAdaptor;
    private NewsRVAdaptor newsRVAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRv = findViewById(R.id.idRVNews);
        categoryRv = findViewById(R.id.idRVCategories);
        idPBLoading = findViewById(R.id.idPBLoading);

        newsArrayList = new ArrayList<>();
        categoryRVModelArrayList = new ArrayList<>();

        newsRVAdaptor = new NewsRVAdaptor(newsArrayList, this);
        categoryRVAdaptor = new CategoryRVAdaptor(categoryRVModelArrayList, this, this::onCategoryClick);
        newsRv.setLayoutManager(new LinearLayoutManager(this));
        newsRv.setAdapter(newsRVAdaptor);
        categoryRv.setAdapter(categoryRVAdaptor);

        getCategoryList();
        getNews("All");
        newsRVAdaptor.notifyDataSetChanged();


    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getNews(category);
    }

    private void getCategoryList(){
        categoryRVModelArrayList.add(new CategoryRVModel("All", "https://images.unsplash.com/photo-1547989453-11e67ffb3885?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2232&q=80"));

        categoryRVModelArrayList.add(new CategoryRVModel("Technology", "https://images.unsplash.com/photo-1485827404703-89b55fcc595e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("Science", "https://images.unsplash.com/photo-1507413245164-6160d8298b31?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("Sports", "https://images.unsplash.com/photo-1547347298-4074fc3086f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("General", "https://images.unsplash.com/photo-1457369804613-52c61a468e7d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("Business", "https://media.istockphoto.com/photos/meeting-with-managers-picture-id479081933"));
        categoryRVModelArrayList.add(new CategoryRVModel("Entertainment", "https://media.istockphoto.com/photos/the-best-fans-a-band-could-want-picture-id502131959"));
        categoryRVModelArrayList.add(new CategoryRVModel("Health", "https://images.unsplash.com/photo-1506126613408-eca07ce68773?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1099&q=80"));
        categoryRVAdaptor.notifyDataSetChanged();
    }

    private void getNews(String category){
        idPBLoading.setVisibility(View.VISIBLE);
        newsArrayList.clear();

        String categoryUrl = "https://newsapi.org/v2/top-headlines?country=us&category=" + category + "&apiKey=bb9ba4b20f684786808600456d6349f5";

        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=bb9ba4b20f684786808600456d6349f5";

        String BASE_URL = "https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;


        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);

        }else{
            call = retrofitAPI.getNewsByCategory(categoryUrl);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();

                idPBLoading.setVisibility(View.GONE);
                ArrayList<News> news = newsModel.getNews();
                for (int i=0; i< news.size(); i++){

                    newsArrayList.add(new News(news.get(i).getTitle(),news.get(i).getDescription(),news.get(i).getUrlToImage(),news.get(i).getUrl(),news.get(i).getContent() ));
                }

                newsRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get News", Toast.LENGTH_SHORT).show();
            }
        });
    }
}