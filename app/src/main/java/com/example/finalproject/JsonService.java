package com.example.finalproject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {
    public Content getContentFromJSON(String quoteJson) {
        Content data = new Content();

        try {
            JSONObject jsonObj = new JSONObject(quoteJson);
            data.setQuote(jsonObj.getString("content"));
            data.setAuthor(jsonObj.getString("author"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
