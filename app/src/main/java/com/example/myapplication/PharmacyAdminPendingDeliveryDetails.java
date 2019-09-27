package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.Database.AdminDeliveryItemClass;
import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.DeliverClass;
import com.example.myapplication.NotificationService.MySingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import java.util.HashMap;
import java.util.Map;


public class PharmacyAdminPendingDeliveryDetails extends Fragment {


    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AIzaSyD_UjU_BbJZ9ZEuC2uz3QNXOJRSbm0a9Qc";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pharmacy_admin_pending_delivery_details, container, false);
        ((PharmacyAdmin)getActivity()).getSupportActionBar().setTitle("Pending Delivery Details");
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
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setTitle("CONFIRMATION").setMessage("Do you want to Accept the Delivery?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Delivery");
                        Query query = dbref.orderByChild("userName").equalTo(item.getUserName());

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                                    if(Integer.parseInt(postSnapShot.child("id").getValue().toString())==item.getId()) {

                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                                        String dateTime = simpleDateFormat.format(new Date());
                                        item.setAcceptDateTime(dateTime);
                                        dbref.child(postSnapShot.getKey()).child("AcceptDateTime").setValue(dateTime);

                                        dbref.child(postSnapShot.getKey()).child("status").setValue(3);
                                        dbref.child(postSnapShot.getKey()).child("acceptedby").setValue(DBHandler.getLoggedUserName());

                                        TOPIC = "/topics/"+postSnapShot.getKey(); //topic has to match what the receiver subscribed to
                                        NOTIFICATION_TITLE = "Delivery Notification";
                                        NOTIFICATION_MESSAGE = "Your Request has been accepted and will be delivered to you within 2 buisness days";

                                        JSONObject notification = new JSONObject();
                                        JSONObject notifcationBody = new JSONObject();
                                        try {
                                            notifcationBody.put("title", NOTIFICATION_TITLE);
                                            notifcationBody.put("message", NOTIFICATION_MESSAGE);

                                            notification.put("to", TOPIC);
                                            notification.put("data", notifcationBody);
                                        } catch (JSONException e) {
                                            Log.i(TAG, "onCreate: " + e.getMessage() );
                                        }
                                        sendNotification(notification);


                                        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_nav_menu);
                                        bottomNav.setSelectedItemId(R.id.nav_pending_delivery_tasks);





                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();


            }
        });


        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setTitle("CONFIRMATION").setMessage("Do you want to Deleete/Reject the Delivery?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Delivery");
                        Query query = dbref.orderByChild("userName").equalTo(item.getUserName());

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                                    if(Integer.parseInt(postSnapShot.child("id").getValue().toString())==item.getId()) {

                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                                        String dateTime = simpleDateFormat.format(new Date());
                                        item.setAcceptDateTime(dateTime);
                                        dbref.child(postSnapShot.getKey()).child("AcceptDateTime").setValue(dateTime);

                                        dbref.child(postSnapShot.getKey()).child("status").setValue(4);
                                        dbref.child(postSnapShot.getKey()).child("acceptedby").setValue(DBHandler.getLoggedUserName());
                                        TOPIC = "/topics/"+postSnapShot.getKey(); //topic has to match what the receiver subscribed to
                                        NOTIFICATION_TITLE = "Delivery Notification";
                                        NOTIFICATION_MESSAGE = "Sorry. Your Request has been Rejected.";

                                        JSONObject notification = new JSONObject();
                                        JSONObject notifcationBody = new JSONObject();
                                        try {
                                            notifcationBody.put("title", NOTIFICATION_TITLE);
                                            notifcationBody.put("message", NOTIFICATION_MESSAGE);

                                            notification.put("to", TOPIC);
                                            notification.put("data", notifcationBody);
                                        } catch (JSONException e) {
                                            Log.i(TAG, "onCreate: " + e.getMessage() );
                                        }
                                        sendNotification(notification);


                                        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_nav_menu);
                                        bottomNav.setSelectedItemId(R.id.nav_pending_delivery_tasks);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });
        return v;
    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
}
