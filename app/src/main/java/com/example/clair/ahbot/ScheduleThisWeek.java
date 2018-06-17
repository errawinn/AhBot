package com.example.clair.ahbot;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.annotation.Nullable;



public class ScheduleThisWeek extends Fragment {
    RecyclerView rvScheduleThisWeek;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_this_week, container, false);
        rvScheduleThisWeek = view.findViewById(R.id.rvScheduleThisWeek);
        return view;
    }
}
