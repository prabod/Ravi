package com.emergelk.ravindrab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emergelk.ravindrab.LeaderBoard.AllClassRevision;
import com.emergelk.ravindrab.LeaderBoard.AllClassTheory;
import com.emergelk.ravindrab.LeaderBoard.TownRevision;
import com.emergelk.ravindrab.LeaderBoard.TownTheory;

public class Fragment_main extends Fragment {
    private FragmentTabHost mTabHost;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView name = (TextView) getActivity().findViewById(R.id.name1);
        TextView town = (TextView) getActivity().findViewById(R.id.town1);
        TextView marks = (TextView) getActivity().findViewById(R.id.marks1);
        TextView rank = (TextView) getActivity().findViewById(R.id.rank1);
        name.setText("Index");
        town.setText("Town");
        marks.setText("Marks");
        rank.setText("Rank");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_main, container, false);

        mTabHost = (FragmentTabHost) mainView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmenta").setIndicator("All 4G LTE Rev"),
                AllClassRevision.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("All 4G LTE"),
                AllClassTheory.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("4G LTE Rev"),
                TownRevision.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentd").setIndicator("4G LTE"),
                TownTheory.class, null);

        return mainView;
    }

}
