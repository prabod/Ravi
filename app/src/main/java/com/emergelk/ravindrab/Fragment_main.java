package com.emergelk.ravindrab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emergelk.ravindrab.LeaderBoard.AllClassRevision;
import com.emergelk.ravindrab.LeaderBoard.AllClassTheory;
import com.emergelk.ravindrab.LeaderBoard.TownRevision;
import com.emergelk.ravindrab.LeaderBoard.TownTheory;

public class Fragment_main extends Fragment {
    private FragmentTabHost mTabHost;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_main, container, false);

        mTabHost = (FragmentTabHost) mainView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("All Class Revision"),
                AllClassRevision.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("All Class Theory"),
                AllClassTheory.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("Revision"),
                TownRevision.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("Theory"),
                TownTheory.class, null);

        return mainView;
    }

}
