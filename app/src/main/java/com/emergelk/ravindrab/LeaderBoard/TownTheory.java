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

public class TownTheory extends ListFragment {
    String town;
    LeaderBoardAdapter adapter;
    private List<ParseObject> theorytwn;
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
        String codeR = indexR.length() != 0 ? indexR.substring(0, 2) : null;
        String codeT = indexT.length() != 0 ? indexT.substring(0, 2) : null;
        final String code = codeR != null ? codeR : (codeT != null ? codeT : null);
        if (indexR.length() != 0) year = (indexR.replaceAll("\\D", "")).substring(0, 2);
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

        final ParseObject finalTheoryPaper = theoryPaper;
        if (finalTheoryPaper != null) {
            ParseQuery<ParseObject> paperQuerytown1 = ParseQuery.getQuery(finalTheoryPaper.get("papertype").toString() +
                    finalTheoryPaper.get("batch").toString() +
                    finalTheoryPaper.get("paperNo").toString());
            paperQuerytown1.orderByDescending("marks");
            paperQuerytown1.whereStartsWith("index", code);
            paperQuerytown1.setLimit(10);
            try {
                theorytwn = paperQuerytown1.find();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            List<ParseObject> array1 = theorytwn;
            for (int i = 0; i < array1.size(); i++) {

                HashMap<String, String> temp = new HashMap<String, String>();
                temp.put("Name", String.valueOf(theorytwn.get(i).get("index")));
                temp.put("Town", String.valueOf(theorytwn.get(i).get("index")).substring(0, 2));
                temp.put("Marks", String.valueOf(theorytwn.get(i).get("marks")));
                temp.put("Rank", String.valueOf(theorytwn.get(i).get("twnrank")));

                list.add(temp);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.town_theory, container, false);
    }
}