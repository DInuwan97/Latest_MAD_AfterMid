package com.example.myapplication.EcareFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.DoctorsInformation;
import com.example.myapplication.R;


public class AddDoctorTimeSlotFragment extends Fragment {

    public static String DATA_RECIEVE_EMAIL = "dataEmail";
    public static String DATA_RECIEVE_DOCTORNAME = "dataDoctorName";
    public static String DATA_RECIEVE_DOCTORMOBILE = "dataDoctorMobile";
    public static String DATA_RECIEVE_HOSPITAL = "dataHospital";
    public static String DATA_RECIEVE_SPECIALIZATION = "dataSpecilization";
    public static String DATA_RECIEVE_NIC = "dataNIC";

    DoctorsInformation docInfo;
    Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_doctor_time_slot, container, false);

        Bundle args = getArguments();
        docInfo = new DoctorsInformation();

        docInfo.setUsername(args.getString(DATA_RECIEVE_DOCTORNAME));
        docInfo.setEmail(args.getString(DATA_RECIEVE_EMAIL));
        docInfo.setMobile(args.getString(DATA_RECIEVE_DOCTORMOBILE));
        docInfo.setHospital(args.getString(DATA_RECIEVE_HOSPITAL));









        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.txtDoctorName.setText(docInfo.getUsername());
        viewHolder.txtDoctorEmail.setText(docInfo.getEmail());
        viewHolder.txtDoctorMobile.setText(docInfo.getMobile());
        viewHolder.txtHospital.setText(docInfo.getHospital());
        //viewHolder.txtDoctorNic.setText(DATA_RECIEVE_NIC);
        //viewHolder.txtSpecialization.setText(DATA_RECIEVE_SPECIALIZATION);

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

        TextView txtDeleteUserDialogBoxConfirmation;

        public ViewHolder(View v){

            txtDoctorName = (TextView)v.findViewById(R.id.txtDoctorName);
            txtDoctorEmail = (TextView)v.findViewById(R.id.txtDoctorEmail);
            txtDoctorMobile = (TextView)v.findViewById(R.id.txtDoctorMobile);
            txtDoctorNic = (TextView)v.findViewById(R.id.txtDoctorNic);
            txtHospital = (TextView)v.findViewById(R.id.txtDoctorNic);
            txtSpecialization = (TextView)v.findViewById(R.id.txtSpecialization);

            btnViewDoctor = (Button) v.findViewById(R.id.btnViewDoctor);


            txtDesignation = (TextView)v.findViewById(R.id.txtDesignation);
            // btnDelete = (Button) v.findViewById(R.id.buttonDelete);


        }
    }


}


