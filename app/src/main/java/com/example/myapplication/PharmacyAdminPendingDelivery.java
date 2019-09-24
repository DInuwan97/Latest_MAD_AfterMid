package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.Database.DeliverClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PharmacyAdminPendingDelivery extends Fragment {

    ArrayList<DeliverClass> list = new ArrayList<>();
    ListView listView;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_pharmacy_admin_pending_delivery, container, false);


        listView = v.findViewById(R.id.listAdminPendingDelivery);



        AlertDialog.Builder alert2 = new AlertDialog.Builder(getContext());
        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        View v123 = inflater2.inflate(R.layout.pharmacy_cart_loading, null);

        alert2.setView(v123);
        alert2.setCancelable(false);
        final AlertDialog va = alert2.create();
        va.setCanceledOnTouchOutside(false);

        va.show();

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Delivery");
        Query query = dbref.orderByChild("status").equalTo(0);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    /*DeliverClass item = new DeliverClass();
                    String id = postSnapShot.child("id").toString();
                    try {
                        item.setId(Integer.parseInt(id));

                    } catch (NumberFormatException e) {

                        e.printStackTrace();
                    }
                    item.setAddress(postSnapShot.child("address").toString());
                    item.setEmail(postSnapShot.child("email").toString());
                    item.setItemNames(postSnapShot.child("itemNames").toString());
                    item.setItemsAmount(postSnapShot.child("itemsAmoint").toString());
                    try {
                        item.setStatus(Integer.parseInt(postSnapShot.child("status").toString()));
                    } catch (NumberFormatException e) {
                        Log.i("error test 2", postSnapShot.child("status").toString());
                        e.printStackTrace();
                    }
                    try {
                        item.setTotalprice(Float.parseFloat(postSnapShot.child("totalprice").toString()));
                    } catch (NumberFormatException e) {
                        Log.i("error test 2",postSnapShot.child("totalprice").toString());
                        e.printStackTrace();
                    }
                    item.setUserName(postSnapShot.child("userName").toString());
*/                  DeliverClass item;
                    item = postSnapShot.getValue(DeliverClass.class);

                    Log.i("error test 2",item.getPhonenumber()+"");
                    list.add(item);

                }
                PharmacyAdminDeliveryPendingAdapter adapter = new PharmacyAdminDeliveryPendingAdapter(getContext(),list);
                listView.setAdapter(adapter);
                va.cancel();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }


}
