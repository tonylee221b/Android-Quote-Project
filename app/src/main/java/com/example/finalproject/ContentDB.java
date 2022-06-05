package com.example.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Content.class})
abstract public class ContentDB extends RoomDatabase {
    public abstract ContentDAO getDao();
}
