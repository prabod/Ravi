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

public class AllClassRevision extends ListFragment {
    String town;
    LeaderBoardAdapter adapter;
    private List<ParseObject> revision;
    private List<ParseObject> revisionlk;
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
        String indexT = String.valueOf(currentUser.get("indexT"));

        String codeT = indexT.length() != 0 ? indexT.substring(0, 2) : null;

        String codeR = indexR.length() != 0 ? indexR.substring(0, 2) : null;
        final String code = codeR != null ? codeR : (codeT != null ? codeT : null);
        if (indexR.length() != 0) year = (indexR.replaceAll("\\D", "")).substring(0, 2);
        else year = (indexT.replaceAll("\\D", "")).substring(0, 2);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("PaperLog");
        query.whereExists("papertype");
        query.selectKeys(Arrays.asList("batch", "paperNo", "papertype", "createdAt"));
        query.whereEqualTo("batch", "20" + year);
        query.whereEqualTo("papertype", "Revision");
        query.addDescendingOrder("createdAt");
        ParseObject revPaper = null;
        try {
            revisionlk = query.find();
            revPaper = revisionlk.get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (revPaper != null) {
            ParseQuery<ParseObject> paperQuery = ParseQuery.getQuery(revPaper.get("papertype").toString() +
                    revPaper.get("batch").toString() +
                    revPaper.get("paperNo").toString());
            paperQuery.orderByDescending("marks");
            paperQuery.whereExists("rank");
            paperQuery.setLimit(10);
            try {
                revision = paperQuery.find();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < revision.size(); i++) {

                HashMap<String, String> temp = new HashMap<String, String>();
                temp.put("Name", String.valueOf(revision.get(i).get("index")));
                temp.put("Town", String.valueOf(revision.get(i).get("index")).substring(0, 2));
                temp.put("Marks", String.valueOf(revision.get(i).get("marks")));
                temp.put("Rank", String.valueOf(revision.get(i).get("rank")));

                list.add(temp);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_class_revision, container, false);
    }
}