package com.emergelk.ravindrab;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_main extends ListFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private List<ParseObject> students;
    private ArrayList<HashMap<String, String>> list;
    private ArrayList<String> batches;
    private ArrayList<String> towns;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<HashMap<String, String>>();
        towns = new ArrayList<>();
        batches = new ArrayList<>();

        final LeaderBoardAdapter adapter = new LeaderBoardAdapter(getActivity(), list);
        setListAdapter(adapter);

        Spinner sTowns = (Spinner) getView().findViewById(R.id.spinner);
        sTowns.setOnItemSelectedListener(this);
        ArrayAdapter<String> aTowns;
        aTowns = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, towns);
        aTowns.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTowns.setAdapter(aTowns);

        Spinner sBatches = (Spinner) getView().findViewById(R.id.spinner2);
        sBatches.setOnItemSelectedListener(this);
        ArrayAdapter<String> aBatches;
        aBatches = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, batches);
        aBatches.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBatches.setAdapter(aBatches);

        //spinner for batches
        ParseQuery<ParseObject> Qbatch = ParseQuery.getQuery("Batches");
        Qbatch.whereExists("year");
        Qbatch.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {

                for (int i = 0; i < results.size(); i++) {
                    batches.add(String.valueOf((int) results.get(i).get("year")));
                }

            }
        });

        //spinner for towns
        ParseQuery<ParseObject> Qtown = ParseQuery.getQuery("Towns");
        Qtown.whereExists("Town");
        Qtown.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {

                for (int i = 0; i < results.size(); i++) {
                    towns.add((String) results.get(i).get("Town"));
                }
            }
        });

        //LeaderBoard query
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
                adapter.notifyDataSetChanged();
            }
        });
        getListView().setOnItemClickListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_main, container, false);

        return mainView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), String.valueOf(students.get(position).get("Name")), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
