package com.example.myapplication;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class SearchFragment extends Fragment {
    @Nullable
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar calender;
    int currentHour;
    int currentMinute;
    String amPm;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        //addTaskDialog();

        return v;
    }

   /* private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.fragment_add_doctor_time_slot, null);

       final EditText txtSelectDate = (EditText)subView.findViewById(R.id.txtSelectDate);
        final EditText chooseTime = (EditText) subView.findViewById(R.id.txtSelectTime);

       txtSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String task = txtSelectDate.getText().toString();


                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity().getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        txtSelectDate.setText(day+"/"+(month+1)+"/"+year);
                    }
                },year , month,day);
                datePickerDialog.show();



            }
        });


        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calender = Calendar.getInstance();
                currentHour = calender.get(Calendar.HOUR_OF_DAY);
                currentMinute = calender.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        chooseTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });






    }*/

    public class ViewHolder{
        EditText txtSelectDate;
    }
}
