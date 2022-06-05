package com.example.finalproject;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Content{
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "quote")
    private String quote;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "image")
    private String img;

    public Content() {}
    public Content(String q, String a, String d, String i) {
        this.quote = q;
        this.author = a;
        this.date = d;
        this.img = i;
    }

    public int getId() { return id; }
    public String getQuote() { return quote; }
    public String getAuthor() { return author; }
    public String getDate() { return date; }
    public String getImg() { return img; }

    public void setId() {}
    public void setQuote(String q) { this.quote = q; }
    public void setAuthor(String a) { this.author = a; }
    public void setDate(String d) { this.date = d; }
    public void setImg(String i) { this.img = i; }
}
