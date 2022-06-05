package com.example.finalproject;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ContentDetailActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {

    ImageView img;
    TextView quote, author, date;
    ContentDBService dbService;
    ContentManager cm;
    Content content;
    NetworkingService networkService;
    int c_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialization
        img = findViewById(R.id.detail_img);
        quote = findViewById(R.id.detail_quote);
        author = findViewById(R.id.detail_author);
        date = findViewById(R.id.detail_date);

        networkService = ((MyApp)getApplication()).getNetworkingService();
        dbService = ((MyApp)getApplication()).getDBService();
        cm = ((MyApp)getApplication()).getContentManager();
        networkService.listener = this;

        // Get Intent Args
        c_id = getIntent().getExtras().getInt("id");
        content = cm.getContentById(c_id);

        quote.setText(content.getQuote());
        author.setText(getString(R.string.detail_author) + " " + content.getAuthor());
        date.setText(getString(R.string.detail_date) + " " + content.getDate());
        networkService.getImageSelected(content.getImg());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_detail);
        } else {
            setContentView(R.layout.activity_detail);
        }
    }

    @Override
    public void quoteListener(String json) {

    }

    @Override
    public void imgListener(Bitmap i, String imgURL) {
        img.setImageBitmap(i);
    }
}
