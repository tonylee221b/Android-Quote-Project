package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContentMenuActivity extends AppCompatActivity implements ContentMenuAdapter.AdapterListener, ContentDBService.DBInterface {
    RecyclerView chView;
    ContentMenuAdapter cma;
    ContentDBService dbService;
    ContentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Initialization
        dbService = ((MyApp)getApplication()).getDBService();

        manager = ((MyApp)getApplication()).getContentManager();
        chView = findViewById(R.id.content_menu_recyc);

        cma = new ContentMenuAdapter(this, manager.getContentHistory());
        cma.listener = this;

        dbService.getAllContents();

        chView.setLayoutManager(new LinearLayoutManager(this));
        chView.setAdapter(cma);
    }

    @Override
    public void ContentHistoryAdapterClickListener(Content ch) {
        Intent intent = new Intent(this, ContentDetailActivity.class);
        intent.putExtra("id", ch.getId());
        startActivity(intent);
    }

    @Override
    public void ContentInserted() {
        dbService.getAllContents();
    }

    @Override
    public void ContentDeleted() {
        dbService.getAllContents();
    }

    @Override
    public void allContentDeleted() {
        dbService.getAllContents();
    }

    @Override
    public void ContentById(Content ch) {}

    @Override
    public void listOfAllContents(List<Content> list) {
        cma = new ContentMenuAdapter(this, (ArrayList<Content>)list);
        cma.notifyDataSetChanged();
    }
}
