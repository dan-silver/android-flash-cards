package com.example.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dan on 12/7/13.
 */
public class LearnFragment extends Fragment {
    static ArrayList<Card> cards;
    int position = 0;
    View view = null;
    MainActivity parent;
    public LearnFragment() {
        //required constructor
    }
    public LearnFragment(MainActivity parent) {
        cards = parent.cards;
        this.parent = parent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
           // cards = parent.cards;
        }

        view = inflater.inflate(R.layout.learn, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    TextView front = (TextView) v.findViewById(R.id.front_text);
                    TextView back = (TextView) v.findViewById(R.id.back_text);
                    if (front.getVisibility() == View.GONE) {
                        front.setVisibility(View.VISIBLE);
                        back.setVisibility(View.GONE);
                    } else {
                        front.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
        displayCard(true);
        return view;
    }

    private void displayCard(Boolean displayFront) {
        Card card = cards.get(position);
        TextView front = (TextView) view.findViewById(R.id.front_text);
        TextView back = (TextView) view.findViewById(R.id.back_text);
        front.setText(card.getFront());
        back.setText(card.getBack());
        if (displayFront) {
            front.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
        } else {
            front.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
        }

        TextView progress = (TextView) view.findViewById(R.id.progress);
        progress.setText("Card " + (position + 1) + " of " + cards.size());
    }

    public void nextCard() {
        if (position == cards.size() - 1) {
            position = 0;
        } else {
            position++;
        }
        displayCard(true);
    }
    public void previousCard() {
        if (position == 0) {
            position = cards.size() - 1;
        } else {
            position--;
        }
        displayCard(true);
    }

}