package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.DeliverClass;

import java.util.ArrayList;

public class PharmacyDeliveryAdapter extends ArrayAdapter<DeliverClass> {
    private LayoutInflater layoutInflater;
    private ArrayList<DeliverClass> itemArray;
    Context con;

    public PharmacyDeliveryAdapter(Context context, ArrayList<DeliverClass> data) {
        super(context, R.layout.list_pharmacy_delivery_cart_item, data);
        this.layoutInflater = LayoutInflater.from(context);
        itemArray = data;
        con = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        final DeliverClass item = getItem(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_pharmacy_delivery_cart_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(item.getUserName());
        viewHolder.txtTotal.setText("Rs. " + item.getTotalprice());
        if(item.getStatus() == 0){
            viewHolder.txtStatus.setText("Pending");
        }else if(item.getStatus() == 1){
            viewHolder.txtStatus.setText("Recieved");
        }else if(item.getStatus() == 2){
            viewHolder.txtStatus.setText("Cancelled");
        }else if(item.getStatus() == 3){
            viewHolder.txtStatus.setText("Accepted");
        }else if(item.getStatus() == 4){
            viewHolder.txtStatus.setText("Rejected");
        }else {
            viewHolder.txtStatus.setText("Pending");
        }

        viewHolder.txtPhoneNo.setText(item.getPhonenumber() + "");
        viewHolder.txtAddress.setText(item.getAddress());


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new PharmacyDeliveryDetails();
                Bundle args = new Bundle();
                args.putInt(PharmacyDeliveryDetails.DATA_RECIEVE_ID, item.getId());
                args.putString(PharmacyDeliveryDetails.DATA_RECIEVE_NAME, item.getUserName());
                args.putString(PharmacyDeliveryDetails.DATA_RECIEVE_EMAIL, item.getEmail());
                args.putInt(PharmacyDeliveryDetails.DATA_RECIEVE_STATUS, item.getStatus());
                args.putString(PharmacyDeliveryDetails.DATA_RECIEVE_ADDRESS, item.getAddress());
                args.putInt(PharmacyDeliveryDetails.DATA_RECIEVE_PHONENUMBER, item.getPhonenumber());
                args.putString(PharmacyDeliveryDetails.DATA_RECIEVE_ITEMNAMES, item.getItemNames());
                args.putString(PharmacyDeliveryDetails.DATA_RECIEVE_ITEMSAMOUNTS, item.getItemsAmount());
                args.putFloat(PharmacyDeliveryDetails.DATA_RECIEVE_TOTALPRICE, item.getTotalprice());
                args.putString(PharmacyDeliveryDetails.DATA_RECIEVE_DATETIME, item.getDateTime());
                args.putString(PharmacyDeliveryDetails.DATA_RECIEVE_ACCEPTEDBY, item.getAcceptedby());
                args.putString(PharmacyDeliveryDetails.DATA_RECIEVE_ACCEPTDATETIME, item.getAcceptDateTime());
                args.putString(PharmacyDeliveryDetails.DATA_RECIEVE_DELIVERDATETIME, item.getDeliveredDateTime());
                args.putString(PharmacyDeliveryDetails.DATA_RECIEVE_KEY, item.getKey());
                Log.i("PharmacyDeliveryAdapter",item.getKey());

                fragment.setArguments(args);

                ((PatientBottomNavigationActivity) con).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment
                ).commit();

            }
        });


        return convertView;
    }

    private class ViewHolder {
        TextView txtName;
        TextView txtAddress;
        TextView txtPhoneNo;
        TextView txtStatus;
        TextView txtTotal;
        CardView cardView;


        public ViewHolder(View view) {
            txtName = view.findViewById(R.id.txtUserName);
            txtAddress = view.findViewById(R.id.txtAddress);
            txtPhoneNo = view.findViewById(R.id.txtPhoneNo);
            txtStatus = view.findViewById(R.id.txtViewStatus);
            txtTotal = view.findViewById(R.id.txtTotalAmount);
            cardView = view.findViewById(R.id.Card);
        }

    }
}
