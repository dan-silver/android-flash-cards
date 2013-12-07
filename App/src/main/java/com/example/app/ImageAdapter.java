package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dan on 12/6/13.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Card> cards;
    public ImageAdapter(Context c, ArrayList<Card> cards) {
        mContext = c;
        this.cards = cards;
    }

    public int getCount() {
        return cards.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.card_item, parent, false);

        TextView front = (TextView) item.findViewById(R.id.card_front);
        TextView back = (TextView) item.findViewById(R.id.card_back);
        front.setText(cards.get(position).getFront());
        back.setText(cards.get(position).getBack());
        return item;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.mars, R.drawable.mercury, R.drawable.earth
    };
}