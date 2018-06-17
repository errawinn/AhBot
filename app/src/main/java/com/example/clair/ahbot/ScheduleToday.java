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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleToday.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleToday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleToday extends Fragment {
    RecyclerView rvScheduleToday;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_today, container, false);
        rvScheduleToday = view.findViewById(R.id.rvScheduleToday);
        return view;
    }
}
