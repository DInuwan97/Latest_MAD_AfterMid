package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.AdminDeliveryItemClass;
import com.example.myapplication.Database.DeliverClass;

import java.util.ArrayList;

public class PharmacyAdminPendingDeliveryDetailsListItemAdapter extends ArrayAdapter<AdminDeliveryItemClass> {


    private LayoutInflater layoutInflater;
    public PharmacyAdminPendingDeliveryDetailsListItemAdapter(Context context, ArrayList<AdminDeliveryItemClass> data) {
        super(context, R.layout.list_pharmacy_pending_delivery_details_medicine_list_item,data);


        this.layoutInflater = LayoutInflater.from(context);


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        AdminDeliveryItemClass item = getItem(position);

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_pharmacy_pending_delivery_details_medicine_list_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.txtNameMedicine.setText(item.getName());
        viewHolder.txtAmount.setText("Rs. "+item.getAmount());

        return convertView;
    }
    private class ViewHolder{
        TextView txtNameMedicine;
        TextView txtAmount;

        public ViewHolder(View view){
            txtNameMedicine = view.findViewById(R.id.txtViewMedicineName);
            txtAmount = view.findViewById(R.id.txtViewMedicineAmount);

        }

    }
}
