package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.Models.TimeSlots;
import com.example.myapplication.R;


import java.util.ArrayList;

public class TimeSlotListAdapter extends ArrayAdapter<TimeSlots> {

    private Context mContext;
    private ArrayList<TimeSlots> mDoctorTimeSlotsList;
    private LayoutInflater layoutInflater;

    ArrayAdapter adapter;
    Context con;

    public TimeSlotListAdapter(Context context, ArrayList<TimeSlots> mDoctorTimeSlotsList) {
        super(context, R.layout.item_time_slot_list,mDoctorTimeSlotsList);
         layoutInflater = LayoutInflater.from(mContext);
        mDoctorTimeSlotsList = mDoctorTimeSlotsList;
        con = mContext;
    }


    @Override
    public View getView(final int position, View converttView, ViewGroup parent){

        final ViewHolder viewHolder;
        final TimeSlots item = getItem(position);


        if (converttView == null) {
            converttView = layoutInflater.inflate(R.layout.item_time_slot_list, parent, false);
            viewHolder = new ViewHolder(converttView);
            converttView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)converttView.getTag();
        }

        viewHolder.txtDoctorMail.setText(mDoctorTimeSlotsList.get(position).getDoctorEmail());
        viewHolder.txtSlotDay.setText(mDoctorTimeSlotsList.get(position).getSlotDay());
        viewHolder.txtSlotStartTime.setText(mDoctorTimeSlotsList.get(position).getSlotStartTime());
        viewHolder.txtSlotEndTime.setText(mDoctorTimeSlotsList.get(position).getSlotEndTime());

        return converttView;
    }


    private class ViewHolder{

        TextView txtDoctorMail,txtSlotDay,txtSlotStartTime,txtSlotEndTime;

        public ViewHolder(View v){

           txtDoctorMail = (TextView)v.findViewById(R.id.txtDoctorEmail);
           txtSlotDay = (TextView)v.findViewById(R.id.txtSlotDay);
           txtSlotStartTime = (TextView)v.findViewById(R.id.txtSelectTimeStart);
           txtSlotEndTime= (TextView)v.findViewById(R.id.txtSelectTimeEnd);

        }
    }






}

