package com.example.app;

/**
 * Created by dan on 12/7/13.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dan on 12/6/13.
 */
public class SetListAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Set> sets;
    public SetListAdapter(Context c, ArrayList<Set> sets) {
        mContext = c;
        this.sets = sets;
    }

    public int getCount() {
        return sets.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(mContext);
        view.setText(sets.get(position).getName());
        return view;
    }
}