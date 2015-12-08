package com.emergelk.ravindrab.LeaderBoard;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emergelk.ravindrab.LeaderBoardAdapter;
import com.emergelk.ravindrab.R;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AllClassTheory extends ListFragment {
    String town;
    LeaderBoardAdapter adapter;
    private List<ParseObject> theory;
    private ArrayList<HashMap<String, String>> list;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<HashMap<String, String>>();

        adapter = new LeaderBoardAdapter(getActivity(), list);
        setListAdapter(adapter);

        String year;
        ParseInstallation currentUser = ParseInstallation.getCurrentInstallation();
        String indexR = (String) currentUser.get("indexR");
        String indexT = (String) currentUser.get("indexT");
        String codeR = indexR != null ? indexR.substring(0, 2) : null;
        String codeT = indexT != null ? indexT.substring(0, 2) : null;
        final String code = codeR != null ? codeR : (codeT != null ? codeT : null);
        if (indexR != null) year = (indexR.replaceAll("\\D", "")).substring(0, 2);
        else year = (indexT.replaceAll("\\D", "")).substring(0, 2);

        ParseQuery<ParseObject> queryT = ParseQuery.getQuery("PaperLog");
        queryT.whereExists("papertype");
        queryT.selectKeys(Arrays.asList("batch", "paperNo", "papertype"));
        queryT.addDescendingOrder("createdAt");
        queryT.whereEqualTo("batch", "20" + year);
        queryT.whereEqualTo("papertype", "Theory");
        ParseObject theoryPaper = null;
        try {
            theoryPaper = queryT.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ParseQuery<ParseObject> papertQuery = ParseQuery.getQuery(theoryPaper.get("papertype").toString() +
                theoryPaper.get("batch").toString() +
                theoryPaper.get("paperNo").toString());
        papertQuery.orderByDescending("marks");
        papertQuery.setLimit(10);
        final ParseObject finalTheoryPaper = theoryPaper;
        try {
            theory = papertQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < theory.size(); i++) {

            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("Name", String.valueOf(theory.get(i).get("index")));
            temp.put("Town", String.valueOf(10));
            temp.put("Marks", String.valueOf(theory.get(i).get("marks")));
            temp.put("Rank", String.valueOf(i + 1));

            list.add(temp);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_class_theory, container, false);
    }
}