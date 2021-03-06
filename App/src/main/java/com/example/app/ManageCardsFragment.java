package com.example.app;

/**
 * Created by dan on 12/5/13.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ManageCardsFragment extends Fragment {
    ImageAdapter adapter;
    ListView list;
    int selectedItem; //-1 for no selected cards
    static MainActivity parent;

    public ManageCardsFragment(MainActivity parent) {
        selectedItem = -1;
        this.parent = parent;
    }

    public ManageCardsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);

        assert rootView != null;
        list = (ListView) rootView.findViewById(R.id.gridview);
        adapter = new ImageAdapter(rootView.getContext(), parent.getCards());
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                if (selectedItem == position) {
                    list.clearChoices();
                    list.requestLayout();
                    selectedItem = -1;
                } else {
                    selectedItem = position;
                }
                parent.refreshMenu();
            }
        });
        return rootView;
    }

    public void removeCard() {
        parent.removeCard(selectedItem);
        resetUI();
    }
    public void resetUI() {
        adapter.notifyDataSetChanged();
        selectedItem = -1;
        parent.refreshMenu();
    }

    public void editCurrentCard() {
        LayoutInflater factory = LayoutInflater.from(parent);
        final View textEntryView = factory.inflate(R.layout.card_input, null);
        final EditText front = (EditText) textEntryView.findViewById(R.id.front);
        final EditText back = (EditText) textEntryView.findViewById(R.id.back);
        front.setText(parent.getCards().get(selectedItem).getFront());
        back.setText(parent.getCards().get(selectedItem).getBack());
        new AlertDialog.Builder(parent)
                .setTitle("Edit Study Card")
                .setView(textEntryView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String cardTitle = front.getText().toString();
                        String cardBack = back.getText().toString();
                        parent.updateCard(selectedItem, cardTitle, cardBack);
                        resetUI();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    })
                .show();

    }

    public void addCard() {
        LayoutInflater factory = LayoutInflater.from(parent);
        final View textEntryView = factory.inflate(R.layout.card_input, null);
        final EditText front = (EditText) textEntryView.findViewById(R.id.front);
        final EditText back = (EditText) textEntryView.findViewById(R.id.back);

        new AlertDialog.Builder(parent)
                .setTitle("Create Study Card")
                .setView(textEntryView)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String frontStr = front.getText().toString();
                        String backStr = back.getText().toString();
                        if (!frontStr.equals("")) {
                            parent.addCard(new Card(frontStr, backStr));
                            resetUI();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        })
                .show();
    }
}