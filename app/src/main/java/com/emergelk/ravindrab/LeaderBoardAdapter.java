package com.emergelk.ravindrab;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class LeaderBoardAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    LayoutInflater inflater;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    TextView txtFourth;

    public LeaderBoardAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.leaderboard, null);

            txtFirst = (TextView) convertView.findViewById(R.id.name);
            txtSecond = (TextView) convertView.findViewById(R.id.town);
            txtThird = (TextView) convertView.findViewById(R.id.marks);
            txtFourth = (TextView) convertView.findViewById(R.id.rank);

            txtFirst.setVisibility(View.VISIBLE);
            txtSecond.setVisibility(View.VISIBLE);
            txtThird.setVisibility(View.VISIBLE);
            txtFourth.setVisibility(View.VISIBLE);
        }

        HashMap<String, String> map = list.get(position);
        txtFirst.setText(map.get("Name"));
        txtSecond.setText(map.get("Town"));
        txtThird.setText(map.get("Marks"));
        txtFourth.setText(map.get("Rank"));

        return convertView;
    }
}
