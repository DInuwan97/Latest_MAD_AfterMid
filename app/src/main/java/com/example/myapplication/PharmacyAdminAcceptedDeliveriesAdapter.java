package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.DeliverClass;

import java.util.ArrayList;

public class PharmacyAdminAcceptedDeliveriesAdapter extends ArrayAdapter<DeliverClass> {
    private LayoutInflater layoutInflater;
    private ArrayList<DeliverClass> itemArray;
    Context con;



    public PharmacyAdminAcceptedDeliveriesAdapter(Context context, ArrayList<DeliverClass> data) {
        super(context, R.layout.list_pharmacy_admin_accepted_delivery_item,data);
        this.layoutInflater = LayoutInflater.from(context);
        itemArray = data;
        con= context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        final DeliverClass item = getItem(position);

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_pharmacy_admin_accepted_delivery_item,parent,false);
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
        viewHolder.txtAcceptedDate.setText(item.getAcceptDateTime());


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new PharmacyAdminAcceptedDeliveriesDetails();
                Bundle args = new Bundle();
                args.putInt(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_ID,item.getId());
                args.putString(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_NAME,item.getUserName());
                args.putString(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_EMAIL,item.getEmail());
                args.putInt(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_STATUS,item.getStatus());
                args.putString(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_ADDRESS,item.getAddress());
                args.putInt(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_PHONENUMBER,item.getPhonenumber());
                args.putString(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_ITEMNAMES,item.getItemNames());
                args.putString(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_ITEMSAMOUNTS,item.getItemsAmount());
                args.putFloat(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_TOTALPRICE,item.getTotalprice());
                args.putString(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_DATETIME,item.getDateTime());
                args.putString(PharmacyAdminAcceptedDeliveriesDetails.DATA_RECIEVE_ACCEPTDATETIME,item.getAcceptDateTime());


                fragment.setArguments(args);

                ((PharmacyAdmin)con).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment
                ).commit();
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
        TextView txtAcceptedDate;
        CardView cardView;


        public ViewHolder(View view){
            txtName = view.findViewById(R.id.txtUserName);
            txtAddress = view.findViewById(R.id.txtAddress);
            txtPhoneNo = view.findViewById(R.id.txtAddress);
            txtDateTime = view.findViewById(R.id.txtViewDateTime);
            txtTotal = view.findViewById(R.id.txtTotalAmount);
            txtAcceptedDate = view.findViewById(R.id.txtViewAccepted);
            cardView = view.findViewById(R.id.Card);
        }

    }


}
