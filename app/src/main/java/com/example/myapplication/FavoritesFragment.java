package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {
    DBHandler myDB;
    private ListView listView;
    private SystemUsersAdapter adapterSystemUsers;
    private List<SystemUsers> mSystemUsersList;

    private Button btnViewPatientDetails;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        myDB = new DBHandler(getActivity().getApplicationContext());

        listView = (ListView)v.findViewById(R.id.listView1);
        myDB = new DBHandler(getActivity().getApplicationContext());

       ArrayList <String> theList = new ArrayList<>();

        Cursor data = myDB.getListContents();


         mSystemUsersList = new ArrayList<>();

       /*mSystemUsersList.add(new SystemUsers(1,"Dinuwan","dinuwan@gmail.com","Employee"));
        mSystemUsersList.add(new SystemUsers(2,"Dinuwan","dinuwan@gmail.com","Employee1"));
        mSystemUsersList.add(new SystemUsers(3,"Dinuwan","dinuwan@gmail.com","Employee2"));
        mSystemUsersList.add(new SystemUsers(4,"Dinuwan","dinuwan@gmail.com","Employee4"));
        adapterSystemUsers = new SystemUsersAdapter(getContext(),mSystemUsersList);
        listView.setAdapter(adapterSystemUsers);*/


      if(data.getCount() == 0){
            Toast.makeText(getActivity().getApplicationContext(),"The Table was Empty",Toast.LENGTH_LONG).show();
        }else{
            //Toast.makeText(getActivity().getApplicationContext(),"The Table was Not Empty",Toast.LENGTH_LONG).show();
            while(data.moveToNext()){
               /*Toast.makeText(getActivity().getApplicationContext(),"The Table was Not Empty",Toast.LENGTH_LONG).show();

                theList.add(data.getString(1));

                ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        theList);
                listView.setAdapter(listAdapter);*/

              // mSystemUsersList = new ArrayList<>();
               mSystemUsersList.add(new SystemUsers(data.getInt(0),data.getString(1),data.getString(2),data.getString(3)));
               adapterSystemUsers = new SystemUsersAdapter(getContext(),mSystemUsersList);
               listView.setAdapter(adapterSystemUsers);

            }
        }

      btnViewPatientDetails = (Button) v.findViewById(R.id.buttonView);

      /*btnViewPatientDetails.setOnClickListener(new View.OnClickListener(){

          @Override
          public void onClick(View view) {
             // Intent intent1 = new Intent(getActivity().getApplicationContext(),PatientDetails.class);
              //startActivity(intent1);
          }
      });*/

 




        return v;
    }


    public void showPatientDetailsFragment(View view){
        Intent intent1 = new Intent(getActivity().getApplicationContext(),PatientDetails.class);
        startActivity(intent1);
    }
}
