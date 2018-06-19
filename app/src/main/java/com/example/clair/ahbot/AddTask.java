package com.example.clair.ahbot;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTask extends AppCompatActivity{


    static AddTask Instance;
    Calendar c;
    ScheduleTaskFirestoreHelper f;
    FirebaseUser user;

EditText EtEnterTask;
CheckBox ChkDaily;
TextView TvDate,TvTime;
Button BtnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Instance=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        c=Calendar.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        f=new ScheduleTaskFirestoreHelper();

        EtEnterTask=findViewById(R.id.etEnterTask);
        ChkDaily=findViewById(R.id.chkEveryday);
        TvDate=findViewById(R.id.tvDatePicker);
        TvTime=findViewById(R.id.tvTimePicker);
        BtnSubmit=findViewById(R.id.btnSubmit);

        ChkDaily.setOnClickListener(mListener);
        TvDate.setOnClickListener(mListener);
        TvTime.setOnClickListener(mListener);
        BtnSubmit.setOnClickListener(mListener);
    }

    private View.OnClickListener mListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.chkEveryday:
                if(ChkDaily.isChecked()){
                    TvDate.setClickable(false);
                    TvDate.setHint(R.string.Everyday);
                }
                else {
                TvDate.setClickable(true);
                TvDate.setHint(R.string.main_msg_enterDueDate);
                }
                break;
            case R.id.tvDatePicker:
                DialogFragment datePickerFragment=new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(),"datepicker");
                break;
            case R.id.tvTimePicker:
                DialogFragment timePickerFragment=new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(),"timepicker");
                break;
            case R.id.btnSubmit:
                String taskName= EtEnterTask.getText().toString();
                String Date=TvDate.getText().toString();
                String Time=TvTime.getText().toString();

                ScheduleTask t=new ScheduleTask(user.toString(),taskName,Date,Time);
                f.addTask(t);

                Intent intent =new Intent();
                intent.putExtra("newToDo",t);
                setResult(RESULT_OK,intent);
                finish();
                break;
                default:
                    break;
        }
        }
    };


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dpd =  new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Instance.updateDate(year,month,day);
        }
    }

    public void updateDate(int y,int m, int d){
        c.set(Calendar.YEAR,y);
        c.set(Calendar.MONTH,m);
        c.set(Calendar.DAY_OF_MONTH,d);
        SimpleDateFormat f=new SimpleDateFormat("EEE, dd/MM/yyyy");
        TvDate.setText(f.format(c.getTime()));
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c=Calendar.getInstance();
            int hour=c.get(Calendar.HOUR_OF_DAY);
            int min= c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(),this,hour,min, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String s=hourOfDay+":"+minute;
            Instance.updateTime(hourOfDay,minute);
        }
    }
    public void updateTime(int h,int m){
        c.set(Calendar.HOUR_OF_DAY,h);
        c.set(Calendar.MINUTE,m);
        SimpleDateFormat f=new SimpleDateFormat("hh:mm aa");
        TvTime.setText(f.format(c.getTime()));
    }

}
