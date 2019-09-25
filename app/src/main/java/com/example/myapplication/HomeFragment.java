package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.DBHandler;

public class HomeFragment extends Fragment {
    @Nullable

    DBHandler myDb;
    Button btnAddNew;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v =  inflater.inflate(R.layout.fragment_home, container, false);

        myDb = new DBHandler(getActivity().getApplicationContext());

      //  btnAddNew = (Button) v.findViewById(R.id.addNew);







       return v;

    }


}
