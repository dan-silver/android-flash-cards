package com.example.app;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by dan on 12/7/13.
 */
public class SwitchSetFragment extends ListFragment{

    MainActivity parent;
    ArrayAdapter<String> adapter;

    public SwitchSetFragment() {
        //required constructor
    }
    public SwitchSetFragment(MainActivity parent) {
        this.parent = parent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, getSetNames());
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setSelector(R.drawable.selection_effect);
        getListView().setItemChecked(parent.getCurrentSet(), true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        parent.setCurrentSet(position);
    }

    private String[] getSetNames() {
        Log.v("silver", ("fetching names"));
        ArrayList<Set> sets = parent.getCardSets();
        String[] names = new String[sets.size()];
        for (int i=0;i<sets.size();i++) {
            names[i] = sets.get(i).getName();
        }
        return names;
    }

    public void addSet() {
        final EditText input = new EditText(parent);
        input.setHint("Set name");
        new AlertDialog.Builder(parent)
                .setTitle("Create Set")
                .setView(input)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String setTitle = input.getText().toString();
                        parent.getCardSets().add(new Set(setTitle));
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        })
                .show();
    }
}