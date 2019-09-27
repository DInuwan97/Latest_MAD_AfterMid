package com.example.myapplication;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.DBHandler;

import java.util.HashMap;
import java.util.List;

public class AddNewAppoinment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_new_appointment, container, false);

        final DBHandler db = new DBHandler(getActivity().getApplicationContext());

        //ArrayList<String> DoctorList = db.selectAllDocs();
        List<HashMap<String, String>> listItems = db.selectAllDocs();
        final SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),listItems,
                R.layout.list_doctors,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.doc_name, R.id.doc_spec});

        final ListView listView = (ListView) view.findViewById(R.id.doc_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = adapter.getItem(position).toString();


                Fragment DocAvailability = new DocAvailability();
                Bundle args = new Bundle();
                //args.putString(PharmacyAdminAddMedicine.DATA_RECIEVE, pos);
               // DocAvailability.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,DocAvailability).commit();
            }
        });

        final EditText editTextSearch = view.findViewById(R.id.doc_search);

        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String searchTxt = editTextSearch.getText().toString();

                if(searchTxt != "") {

                    List<HashMap<String, String>> listItems = db.selectSomeDocs(searchTxt);
                    final SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),listItems,
                            R.layout.list_doctors,
                            new String[]{"First Line", "Second Line"},
                            new int[]{R.id.doc_name, R.id.doc_spec});
                    listView.setAdapter(adapter);
                }

                return false;
            }
        });


        return view;
    }






}

