package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.EcareFragments.AddDoctorTimeSlotFragment;

import java.util.ArrayList;

public class DoctorListAdapter extends ArrayAdapter<DoctorsInformation> {

    private Context mContext;
    private ArrayList<DoctorsInformation> mDoctorList;
    private LayoutInflater layoutInflater;

    ArrayAdapter adapter;
    Context con;

    public DoctorListAdapter(Context mContext, ArrayList<DoctorsInformation> mDoctorList) {
        super(mContext, R.layout.item_doctor_list,mDoctorList);
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mDoctorList = mDoctorList;
        con = mContext;
    }


    @Override
    public View getView(final int position, View converttView, ViewGroup parent){

        final ViewHolder viewHolder;
        final DoctorsInformation item = getItem(position);


        if (converttView == null) {
            converttView = layoutInflater.inflate(R.layout.item_doctor_list, parent, false);
            viewHolder = new ViewHolder(converttView);
            viewHolder.btnViewDoctor = (Button) converttView.findViewById(R.id.btnViewDoctor);

            viewHolder.btnViewDoctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fragment = new AddDoctorTimeSlotFragment();
                    Bundle args = new Bundle();

                    args.putString(AddDoctorTimeSlotFragment.DATA_RECIEVE_EMAIL, String.valueOf(mDoctorList.get(position).getEmail()));
                    args.putString(AddDoctorTimeSlotFragment.DATA_RECIEVE_DOCTORNAME, String.valueOf(mDoctorList.get(position).getUsername()));
                    args.putString(AddDoctorTimeSlotFragment.DATA_RECIEVE_DOCTORMOBILE,  String.valueOf(mDoctorList.get(position).getMobile()));
                    args.putString(AddDoctorTimeSlotFragment.DATA_RECIEVE_HOSPITAL,  String.valueOf(mDoctorList.get(position).getHospital()));
                    //args.putString(AddDoctorTimeSlotFragment.DATA_RECIEVE_SPECIALIZATION, viewHolder.txtSpecialization.toString());

                    fragment.setArguments(args);
                    //Toast.makeText(getContext(),"Button was clicked for list itme "+ position,Toast.LENGTH_SHORT).show();
                    //Bundle bundle = new Bundle();
                    Intent i = new Intent(con, AddDoctorTimeSlotFragment.class);
                    i.putExtra("A",viewHolder.txtDoctorName.toString());
                   // getContext().getApplicationContext().startActivity(i);
                    ((MainActivity)con).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                }
            });

            converttView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)converttView.getTag();
        }

            viewHolder.txtDoctorName.setText(mDoctorList.get(position).getUsername());
            viewHolder.txtDoctorEmail.setText(mDoctorList.get(position).getEmail().toString());
            viewHolder.txtDoctorMobile.setText(mDoctorList.get(position).getMobile().toString());
            viewHolder.txtHospital.setText(mDoctorList.get(position).getHospital().toString());
            viewHolder.txtDoctorNic.setText(mDoctorList.get(position).getNic().toString());
//            viewHolder.txtSpecialization.setText(mDoctorList.get(position).getSpecialization().toString());


        /*viewHolder.btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //openDialog(item);
            }
        });*/



        return converttView;
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
