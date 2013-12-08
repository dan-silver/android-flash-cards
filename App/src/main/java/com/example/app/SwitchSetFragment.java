package com.example.app;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by dan on 12/7/13.
 */
public class SwitchSetFragment extends ListFragment {

    MainActivity parent;
    SetListAdapter adapter;

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
        adapter = new SetListAdapter(this.parent, getSets());
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

    public void addSet() {
        final EditText input = new EditText(parent);
        input.setHint("Set name");
        new AlertDialog.Builder(parent)
                .setTitle("Create Set")
                .setView(input)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String setTitle = input.getText().toString();
                        getSets().add(new Set(setTitle));
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