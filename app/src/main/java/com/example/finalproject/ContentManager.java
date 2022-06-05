package com.example.finalproject;

import java.util.ArrayList;

public class ContentManager {
    private ArrayList<Content> allContentHistory = new ArrayList<>(0);

    public void ContentManager() {}
    public void setContentHistory(ArrayList<Content> c) {
        allContentHistory = c;
    };
    public Content getContentById(int id) {
        int ind = -1;

        for (int i = 0; i < allContentHistory.size(); ++i) {
            if (allContentHistory.get(i).getId() == id) {
                ind = i;
            }
        }

        return allContentHistory.get(ind);
    }
    public ArrayList<Content> getContentHistory() {
        return allContentHistory;
    }
}
