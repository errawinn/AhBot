package com.example.clair.ahbot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    public List<ScheduleTask> mDataset;
    Context context;

    public TaskAdapter(){}
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView TvTaskName,TvDate,TvTime;
        public ViewHolder(View v){
            super(v);
            TvTaskName=v.findViewById(R.id.tvTaskName);
            TvDate= v.findViewById(R.id.tvShowDate);
            TvTime= v.findViewById(R.id.tvShowTime);
        }

    }

    public TaskAdapter(Context context){
        this.context = context;
        this.mDataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        final ScheduleTask st=mDataset.get(position);
        holder.TvTaskName.setText(st.getTaskName());
        holder.TvDate.setText(st.getTaskDueDate());
        holder.TvTime.setText(st.getTaskDueTime());
    }

    @Override
    public int getItemCount() {
        if (mDataset==null) return 0;
        else return mDataset.size();
    }


    public void addItem (ScheduleTask t) {
        mDataset.add(t);
        notifyItemChanged(mDataset.size() - 1);
    }

    public void addAllItems(List<ScheduleTask> tasksList) {
        for (ScheduleTask task : tasksList) {
            addItem(task);
        }
    }
    public void deleteEverything(){
        if(mDataset!=null)  mDataset.clear();
    }
}
