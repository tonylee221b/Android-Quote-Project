package com.example.finalproject;

import android.app.Application;

public class MyApp extends Application {
    private NetworkingService networkingService = new NetworkingService();
    private JsonService jsonService = new JsonService();
    private ContentDBService dbService = new ContentDBService();
    private ContentManager mainContentManager = new ContentManager();

    public JsonService getJsonService() {
        return jsonService;
    }
    public NetworkingService getNetworkingService() {
        return networkingService;
    }
    public ContentDBService getDBService() { return dbService; }
    public ContentManager getContentManager() { return mainContentManager; }

    Content mainData = new Content();
}
