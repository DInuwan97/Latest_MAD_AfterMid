package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class pharmacyAdminMedicineList extends Fragment {
    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ((PharmacyAdmin)getActivity()).getSupportActionBar().setTitle("Pharmacy Admin");
        final View v = inflater.inflate(R.layout.fragment_pharmacy_medicine_list, container, false);

        final DBHandler dh = new DBHandler(getActivity().getApplicationContext());

        ArrayList<String> MedicineList = dh.selectAll();
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.list_medicine_item,MedicineList);



        final ListView listView = v.findViewById(R.id.pharmacyListMedicine);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        listView.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = adapter.getItem(position).toString();


                Fragment medicineDetails = new PharmacyAdminAddMedicine();
                Bundle args = new Bundle();
                args.putString(PharmacyAdminAddMedicine.DATA_RECIEVE, pos);
                medicineDetails.setArguments(args);

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,medicineDetails).commit();



            }
        });

        final EditText editTextSearch = v.findViewById(R.id.pharmacySearchListMedicineEditText);

        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String searchTxt = editTextSearch.getText().toString();

                if(searchTxt != "") {

                    adapter.getFilter().filter(searchTxt);

                }

                return false;
            }
        });

        adapter.notifyDataSetChanged();
        return v;
    }



}
