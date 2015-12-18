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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.leaderboard, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.name);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.town);
            holder.txtThird = (TextView) convertView.findViewById(R.id.marks);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.rank);

            holder.txtFirst.setVisibility(View.VISIBLE);
            holder.txtSecond.setVisibility(View.VISIBLE);
            holder.txtThird.setVisibility(View.VISIBLE);
            holder.txtFourth.setVisibility(View.VISIBLE);
            convertView.setTag(holder);

        } else holder = (ViewHolder) convertView.getTag();

        HashMap<String, String> map = list.get(position);
        holder.txtFirst.setText(map.get("Name"));
        holder.txtSecond.setText(map.get("Town"));
        holder.txtThird.setText(map.get("Marks"));
        holder.txtFourth.setText(map.get("Rank"));
        return convertView;

    }

    class ViewHolder {

        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;

    }
}
