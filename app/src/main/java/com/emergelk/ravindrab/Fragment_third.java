package com.emergelk.ravindrab;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Fragment_third extends ListFragment {
    String town;
    MyProfileAdapter adapter;

    private ArrayList<HashMap<String, String>> list;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<HashMap<String, String>>();

        adapter = new MyProfileAdapter(getActivity(), list);
        setListAdapter(adapter);

        String year;
        String[] ptype = new String[]{"Revision", "Theory"};
        ParseInstallation currentUser = ParseInstallation.getCurrentInstallation();
        TextView welcome = (TextView) getActivity().findViewById(R.id.welcome);
        welcome.setText("Welcome " + currentUser.get("name"));
        String indexR = (String) currentUser.get("indexR");
        String indexT = (String) currentUser.get("indexT");
        String codeR = indexR.length() != 0 ? indexR.substring(0, 2) : null;
        String codeT = indexT.length() != 0 ? indexT.substring(0, 2) : null;
        final String code = codeR != null ? codeR : (codeT != null ? codeT : null);
        if (indexR.length() != 0) year = (indexR.replaceAll("\\D", "")).substring(0, 2);
        else year = (indexT.replaceAll("\\D", "")).substring(0, 2);
        for (String pp : ptype) {
            ParseQuery<ParseObject> queryT = ParseQuery.getQuery("PaperLog");
            queryT.whereExists("papertype");
            queryT.selectKeys(Arrays.asList("batch", "paperNo", "papertype"));
            queryT.addDescendingOrder("createdAt");
            queryT.whereEqualTo("batch", "20" + year);
            queryT.whereEqualTo("papertype", pp);
            List<ParseObject> theoryPaper = null;
            try {
                theoryPaper = queryT.find();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (theoryPaper != null) {
                for (ParseObject finalTheoryPaper : theoryPaper) {
                    ParseQuery<ParseObject> paperQuerytown1 = ParseQuery.getQuery(finalTheoryPaper.get("papertype").toString() +
                            finalTheoryPaper.get("batch").toString() +
                            finalTheoryPaper.get("paperNo").toString());
                    if (pp == "Revision" && codeR == null) {
                        break;
                    } else if (pp == "Theory" && codeT == null) {
                        break;
                    }
                    String in = null;
                    if (pp == "Revision") {
                        in = indexR;
                    } else if (pp == "Theory") {
                        in = indexT;
                    }
                    paperQuerytown1.whereEqualTo("index", in);
                    ParseObject theorytwn = null;
                    try {
                        int count = paperQuerytown1.count();
                        if (count > 0) {
                            theorytwn = paperQuerytown1.getFirst();
                        }
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    if (theorytwn != null) {
                        HashMap<String, String> temp = new HashMap<String, String>();
                        temp.put("PaperName", finalTheoryPaper.get("papertype").toString() + " " +
                                finalTheoryPaper.get("batch").toString() + " " +
                                finalTheoryPaper.get("paperNo").toString());

                        temp.put("Marks", String.valueOf(theorytwn.get("marks")));
                        temp.put("Rank", String.valueOf(theorytwn.get("twnrank")));
                        list.add(temp);
                    }

                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third, container, false);
    }
}