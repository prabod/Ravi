package com.emergelk.ravindrab;

/**
 * Created by prabod on 11/22/15.
 */

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_main extends ListFragment implements AdapterView.OnItemClickListener {

    private List<ParseObject> students;
    private ArrayList<HashMap<String, String>> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<HashMap<String, String>>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("LeaderBoard");
        query.orderByDescending("Marks");
        query.whereExists("Marks");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                students = results;

                for (int i = 0; i < students.size(); i++) {

                    HashMap<String, String> temp = new HashMap<String, String>();
                    temp.put("Name", String.valueOf(students.get(i).get("Name")));
                    temp.put("Town", String.valueOf(students.get(i).get("Town")));
                    temp.put("Marks", String.valueOf(students.get(i).get("Marks")));
                    temp.put("Rank", String.valueOf(i + 1));
                    Log.d("de", String.valueOf(students.get(i).get("Name")) +
                                    String.valueOf(students.get(i).get("Town")) +
                                    String.valueOf(students.get(i).get("Marks"))
                    );
                    list.add(temp);
                }
            }
        });

        LeaderBoardAdapter adapter = new LeaderBoardAdapter(getActivity(), list);
        setListAdapter(adapter);

        getListView().setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), String.valueOf(students.get(position).get("Name")), Toast.LENGTH_SHORT)
                .show();
    }
}
