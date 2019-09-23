package com.example.myapplication;

import android.os.Bundle;

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

import com.example.myapplication.Database.DBHandler;

import java.util.ArrayList;


public class PharmacyMedicineList extends Fragment {
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isResumed()){
            if(isVisibleToUser) {
                adapter.notifyDataSetChanged();
            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint()) {
            adapter.notifyDataSetChanged();
        }
    }
    ArrayAdapter adapter;
    DataPassListener mCallback;

    public interface DataPassListener{
        public void passData(String data);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_pharmacy_medicine_list, container, false);

        final DBHandler dh = new DBHandler(getActivity().getApplicationContext());

        ArrayList<String> MedicineList = dh.selectAll();
        adapter= new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.list_medicine_item,MedicineList);

        adapter.notifyDataSetChanged();
        final ListView listView = v.findViewById(R.id.pharmacyListMedicine);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = adapter.getItem(position).toString();


                Fragment medicineDetails = new pharmacyMedicineItemDetails();
                Bundle args = new Bundle();
                args.putString(pharmacyMedicineItemDetails.DATA_RECIEVE, pos);
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

                        /*ArrayList<String> list = dh.selectSome(searchTxt);
                        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                                R.layout.list_medicine_item, list);
                        listView.setAdapter(adapter);*/

                        adapter.getFilter().filter(searchTxt);
                }

                return false;
            }
        });

        adapter.notifyDataSetChanged();
        return v;
    }


}
