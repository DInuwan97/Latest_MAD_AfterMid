package com.example.myapplication.NotificationService;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.Database.DBHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "mFirebaseIIDService";
    //private static final String SUBSCRIBE_TO = "userABC";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        /*
          This method is invoked whenever the token refreshes
          OPTIONAL: If you want to send messages to this application instance
          or manage this apps subscriptions on the server side,
          you can send this token to your server.
        */
        String username= DBHandler.getLoggedUserName();
        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Delivery");
        Query query = dbref.orderByChild("userName").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                    String SUBSCRIBE_TO = dbref.getKey();
                    Log.i(TAG, "onTokenRefresh completed with token: " + SUBSCRIBE_TO);
                    FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Once the token is generated, subscribe to topic with the userId
        //FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO);
        Log.i(TAG, "onTokenRefresh completed with token: " + token);
    }
}