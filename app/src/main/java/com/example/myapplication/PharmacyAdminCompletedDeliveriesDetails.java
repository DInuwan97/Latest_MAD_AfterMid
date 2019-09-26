package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Database.AdminDeliveryItemClass;
import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.DeliverClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PharmacyAdminCompletedDeliveriesDetails extends Fragment {


    final static String DATA_RECIEVE_ID                          = "datarecieveid";
    final static String DATA_RECIEVE_NAME                = "datarecievename";
    final static String DATA_RECIEVE_ADDRESS         = "datarecieveaddress";
    final static String DATA_RECIEVE_EMAIL          = "datarecieveemail";
    final static String DATA_RECIEVE_STATUS           = "datarecievestatus";
    final static String DATA_RECIEVE_PHONENUMBER    = "datarecievephonenumber";
    final static String DATA_RECIEVE_ITEMNAMES       = "datarecieveitemnames";
    final static String DATA_RECIEVE_ITEMSAMOUNTS    = "datarecieveitemamounts";
    final static String DATA_RECIEVE_TOTALPRICE      = "datarecievetotoalprice";
    final static String DATA_RECIEVE_DATETIME        = "datarecievedatetime";
    final static String DATA_RECIEVE_ACCEPTDATETIME        = "datarecieveacceptdatetime";
    final static String DATA_RECIEVE_DELIVERDATETIME        = "datarecievedeliverdatetime";


    TextView UserName ;
    TextView UserEmail ;
    TextView UserPhone ;
    TextView UserAddress ;
    TextView Status ;
    TextView Time ;
    TextView AcceptDate;
    TextView DeliverDate;
    TextView TotalPrice ;
    ListView list;

    Button btnDelete;
    DeliverClass item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_pharmacy_admin_completed_deliveries_details, container, false);
        ((PharmacyAdmin)getActivity()).getSupportActionBar().setTitle("Completed Delivery Details");


        UserName = v.findViewById(R.id.txtViewUserName);
        UserEmail = v.findViewById(R.id.txtViewUserEmail);
        UserPhone = v.findViewById(R.id.txtViewPhone);
        UserAddress = v.findViewById(R.id.txtViewAddress);
        Status = v.findViewById(R.id.txtViewStatus);
        Time = v.findViewById(R.id.txtViewDateTime);
        AcceptDate = v.findViewById(R.id.txtViewAcceptedDate);
        DeliverDate = v.findViewById(R.id.txtViewCompletedDate);
        TotalPrice = v.findViewById(R.id.txtViewTotal);

        list = v.findViewById(R.id.AdminAcceptedList);

        btnDelete = v.findViewById(R.id.btnDelete);

        Bundle args = getArguments();
        item = new DeliverClass();
        item.setId(args.getInt(DATA_RECIEVE_ID));
        item.setUserName(args.getString(DATA_RECIEVE_NAME));
        item.setAddress(args.getString(DATA_RECIEVE_ADDRESS));
        item.setEmail(args.getString(DATA_RECIEVE_EMAIL));
        item.setStatus(args.getInt(DATA_RECIEVE_STATUS));
        item.setPhonenumber(args.getInt(DATA_RECIEVE_PHONENUMBER));
        item.setItemNames(args.getString(DATA_RECIEVE_ITEMNAMES));
        item.setItemsAmount(args.getString(DATA_RECIEVE_ITEMSAMOUNTS));
        item.setTotalprice(args.getFloat(DATA_RECIEVE_TOTALPRICE));
        item.setDateTime(args.getString(DATA_RECIEVE_DATETIME));
        item.setAcceptDateTime(args.getString(DATA_RECIEVE_ACCEPTDATETIME));
        item.setDeliveredDateTime(args.getString(DATA_RECIEVE_DELIVERDATETIME));



        //Log.i("testindadasd",item.getAddress()+"");
        UserName.setText(item.getUserName());
        UserEmail.setText(item.getEmail());
        UserPhone.setText("+" + item.getPhonenumber());
        UserAddress.setText(item.getAddress());
        if (item.getStatus() == 4) {
            Status.setText("Delivery Not Accepted");
        } else if (item.getStatus() == 3) {
            Status.setText("Delivery Accepted");
        } else if (item.getStatus() == 2) {
            Status.setText("Delivery Canceled by the User");
        } else if(item.getStatus()==1){
            Status.setText("Delivery Completed");
        } else if(item.getStatus() == 0){
            Status.setText("Delivery Pending");
        }
        Time.setText(item.getDateTime());
        TotalPrice.setText("Rs. "+item.getTotalprice());
        AcceptDate.setText(item.getAcceptDateTime());
        DeliverDate.setText(item.getDeliveredDateTime());

        String Names[] = TextUtils.split(item.getItemNames(),"#!");
        String Amounts[] = TextUtils.split(item.getItemsAmount(),"#!");
        ArrayList<AdminDeliveryItemClass> arrayList = new ArrayList<>();
        for(int i = 0; i<Names.length;i++){
            AdminDeliveryItemClass adminDeliveryItemClass = new AdminDeliveryItemClass();
            adminDeliveryItemClass.setName(Names[i]);
            adminDeliveryItemClass.setAmount(Amounts[i]);
            arrayList.add(adminDeliveryItemClass);
        }



        PharmacyAdminPendingDeliveryDetailsListItemAdapter adapter = new PharmacyAdminPendingDeliveryDetailsListItemAdapter(getContext(),arrayList);
        list.setAdapter(adapter);



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Delivery");
                Query query = dbref.orderByChild("userName").equalTo(item.getUserName());

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                            if(Integer.parseInt(postSnapShot.child("id").getValue().toString())==item.getId()) {

                                dbref.child(postSnapShot.getKey()).removeValue();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new PharmacyAdminCompletedDeliveries()).commit();



                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        return v;
    }

}
