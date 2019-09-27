package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Database.DeliverClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PharmacyAdminPendingDelivery extends Fragment {
    PharmacyAdminPendingDeliveryAdapter adapter;
    ArrayList<DeliverClass> list = new ArrayList<>();
    ListView listView;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_pharmacy_admin_pending_delivery, container, false);
        ((PharmacyAdmin)getActivity()).getSupportActionBar().setTitle("Pending Deliveries");

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
                    DeliverClass item;
                    item = postSnapShot.getValue(DeliverClass.class);

                   // Log.i("error test 2",item.getPhonenumber()+"");
                    list.add(item);

                }
                try {
                    adapter = new PharmacyAdminPendingDeliveryAdapter(getContext(),list);
                    listView.setAdapter(adapter);
                    va.cancel();
                }catch (NullPointerException e){
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
