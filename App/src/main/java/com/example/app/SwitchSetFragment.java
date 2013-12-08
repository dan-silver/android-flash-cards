package com.example.app;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by dan on 12/7/13.
 */
public class SwitchSetFragment extends ListFragment {

    MainActivity parent;

    public SwitchSetFragment() {
        //required constructor
    }
    public SwitchSetFragment(MainActivity parent) {
        this.parent = parent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        String[] names = new String[getSets().size()];
        for (int i=0;i<getSets().size();i++) {
            names[i] = getSets().get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, names);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setSelector(R.drawable.selection_effect);

        getListView().setItemChecked(parent.getCurrentSet(), true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        parent.setCurrentSet(position);
    }

    private ArrayList<Set> getSets() {
        return parent.cardSets;
    }
}