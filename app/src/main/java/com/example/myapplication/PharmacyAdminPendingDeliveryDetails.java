package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

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
import com.example.myapplication.Database.DeliverClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class PharmacyAdminPendingDeliveryDetails extends Fragment {

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

    TextView UserName ;
    TextView UserEmail ;
    TextView UserPhone ;
    TextView UserAddress ;
    TextView Status ;
    TextView Time ;
    TextView TotalPrice ;
    ListView list;


    Button btnAccept;
    Button btnReject;
    DeliverClass item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pharmacy_admin_pending_delivery_details, container, false);

        UserName = v.findViewById(R.id.txtViewUserName);
        UserEmail = v.findViewById(R.id.txtViewUserEmail);
        UserPhone = v.findViewById(R.id.txtViewPhone);
        UserAddress = v.findViewById(R.id.txtViewAddress);
        Status = v.findViewById(R.id.txtViewStatus);
        Time = v.findViewById(R.id.txtViewDateTime);
        TotalPrice = v.findViewById(R.id.txtViewTotal);
        list = v.findViewById(R.id.AdminPendingList);

        btnAccept = v.findViewById(R.id.btnAccept);
        btnReject = v.findViewById(R.id.btnReject);


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


        Log.i("testindadasd",item.getAddress()+"");
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



        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Delivery");
                Query query = dbref.orderByChild("userName").equalTo(item.getUserName());

            }
        });


        return v;
    }

}
