package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class TestFindUserFragment extends Fragment {

    public static final String TAG = "FIndUSer";

    Button scnbtn,searchbtn;
    EditText getPID;
    DBHandler db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_test_find_user,container,false);

        getPID = (EditText)v.findViewById(R.id.getPid);
        scnbtn = (Button)v.findViewById(R.id.scan_qr_btn);
        searchbtn = (Button)v.findViewById(R.id.search_id_btn);

        db = new DBHandler(getActivity().getApplicationContext());

         final Activity activity = this.getActivity();

        scnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //scan qr code

                //added getActivity not sure

                IntentIntegrator integrator = new IntentIntegrator(getActivity()).forSupportFragment(TestFindUserFragment.this);


                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);

                integrator.setPrompt("Scan");

                integrator.setCameraId(0);

                integrator.setBeepEnabled(false);

                integrator.setBarcodeImageEnabled(false);

                integrator.initiateScan();

                Log.d(TAG, "onClick: ended");


            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pid = getPID.getText().toString();



                if(pid.equals(""))
                {
                    Toast.makeText(getContext(),"Enter Valid ID",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    db.setPatientID(pid);


                    Fragment frag = new TestDescriptionFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();


                    /*Intent intent = new Intent(getContext(), TestDescriptionFragment.class);

                    startActivity(intent);*/
                }


            }
        });






        return v;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        Log.d(TAG, "onActivityResult: in onacttivityresult");

        if(result != null)
        {
            if(result.getContents()==null)
            {
                Log.d(TAG, "onActivityResult: in if statement");

                Toast.makeText(getContext(),"You cancelled the scanner",Toast.LENGTH_SHORT).show();

            }
            else
            {

                Log.d(TAG, "onActivityResult: in else statment");

                String pID = result.getContents();

                Log.d(TAG, "onActivityResult: PID = "+pID);

                db.setPatientID(pID);

                Toast.makeText(getContext(),"Patient ID: "+ pID,Toast.LENGTH_SHORT).show();


                Fragment frag = new TestDescriptionFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();


                Toast.makeText(getContext(),result.getContents(),Toast.LENGTH_SHORT).show();

            }


        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }



    }
}
