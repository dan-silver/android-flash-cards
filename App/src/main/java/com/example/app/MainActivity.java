/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] drawerMenuItems;
    private int currentSet;

    ArrayList<Card> cards = new ArrayList<Card>();
    ArrayList<Set> cardSets = new ArrayList<Set>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        drawerMenuItems = getResources().getStringArray(R.array.drawer_menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the manage content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerMenuItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        CardsDataSource dataSource = new CardsDataSource(this);
        dataSource.open();
        cards.addAll(dataSource.getAllComments());
        dataSource.close();

        cardSets.add(new Set("Spanish Chapter 1"));
        cardSets.add(new Set("Spanish Chapter 2"));
        cardSets.add(new Set("Spanish Chapter 3"));
        cardSets.add(new Set("Spanish Chapter 4"));
        cardSets.add(new Set("Spanish Chapter 5"));
        currentSet = 4;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment instanceof ManageCardsFragment) {
            inflater.inflate(R.menu.manage, menu);
        } else if (fragment instanceof LearnFragment) {
            inflater.inflate(R.menu.learn, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        for (int i = 0; i < menu.size(); i++) menu.getItem(i).setVisible(!drawerOpen);
        Fragment cardManager = getFragmentManager().findFragmentById(R.id.content_frame);
        if (cardManager instanceof ManageCardsFragment && ((ManageCardsFragment) cardManager).selectedItem == -1) {
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        FragmentManager fragmentManager = getFragmentManager();
        switch (item.getItemId()) {
            case R.id.action_add_card:
                final ManageCardsFragment cardManager = (ManageCardsFragment) fragmentManager.findFragmentById(R.id.content_frame);
                LayoutInflater factory = LayoutInflater.from(this);
                final View textEntryView = factory.inflate(R.layout.card_input, null);
                final EditText front = (EditText) textEntryView.findViewById(R.id.front);
                final EditText back = (EditText) textEntryView.findViewById(R.id.back);

                new AlertDialog.Builder(this)
                        .setTitle("Create Study Card")
                        .setView(textEntryView)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String frontStr = front.getText().toString();
                                String backStr = back.getText().toString();
                                if (!frontStr.equals("")) {
                                    cardManager.addCard(new Card(frontStr, backStr));
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                })
                        .show();
                break;
            case R.id.action_edit_card:
                ((ManageCardsFragment) fragmentManager.findFragmentById(R.id.content_frame)).editCurrentCard();
                break;
            case R.id.action_remove_card:
                Log.v("silver", "Removing a new card!");
                ((ManageCardsFragment) fragmentManager.findFragmentById(R.id.content_frame)).removeCard();
                break;
            case R.id.action_next_card:
                ((LearnFragment) fragmentManager.findFragmentById(R.id.content_frame)).nextCard();
                break;
            case R.id.action_previous_card:
                ((LearnFragment) fragmentManager.findFragmentById(R.id.content_frame)).previousCard();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void addCard(Card card) {
        cards.add(card);
        CardsDataSource dataSource = new CardsDataSource(this);
        dataSource.open();
        dataSource.createCard(card.getFront(), card.getBack());
        dataSource.close();
    }

    public void removeCard(int cardPosition) {
        Card card = cards.get(cardPosition);
        CardsDataSource dataSource = new CardsDataSource(this);
        dataSource.open();
        dataSource.deleteCard(card);
        dataSource.close();
        long id = card.getId();
        for (int i = 0;i< cards.size();i++) {
            Card c = cards.get(i);
            if (c.getId() == id) {
                cards.remove(i);
            }
        }
    }

    public void updateCard(int selectedItem, String cardFront, String cardBack) {
        Card card = cards.get(selectedItem);
        card.setFront(cardFront);
        card.setBack(cardBack);

        CardsDataSource dataSource = new CardsDataSource(this);
        dataSource.open();
        dataSource.editCard(card);
        dataSource.close();
    }

    public void setCurrentSet(int set) {
        currentSet = set;
    }
    public int getCurrentSet() {
        return currentSet;
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment;
        if (drawerMenuItems[position].equals("Manage")) {
            fragment = new ManageCardsFragment(this);
        } else if (drawerMenuItems[position].equals("Learn")) {
            fragment = new LearnFragment(this);
        } else if (drawerMenuItems[position].equals("Switch Set")) {
            fragment = new SwitchSetFragment(this);
        } else {
            fragment = new BlankFragment();
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(drawerMenuItems[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void refreshMenu() {
        invalidateOptionsMenu();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}