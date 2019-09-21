package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.MedicineItemClass;

import java.util.ArrayList;


public class PharmacyMedicineCart extends Fragment{
    AlertDialog.Builder builder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_pharmacy_medicine_cart, container, false);

        DBHandler db = new DBHandler(getContext());
        ArrayList<MedicineItemClass> cartList = db.selectAllCart();

        ListView listView = v.findViewById(R.id.cartListMedicine);


        cartListAdapter cartListAdap = new cartListAdapter(getActivity().getApplicationContext(), cartList);

        listView.setAdapter(cartListAdap);

        final TextView totalAmoutTextView = v.findViewById(R.id.txtPharmacyCartTotal);
        DBHandler dbHandler = new DBHandler(getContext());
        float totalAmount = dbHandler.calculateCartTotal();
        String strTotoalAmount = "Total Price : Rs. " + totalAmount;
        totalAmoutTextView.setText(strTotoalAmount);

        try {
            Thread t = new Thread() {

                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(100);
                            if(getActivity()==null){
                                return;
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        DBHandler dbHandler = new DBHandler(getContext());
                                        float totalAmount = dbHandler.calculateCartTotal();
                                        String strTotoalAmount = "Total Price : Rs. " + totalAmount;
                                        totalAmoutTextView.setText(strTotoalAmount);
                                    }catch (NullPointerException e){

                                    }
                                }
                            });
                        }
                    } catch (InterruptedException e) {

                    }
                }
            };

            t.start();
        } catch (NullPointerException e) {


        } catch (Exception e){

        }


        Button btnOrderItems = v.findViewById(R.id.btnPharmacyCartBuy);


        builder = new AlertDialog.Builder(getContext());

        btnOrderItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*builder.setMessage("Do you want to Buy the Selected Items ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Check Out");
                alert.show();*/

                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(getContext());
                alert.setMessage("Enter Address to Deliver");
                alert.setTitle("Address");

                alert.setView(edittext);

                alert.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String YouEditTextValue = edittext.getText().toString();
                        Log.i("testing address",YouEditTextValue);

                    }
                });

                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                    }
                });


                alert.show();



            }
        });
        return v;
    }


}
