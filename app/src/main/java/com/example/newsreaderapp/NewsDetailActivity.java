package com.example.newsreaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    String title, description, imageUrl, url, content;
    private TextView titleTv, subTitleTv, contentTv;
    private ImageView newsIv;
    private Button readNewsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        imageUrl = getIntent().getStringExtra("imageUrl");
        url = getIntent().getStringExtra("url");
        content = getIntent().getStringExtra("content");

        titleTv = findViewById(R.id.idTVTitle);
        subTitleTv = findViewById(R.id.idTVSubTitle);
        contentTv = findViewById(R.id.idTVSubDescription);
        newsIv = findViewById(R.id.idIvNews);

        readNewsBtn = findViewById(R.id.idBtnFullNews);
        titleTv.setText(title);
        subTitleTv.setText(description);
        contentTv.setText(content);
        Picasso.get().load(imageUrl).into(newsIv);

        readNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });


    }
}