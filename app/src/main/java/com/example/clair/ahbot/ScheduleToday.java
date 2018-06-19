package com.example.clair.ahbot;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import javax.annotation.Nullable;


public class ScheduleToday extends Fragment {
    RecyclerView rvScheduleToday;
    TaskAdapter taskAdapter;
    RecyclerView.LayoutManager rLayourManager;
    List<ScheduleTask> myDatasaet;
    AddTask at = new AddTask();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_today, container, false);
        rvScheduleToday = view.findViewById(R.id.rvScheduleToday);
        rLayourManager = new LinearLayoutManager(Schedule.Instance.getApplication());
        rvScheduleToday.setLayoutManager(rLayourManager);
        taskAdapter = new TaskAdapter(this.getContext());
        if (myDatasaet != null) {
            UpdateList(myDatasaet);
        }
        rvScheduleToday.setAdapter(taskAdapter);
        return view;
    }
    public void UpdateList(List<ScheduleTask> st) {
        if (taskAdapter != null) {
            taskAdapter.deleteEverything();
            taskAdapter.addAllItems(st, "today");
        } else {
            myDatasaet = st;
        }
    }

    class rvScheduleToday extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTaskName;
        TextView tvShowTime;
        CheckBox chkDone;

        public rvScheduleToday(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTaskName = at.EtEnterTask;
            tvShowTime = at.TvTime;
        }

        @Override
        public void onClick(View v) {
            ScheduleTask taskDetail = myDatasaet.get(getAdapterPosition());
            //one of these daysss... editttt
        }
    }
}

