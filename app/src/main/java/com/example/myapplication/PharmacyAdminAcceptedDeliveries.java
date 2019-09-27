package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.DeliverClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PharmacyAdminAcceptedDeliveries extends Fragment {


    PharmacyAdminAcceptedDeliveriesAdapter adapter;
    ArrayList<DeliverClass> list = new ArrayList<>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_pharmacy_admin_accepted_deliveries, container, false);
        ((PharmacyAdmin)getActivity()).getSupportActionBar().setTitle("Accepted Deliveries");

        listView = v.findViewById(R.id.listAcceptedDelivery);


        AlertDialog.Builder alert2 = new AlertDialog.Builder(getContext());
        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        View v123 = inflater2.inflate(R.layout.pharmacy_cart_loading, null);

        alert2.setView(v123);
        alert2.setCancelable(false);
        final AlertDialog va = alert2.create();
        va.setCanceledOnTouchOutside(false);

        va.show();


        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Delivery");
        Query query = dbref.orderByChild("status").equalTo(3);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    if(TextUtils.equals(postSnapShot.child("acceptedby").getValue().toString(), DBHandler.getLoggedUserName())) {
                        DeliverClass item;

                        item = postSnapShot.getValue(DeliverClass.class);

                        list.add(item);
                    }

                }
                try{
                    adapter = new PharmacyAdminAcceptedDeliveriesAdapter(getContext(),list);
                    listView.setAdapter(adapter);
                    va.cancel();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Error. Restart the Application.",Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

}
