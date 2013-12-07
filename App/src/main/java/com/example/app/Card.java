package com.example.app;

/**
 * Created by dan on 12/6/13.
 */
public class Card {
    private String back;
    private String title;

    public Card(String title, String back) {
        this.title = title;
        this.back = back;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
