package com.example.app;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 12/7/13.
 */
public class SwitchSetFragment extends ListFragment {

    MainActivity parent;
    ArrayList<Set> sets = new ArrayList<Set>();

    public SwitchSetFragment() {
        //required constructor
    }
    public SwitchSetFragment(MainActivity parent) {
        this.parent = parent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        sets.add(new Set("Spanish Chapter 1"));
        sets.add(new Set("Spanish Chapter 2"));
        sets.add(new Set("Spanish Chapter 3"));
        sets.add(new Set("Spanish Chapter 4"));

        String[] names = new String[sets.size()];
        for (int i=0;i<sets.size();i++) {
            names[i] = sets.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, names);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setSelector(R.drawable.selection_effect);
        selectItem(0);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        selectItem(position);
    }
    private void selectItem(int p) {
        getListView().setSelection(p);
        getListView().setSelected(true);
    }
}
