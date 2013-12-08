package com.example.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dan on 12/7/13.
 */
public class SwitchSetFragment extends Fragment {
    MainActivity parent;
    public SwitchSetFragment() {
        //required constructor
    }
    public SwitchSetFragment(MainActivity parent) {
        this.parent = parent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.switch_set, container, false);
        //do stuff with view
        return view;
    }
}
