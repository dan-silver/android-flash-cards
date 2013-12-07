package com.example.app;

/**
 * Created by dan on 12/5/13.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class PlanetFragment extends Fragment {
    public ArrayList<Card> cards = new ArrayList<Card>();
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public PlanetFragment() {
        // Empty constructor required for fragment subclasses
        cards.add(new Card("test 1"));
        cards.add(new Card("test 2"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);

        ListView list = (ListView) rootView.findViewById(R.id.gridview);
        list.setAdapter(new ImageAdapter(rootView.getContext(), cards));

        return rootView;
    }
}