package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.DeliverClass;
import com.example.myapplication.NotificationService.MySingleton;
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

public class PharmacyAdminPendingDeliveryAdapter extends ArrayAdapter<DeliverClass> {
    private LayoutInflater layoutInflater;
    private ArrayList<DeliverClass> itemArray;
    Context con;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AIzaSyD_UjU_BbJZ9ZEuC2uz3QNXOJRSbm0a9Qc";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;


    public PharmacyAdminPendingDeliveryAdapter(Context context, ArrayList<DeliverClass> data) {
        super(context, R.layout.list_pharmacy_admin_pending_delivery_item,data);
        this.layoutInflater = LayoutInflater.from(context);
        itemArray = data;
        con= context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        final DeliverClass item = getItem(position);

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_pharmacy_admin_pending_delivery_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.txtName.setText(item.getUserName());
        viewHolder.txtTotal.setText("Rs. "+item.getTotalprice());
        viewHolder.txtDateTime.setText(item.getDateTime());
        viewHolder.txtPhoneNo.setText(item.getPhonenumber()+"");
        viewHolder.txtAddress.setText(item.getAddress());


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new PharmacyAdminPendingDeliveryDetails();
                Bundle args = new Bundle();
                args.putInt(PharmacyAdminPendingDeliveryDetails.DATA_RECIEVE_ID,item.getId());
                args.putString(PharmacyAdminPendingDeliveryDetails.DATA_RECIEVE_NAME,item.getUserName());
                args.putString(PharmacyAdminPendingDeliveryDetails.DATA_RECIEVE_EMAIL,item.getEmail());
                args.putInt(PharmacyAdminPendingDeliveryDetails.DATA_RECIEVE_STATUS,item.getStatus());
                args.putString(PharmacyAdminPendingDeliveryDetails.DATA_RECIEVE_ADDRESS,item.getAddress());
                args.putInt(PharmacyAdminPendingDeliveryDetails.DATA_RECIEVE_PHONENUMBER,item.getPhonenumber());
                args.putString(PharmacyAdminPendingDeliveryDetails.DATA_RECIEVE_ITEMNAMES,item.getItemNames());
                args.putString(PharmacyAdminPendingDeliveryDetails.DATA_RECIEVE_ITEMSAMOUNTS,item.getItemsAmount());
                args.putFloat(PharmacyAdminPendingDeliveryDetails.DATA_RECIEVE_TOTALPRICE,item.getTotalprice());
                args.putString(PharmacyAdminPendingDeliveryDetails.DATA_RECIEVE_DATETIME,item.getDateTime());


                fragment.setArguments(args);

                ((PharmacyAdmin)con).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment
                        ).commit();
            }
        });

        viewHolder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                dbref.child(postSnapShot.getKey()).child("status").setValue(3);
                                dbref.child(postSnapShot.getKey()).child("AcceptDateTime").setValue(dateTime);

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


                                remove(item);
                                notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        viewHolder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("CONFIRMATION").setMessage("Do you want to Delete/Reject the request?");
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



                                        remove(item);
                                        notifyDataSetChanged();
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


        return convertView;
    }
    private class ViewHolder{
        TextView txtName;
        TextView txtAddress;
        TextView txtPhoneNo;
        TextView txtDateTime;
        TextView txtTotal;
        Button btnReject;
        Button btnAccept;
        CardView cardView;
        public ViewHolder(View view){
            txtName = view.findViewById(R.id.txtUserName);
            txtAddress = view.findViewById(R.id.txtAddress);
            txtPhoneNo = view.findViewById(R.id.txtPhoneNo);
            txtDateTime = view.findViewById(R.id.txtViewDateTime);
            txtTotal = view.findViewById(R.id.txtTotalAmount);
            btnReject = view.findViewById(R.id.btnReject);
            btnAccept = view.findViewById(R.id.btnAccept);
            cardView = view.findViewById(R.id.Card);
        }

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
