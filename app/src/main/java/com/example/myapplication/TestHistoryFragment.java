package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.myapplication.Database.DBHandler;

import java.util.ArrayList;


public class TestHistoryFragment extends Fragment {

    private static final String TAG = "Test History";

    ListView listView;
    DBHandler db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_test_history,container,false);
        ((TestActivity)getActivity()).getSupportActionBar().setTitle("History");


        db = new DBHandler(getActivity().getApplicationContext());

        listView = (ListView)v.findViewById(R.id.preTestLIstView);

        Log.d(TAG, "onCreateView: started onCreaet View");


        showListView();








        return v;

    }

    public void showListView()
    {

        Log.d(TAG, "showListView: Started");

        //get table

        String pid = db.getPatientID();


        ArrayList<String> listData = new ArrayList<String>();

        Cursor testlist = db.getTestsHistory();

        if (testlist.moveToFirst()){



            do{

                listData.add(testlist.getString(testlist.getColumnIndex("test_name")));



            }while(testlist.moveToNext());
        }
        testlist.close();
        Log.d(TAG, "showListView: ended list fetching");

        ListAdapter adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listData);

        listView.setAdapter(adapter);

        Log.d(TAG, "showListView: adapter run");




    }
}
