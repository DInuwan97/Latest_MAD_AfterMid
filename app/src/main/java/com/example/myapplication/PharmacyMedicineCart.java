package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.DeliverClass;
import com.example.myapplication.Database.MedicineItemClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PharmacyMedicineCart extends Fragment{
    AlertDialog.Builder builder;
    cartListAdapter cartListAdap;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((PatientBottomNavigationActivity)getActivity()).getSupportActionBar().setTitle("Medicine Cart");
        View v = inflater.inflate(R.layout.fragment_pharmacy_medicine_cart, container, false);

        DBHandler db = new DBHandler(getContext());
        final ArrayList<MedicineItemClass> cartList = db.selectAllCart();

        ListView listView = v.findViewById(R.id.cartListMedicine);


         cartListAdap = new cartListAdapter(getActivity().getApplicationContext(), cartList);

        listView.setAdapter(cartListAdap);

        final TextView totalAmoutTextView = v.findViewById(R.id.txtPharmacyCartTotal);
        final DBHandler dbHandler = new DBHandler(getContext());
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

                alert.setMessage("Fill the details correctly.\nYou can't edit the details later.");
                alert.setTitle("Enter Details");



                LayoutInflater inflater = getActivity().getLayoutInflater();
                View v12  = inflater.inflate(R.layout.pharmacy_cart_user_info,null);
                final EditText editTextName = v12.findViewById(R.id.editTextName);
                final EditText editTextAddress = v12.findViewById(R.id.editTextAddress);
                final EditText editTextEmail = v12.findViewById(R.id.editTextEmail);
                final EditText editTextPhone = v12.findViewById(R.id.editTextPhone);


               editTextEmail.setText(DBHandler.getLoggedUserEmail());
               editTextName.setText(DBHandler.getLoggedUserName());

               if(!TextUtils.equals(DBHandler.getLoggedUserAddress(),"NULL")){
                   editTextAddress.setText(DBHandler.getLoggedUserAddress());
               }
               if(!TextUtils.equals(DBHandler.getLoggedUserMobile(),"NULL")){
                   editTextPhone.setText(DBHandler.getLoggedUserMobile());
               }



                alert.setView(v12);

                alert.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog1, int whichButton) {
                        int phone;
                        String email;
                        String address;
                        String name;

                        try {
                            phone       = Integer.parseInt(editTextPhone.getText().toString());
                            email      = editTextEmail.getText().toString();
                            address       = editTextAddress.getText().toString();
                            name         = editTextName.getText().toString();


                            if(TextUtils.isEmpty(email)||TextUtils.isEmpty(address)||TextUtils.isEmpty(name)){
                                Toast.makeText(getContext(),"Enter Valid Details",Toast.LENGTH_SHORT).show();
                                dialog1.cancel();
                            }else {

                                DBHandler db = new DBHandler(getContext());
                                ArrayList<MedicineItemClass> list = db.selectAllCart();
                                ArrayList<String> nameList = new ArrayList<>();
                                ArrayList<Float> amountList = new ArrayList<>();

                                for(MedicineItemClass item : list){
                                    String nameMedicine=item.getNameMedicine();
                                    nameList.add(nameMedicine);
                                    float amountMedicine=item.getAmount();
                                    amountList.add(amountMedicine);

                                }
                                String nameArray = TextUtils.join("#!",nameList);
                                String amountArray = TextUtils.join("#!",amountList);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                                String dateTime = simpleDateFormat.format(new Date());




                                final DeliverClass deliverItem = new DeliverClass();

                                deliverItem.setAddress(address);
                                deliverItem.setDateTime(dateTime);
                                deliverItem.setEmail(email);
                                deliverItem.setItemNames(nameArray);
                                deliverItem.setItemsAmount(amountArray);
                                deliverItem.setPhonenumber(phone);
                                deliverItem.setStatus(0);
                                deliverItem.setUserName(name);
                                deliverItem.setTotalprice(db.calculateCartTotal());

                                long id = db.addDeliver(deliverItem);

                                deliverItem.setId((int)id);


                                AlertDialog.Builder alert2 = new AlertDialog.Builder(getContext());
                                LayoutInflater inflater2 = getActivity().getLayoutInflater();
                                View v123 = inflater2.inflate(R.layout.pharmacy_cart_loading, null);

                                alert2.setView(v123);
                                alert2.setCancelable(false);
                                final AlertDialog va = alert2.create();
                                va.setCanceledOnTouchOutside(false);

                                va.show();

                                final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Delivery");
                                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        dbref.push().setValue(deliverItem);
                                        DBHandler db = new DBHandler(getContext());

                                        if(db.clearCart()){
                                            ((PatientBottomNavigationActivity)getActivity()).getSupportActionBar().setTitle("Medicine List");
                                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                    new PharmacyMedicineList()).commit();
                                        }else{
                                            Toast.makeText(getContext(),"Error. Clear the Cart Manually",Toast.LENGTH_SHORT).show();
                                            cartListAdap.notifyDataSetChanged();
                                        }
                                        va.cancel();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });





                            }
                        }catch(NumberFormatException e){
                            Toast.makeText(getContext(),"Enter Valid Phone Number",Toast.LENGTH_SHORT).show();
                            dialog1.cancel();
                        }

                    }
                });

                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                    }
                });


                DBHandler db = new DBHandler(getContext());

                if(!db.checkCartIsEmpty()) {
                    alert.show();
                }else{
                    Toast.makeText(getContext(),"Cart is Empty. Add items First.",Toast.LENGTH_SHORT).show();
                }


            }
        });




        return v;
    }


}
