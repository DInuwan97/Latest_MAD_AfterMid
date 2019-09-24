package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.DBHandler;


public class AddNewDoctorFragment extends Fragment {

    private FragmentAddNewDoctorListner listner;

    DBHandler myDb;
    EditText txtUserName,txtUserEmail,txtDesignation,txtPassword,txtConfirmPassword;
    EditText txtDoctorName,txtDoctorEmail,txtHospital,txtDoctorMobile,txtSpecilization,txtDoctorNic;
    Button btnAddDoctor;

    public interface FragmentAddNewDoctorListner{
        void onInputAddNewDoctorSent(CharSequence input);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_new_doctor,container,false);

        myDb = new DBHandler(getActivity().getApplicationContext());

        txtDoctorName = (EditText) v.findViewById(R.id.editText_doctorName);
        txtDoctorEmail = (EditText) v.findViewById(R.id.editText_doctorEmail);
        txtHospital = (EditText) v.findViewById(R.id.editText_Hospital);
        txtDoctorMobile = (EditText) v.findViewById(R.id.editText_doctorMobile);
        txtSpecilization = (EditText) v.findViewById(R.id.editText_specialiation);
        txtDoctorNic = (EditText) v.findViewById(R.id.editText_doctorNIC);

        btnAddDoctor = (Button) v.findViewById(R.id.btn_AddNewDoc);

        //addNewDoctor();


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_new_doctor, container, false);

        btnAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isInserted = myDb.addDoctor(txtDoctorName.getText().toString(),
                        txtDoctorEmail.getText().toString(),
                        txtHospital.getText().toString(),
                        txtDoctorMobile.getText().toString(),
                        txtSpecilization.getText().toString(),
                        txtDoctorNic.getText().toString());


                if (isInserted == true) {
                    Toast.makeText(getActivity().getApplicationContext(), "Sucessfully Registered!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(),SignInActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Insertion Failiure!!!", Toast.LENGTH_LONG).show();
                }

            }
        });

     return v;
    }






}
