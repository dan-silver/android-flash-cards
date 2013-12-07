package com.example.app;

/**
 * Created by dan on 12/6/13.
 */
public class Card {
    private String back;
    private String front;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;

    public Card(String front, String back) {
        this.front = front;
        this.back = back;
    }
    public Card() {

    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }
}
