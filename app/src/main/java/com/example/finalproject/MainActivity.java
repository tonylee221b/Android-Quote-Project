package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ContentDBService.DBInterface {

    FragmentManager fm = getSupportFragmentManager();

    Button saveBtn, nextBtn;

    Content data;
    ContentDBService dbService;
    ContentManager cm;
    ContentFrag cf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialization
        saveBtn = findViewById(R.id.save_btn);
        nextBtn = findViewById(R.id.next_btn);

        data = ((MyApp) getApplication()).mainData;
        cm = ((MyApp) getApplication()).getContentManager();
        dbService = ((MyApp) getApplication()).getDBService();
        dbService.listener = this;
        dbService.getInstance(this);
        dbService.getAllContents();

        // EventListeners
        saveBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        cf = (ContentFrag)fm.findFragmentById(R.id.frag_layout);
        generateFragment();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main);
        }

        saveBtn = findViewById(R.id.save_btn);
        nextBtn = findViewById(R.id.next_btn);

        // EventListeners
        saveBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        cf = (ContentFrag)fm.findFragmentById(R.id.frag_layout);
        generateFragment();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.save_btn:
                data.setDate(new Date().toString());
                addContentIntoDB();

                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

                generateFragment();
                break;
            case R.id.next_btn:
                generateFragment();
                break;
        }
    }

    // Menu Options
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_detail:
                Intent listIntent = new Intent(this, ContentMenuActivity.class);
                startActivity(listIntent);
                break;
            case R.id.menu_reset:
                dbService.deleteAll();
                Toast.makeText(this, "History has been reset", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    public void generateFragment() {
        if (cf == null) {
            cf = ContentFrag.newInstance();
            fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).add(R.id.frag_layout, cf).commit();
        } else {
            cf = ContentFrag.newInstance();
            fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.frag_layout, cf).commit();
        }
    }

    public void addContentIntoDB() {
        Content tmp = new Content(data.getQuote(), data.getAuthor(), data.getDate(), data.getImg());
        dbService.insertNewContent(tmp);
    }

    @Override
    public void ContentInserted() {
        dbService.getAllContents();
    }

    @Override
    public void ContentDeleted() {

    }

    @Override
    public void allContentDeleted() {
        dbService.getAllContents();
    }

    @Override
    public void ContentById(Content ch) {

    }

    @Override
    public void listOfAllContents(List<Content> list) {
        ArrayList<Content> tmp = (ArrayList<Content>)list;
        cm.setContentHistory(tmp);
    }
}