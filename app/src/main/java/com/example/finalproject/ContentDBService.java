package com.example.finalproject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContentDBService {
    interface DBInterface {
        void ContentInserted();
        void ContentDeleted();
        void allContentDeleted();
        void ContentById(Content ch);
        void listOfAllContents(List<Content> list);
    }

    static ContentDB db;
    DBInterface listener;
    ExecutorService dbExecutor = Executors.newFixedThreadPool(4);
    Handler dbHandler = new Handler(Looper.getMainLooper());

    public ContentDB getInstance(Context ctx) {
        if (db == null) {
            db = Room.databaseBuilder(ctx.getApplicationContext(), ContentDB.class, "content_db").build();
        }

        return db;
    }

    public void insertNewContent(Content c) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().insertNewContent(c);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.ContentInserted();
                    }
                });
            }
        });
    }

    public void deleteContent(Content c) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().deleteContent(c);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.ContentDeleted();
                    }
                });
            }
        });
    }

    public void deleteAll() {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().deleteAll();
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.allContentDeleted();
                    }
                });
            }
        });
    }

    public void getContentById(int id) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Content ch = db.getDao().getContentById(id);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.ContentById(ch);
                    }
                });
            }
        });
    }

    public void getAllContents() {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Content> list  = db.getDao().getAllContents();
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.listOfAllContents(list);
                    }
                });
            }
        });
    }
}
