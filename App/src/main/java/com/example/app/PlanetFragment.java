package com.example.app;

/**
 * Created by dan on 12/5/13.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PlanetFragment extends Fragment {
    ArrayList<Card> cards = new ArrayList<Card>();
    ImageAdapter adapter;
    ListView list;
    int selectedItem;

    public PlanetFragment() {
        selectedItem = -1;
        cards.add(new Card("test 1"));
        cards.add(new Card("test 2"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);

        assert rootView != null;
        list = (ListView) rootView.findViewById(R.id.gridview);
        adapter = new ImageAdapter(rootView.getContext(), cards);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedItem == position) {
                    list.clearChoices();
                    list.requestLayout();
                    selectedItem = -1;
                } else {
                    selectedItem = position;
                }
            }
        });

        return rootView;
    }

    public void addCard(Card card) {
        cards.add(card);
        adapter.notifyDataSetChanged();
    }

    public void removeCard() {
        cards.remove(selectedItem);
        adapter.notifyDataSetChanged();
    }
}