package com.example.clair.ahbot;

import android.app.Activity;
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


public class ScheduleAll extends Fragment{
    public static final String TAB = "FragmentAll";

    RecyclerView rvScheduleAll;
    TaskAdapter taskAdapter;
    RecyclerView.LayoutManager rLayourManager;
    List<ScheduleTask> myDatasaet;
    FirestoreHelper firestoreHelper;
    AddTask at=new AddTask();
    Schedule s;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        s=new Schedule();
        View view = inflater.inflate(R.layout.fragment_schedule_all, container, false);
        rvScheduleAll = view.findViewById(R.id.rvScheduleAll);
        rLayourManager=new LinearLayoutManager(s.getApplication());
        rvScheduleAll.setLayoutManager(rLayourManager);
        taskAdapter=new TaskAdapter(this.getContext());
        rvScheduleAll.setAdapter(taskAdapter);
        //firestoreHelper.getTasks();

    return view;
    }
    public void UpdateList(List<ScheduleTask> st){
        taskAdapter.deleteEverything();
        taskAdapter.addAllItems(st);
    }
class rvScheduleAll extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView tvTaskName;
    TextView tvShowDate;
    TextView tvShowTime;
    CheckBox chkDone;

    public rvScheduleAll(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        tvTaskName = at.EtEnterTask;
        tvShowDate = at.TvDate;
        tvShowTime = at.TvTime;
    }

    @Override
    public void onClick(View v) {
        ScheduleTask taskDetail = myDatasaet.get(getAdapterPosition());
        //one of these daysss... editttt
    }
}
}
