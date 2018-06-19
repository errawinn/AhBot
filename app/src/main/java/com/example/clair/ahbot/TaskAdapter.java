package com.example.clair.ahbot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    public List<ScheduleTask> mDataset;
    Context context;
    SimpleDateFormat formatter=new SimpleDateFormat("E, dd/MM/yyyy");
    Date d;
    Date current = Calendar.getInstance().getTime();
    Date weak=addDay(current,7);

    public static Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }

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

    public void addAllItems(List<ScheduleTask> tasksList,String whatClass) {
        switch (whatClass){
            case "all":
                for (ScheduleTask task : tasksList)
                    if(task.getTaskDueDate()!="")
                    addItem(task);
                break;
            case "thisweek":
                for (ScheduleTask task : tasksList) {
                    try {
                        d= formatter.parse(task.getTaskDueDate());
                        if(d.after(current)&&d.before(weak))
                            addItem(task);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "today":
                for (ScheduleTask task : tasksList) {
                    try {
                        d= formatter.parse(task.getTaskDueDate());
                        if(d.equals(current))
                            addItem(task);
                    } catch (ParseException e) {
                        if(d==null)
                            addItem(task);
                        e.printStackTrace();
                    }

                }
            break;
                default:
                    break;
        }
    }
    public void deleteEverything(){
        if(mDataset!=null)  mDataset.clear();
    }
}
