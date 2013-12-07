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


public class ManageCardsFragment extends Fragment {
    ImageAdapter adapter;
    ListView list;
    int selectedItem; //-1 for no selected cards
    final MainActivity parent;

    public ManageCardsFragment(MainActivity parent) {
        selectedItem = -1;
        this.parent = parent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);

        assert rootView != null;
        list = (ListView) rootView.findViewById(R.id.gridview);
        adapter = new ImageAdapter(rootView.getContext(), parent.cards);
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

    public void addCard(Card card) {
        parent.cards.add(card);
        resetUI();
    }

    public void removeCard() {
        parent.cards.remove(selectedItem);
        resetUI();
    }
    public void resetUI() {
        adapter.notifyDataSetChanged();
        selectedItem = -1;
        parent.refreshMenu();
    }

    public void editCurrentCard() {
        final EditText input = new EditText(parent);
        input.setText(parent.cards.get(selectedItem).getTitle());
        new AlertDialog.Builder(parent)
                .setTitle("Edit Study Card")
                .setView(input)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String cardTitle = input.getText().toString();
                        Card card = parent.cards.get(selectedItem);
                        card.setTitle(cardTitle);
                        resetUI();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    })
                .show();
    }
}