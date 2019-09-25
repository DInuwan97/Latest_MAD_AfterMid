package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorListAdapter extends ArrayAdapter<DoctorsInformation> {

    private Context mContext;
    private ArrayList<DoctorsInformation> mDoctorList;
    private LayoutInflater layoutInflater;

    ArrayAdapter adapter;

    public DoctorListAdapter(Context mContext, ArrayList<DoctorsInformation> mDoctorList) {
        super(mContext, R.layout.item_doctor_list,mDoctorList);
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mDoctorList = mDoctorList;
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
                    Toast.makeText(getContext(),"Button was clicked for list itme "+ position,Toast.LENGTH_SHORT).show();
                    //Bundle bundle = new Bundle();
                    //Intent i = new Intent(getContext(),AddDoctorTimeSlotFragment.class);


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
            viewHolder.txtSpecialization.setText(mDoctorList.get(position).getNic().toString());






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
