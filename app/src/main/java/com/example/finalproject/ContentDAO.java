package com.example.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContentDAO {
    @Insert
    void insertNewContent(Content c);

    @Delete
    void deleteContent(Content c);

    @Query("DELETE from Content")
    void deleteAll();

    @Query("Select * from Content where id = :id")
    Content getContentById(int id);

    @Query("Select * from Content")
    List<Content> getAllContents();
}
