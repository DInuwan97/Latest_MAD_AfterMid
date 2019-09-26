package com.example.myapplication.EcareFragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.DoctorsInformation;
import com.example.myapplication.Models.TimeSlots;
import com.example.myapplication.R;

import java.util.Calendar;


public class AddDoctorTimeSlotFragment extends Fragment {

    public static String DATA_RECIEVE_EMAIL = "dataEmail";
    public static String DATA_RECIEVE_DOCTORNAME = "dataDoctorName";
    public static String DATA_RECIEVE_DOCTORMOBILE = "dataDoctorMobile";
    public static String DATA_RECIEVE_HOSPITAL = "dataHospital";
    public static String DATA_RECIEVE_SPECIALIZATION = "dataSpecilization";
    public static String DATA_RECIEVE_NIC = "dataNIC";

    DoctorsInformation docInfo;
    Intent intent;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar calender;
    int currentHour;
    int currentMinute;
    String amPm;

    Button btnAddTimeSlot;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_doctor_time_slot, container, false);

        final EditText chooseTime = (EditText)v.findViewById(R.id.txtSelectTimeStart);
        final EditText chooseTime2 = (EditText)v.findViewById(R.id.txtSelectTimeEnd);
        final EditText slotDay = (EditText)v.findViewById(R.id.txtSelectDate);
        btnAddTimeSlot = (Button)v.findViewById(R.id.save_btn);
        Button btnViewDoctor = (Button) v.findViewById(R.id.btnViewDoctor);




        Bundle args = getArguments();
        docInfo = new DoctorsInformation();

        docInfo.setUsername(args.getString(DATA_RECIEVE_DOCTORNAME));
        docInfo.setEmail(args.getString(DATA_RECIEVE_EMAIL));
        docInfo.setMobile(args.getString(DATA_RECIEVE_DOCTORMOBILE));
        docInfo.setHospital(args.getString(DATA_RECIEVE_HOSPITAL));









        final ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.txtDoctorName.setText(docInfo.getUsername());
        viewHolder.txtDoctorEmail.setText(docInfo.getEmail());
        viewHolder.txtDoctorMobile.setText(docInfo.getMobile());
        viewHolder.txtHospital.setText(docInfo.getHospital());
        //viewHolder.txtDoctorNic.setText(DATA_RECIEVE_NIC);
        //viewHolder.txtSpecialization.setText(DATA_RECIEVE_SPECIALIZATION);




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

        chooseTime2.setOnClickListener(new View.OnClickListener() {
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
                        chooseTime2.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });


        btnAddTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeSlots timeSlots = new TimeSlots();
                Bundle args = getArguments();

                timeSlots.setDoctorEmail(args.getString(DATA_RECIEVE_EMAIL));
                timeSlots.setSlotDay(slotDay.getText().toString());
                timeSlots.setSlotStartTime(chooseTime.getText().toString());
                timeSlots.setSlotEndTime(chooseTime2.getText().toString());

                DBHandler myDB = new DBHandler(getActivity().getApplicationContext());

                boolean isInserted = myDB.addTimeSlot(timeSlots);

                if(isInserted == true){
                    Toast.makeText(getActivity().getApplicationContext(), "Sucessfully Added!!!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Dail to add", Toast.LENGTH_LONG).show();
                }


            }
        });


        btnViewDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });






        return v;



    }


    private class ViewHolder{

        TextView txtDoctorName;
        TextView txtDoctorEmail;
        TextView txtDoctorMobile;
        TextView txtDoctorNic;
        TextView txtHospital;
        TextView txtSpecialization;

        TextView txtDesignation;
        //Button btnDelete,btnView;

        Button btnViewDoctor;
        EditText txtSelectTime;

        TextView txtDeleteUserDialogBoxConfirmation;

        public ViewHolder(View v){

            txtDoctorName = (TextView)v.findViewById(R.id.txtDoctorName);
            txtDoctorEmail = (TextView)v.findViewById(R.id.txtDoctorEmail);
            txtDoctorMobile = (TextView)v.findViewById(R.id.txtDoctorMobile);
            txtDoctorNic = (TextView)v.findViewById(R.id.txtDoctorNic);
            txtHospital = (TextView)v.findViewById(R.id.txtDoctorNic);
            txtSpecialization = (TextView)v.findViewById(R.id.txtSpecialization);




            txtDesignation = (TextView)v.findViewById(R.id.txtDesignation);
            // btnDelete = (Button) v.findViewById(R.id.buttonDelete);


            txtSelectTime = (EditText)v.findViewById(R.id.txtSelectTimeEnd);


        }
    }


}


