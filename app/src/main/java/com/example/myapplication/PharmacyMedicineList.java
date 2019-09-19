package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.Database.DBHandler;

import java.util.ArrayList;


public class PharmacyMedicineList extends Fragment {

    DataPassListener mCallback;

    public interface DataPassListener{
        public void passData(String data);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_pharmacy_medicine_list, container, false);

        DBHandler dh = new DBHandler(getActivity().getApplicationContext());

        ArrayList<String> MedicineList = dh.selectAll();
        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.list_medicine_item,MedicineList);

        ListView listView = v.findViewById(R.id.pharmacyListMedicine);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = adapter.getItem(position).toString();
                Log.i("testing",pos);

                Fragment medicineDetails = new pharmacyMedicineItemDetails();
                Bundle args = new Bundle();
                args.putString(pharmacyMedicineItemDetails.DATA_RECIEVE, pos);
                medicineDetails.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,medicineDetails).commit();



            }
        });

        return v;
    }


}
