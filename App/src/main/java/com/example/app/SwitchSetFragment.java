package com.example.app;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;


/**
 * Created by dan on 12/7/13.
 */
public class SwitchSetFragment extends ListFragment{

    static MainActivity parent;
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
        adapter = new SetListAdapter(parent, parent.getCardSets());
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setSelector(R.drawable.selection_effect);
        refreshUI();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        parent.setCurrentSet(position);
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
                        parent.setCurrentSet(parent.getCardSets().size()-1);
                        refreshUI();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        })
                .show();
    }

    public void refreshUI() {
        adapter.notifyDataSetChanged();
        getListView().setItemChecked(parent.getCurrentSet(), true);
    }

    public void removeSet() {
        parent.getCardSets().remove(parent.getCurrentSet());
        parent.setCurrentSet(0);
        refreshUI();
    }
}