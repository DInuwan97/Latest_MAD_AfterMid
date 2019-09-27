package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.MedicineItemClass;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class PharmacyAdminAddMedicine extends Fragment {
    public static final String DATA_RECIEVE = "data_recieve";
    private static final int GALLERY_REQUEST = 1888;
    View v;
    private static final int CAMERA_REQUEST = 1999;
    ImageView   imageView           ;
    Button      btnGallery          ;
    Button      btnCamera           ;
    EditText    editTextName        ;
    EditText    editPricePerItem    ;
    EditText    editItemType        ;
    EditText    editDescription     ;
    EditText    editUsage           ;
    EditText    editIngredients     ;
    EditText    editSideEffects     ;
    Button      btnClear            ;
    Button      btnAdd              ;
    Button      btnDelete           ;
    MedicineItemClass item;
    MedicineItemClass item2;
    byte[] imageByte;
    String imgStr;
    Boolean imageChanged= false;


    Drawable editTextBackground;
    DatabaseReference DBRef;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST) {

            Bitmap bitmap;

            try {
                Uri target =data.getData();
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(target));
               // imageView.setImageBitmap(bitmap);
                int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                imageView.setImageBitmap(scaled);

                imageChanged = true;


            }catch (FileNotFoundException e){
                Log.i("testing file","Filenot found exception");
                e.printStackTrace();
            }catch (Exception e){
                Log.i("testing file"," exception");
                e.printStackTrace();
            }
        }
        if(requestCode == CAMERA_REQUEST){

            Bitmap bitmap;
            try {
                /*Uri target =data.getData();
                bitmap =BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(target));
                int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                imageView.setImageBitmap(scaled);*/


                Bundle extras = data.getExtras();
                bitmap = (Bitmap)extras.get("data");
                imageView.setImageBitmap(bitmap);
                imageChanged = true;


            }catch (Exception e){
                Log.i("testing file"," exception camrea");
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pharmacy_admin_add_medicine, container, false);

        ((PharmacyAdmin)getActivity()).getSupportActionBar().setTitle("Add Medicine");
        imageView           =v.findViewById(R.id.medicineImageView);
        btnGallery          =v.findViewById(R.id.btnGallery);
        btnCamera           =v.findViewById(R.id.btnCamera);
        editTextName        =v.findViewById(R.id.name_field);
        editPricePerItem    =v.findViewById(R.id.price_field);
        editItemType        =v.findViewById(R.id.ItemTypeField);
        editDescription     =v.findViewById(R.id.discription_field);
        editUsage           =v.findViewById(R.id.usageField);
        editIngredients     =v.findViewById(R.id.Ingredients_field);
        editSideEffects     =v.findViewById(R.id.Side_Effects_field);
        btnAdd              =v.findViewById(R.id.btnAdd);
        btnClear            =v.findViewById(R.id.btnClear);
        btnDelete           =v.findViewById(R.id.btnDelete);


        editTextBackground = editTextName.getBackground();

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALLERY_REQUEST);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST);
            }
        });

        Bundle args = getArguments();
        if(args!=null) {
            String medicineName = args.getString(DATA_RECIEVE);
            ((PharmacyAdmin)getActivity()).getSupportActionBar().setTitle("Update Medicine");

            DBHandler db = new DBHandler(getContext());
            item2 = db.selectMedicineItem(medicineName);
            imageView.setImageResource(0);
            editTextName.setText(item2.getNameMedicine());
            editPricePerItem.setText(item2.getPrice()+"");
            editItemType.setText(item2.getPriceItemType());
            editDescription.setText(item2.getDescription());
            editUsage.setText(item2.getUsage());
            editIngredients.setText(item2.getIngredients());
            editSideEffects.setText(item2.getSideEffects());

            btnAdd.setText("Update");
            editTextName.setBackground(null);
            editTextName.setEnabled(false);
            editTextName.setFocusable(false);
            editTextName.setCursorVisible(false);
            editTextName.setTextColor(Color.BLACK);


            if(item2.getImage()!=null) {
                BitmapFactory.Options options= new BitmapFactory.Options();
                imageByte = item2.getImage();

                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inSampleSize = 1;
                options.inScaled = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                Bitmap image = BitmapFactory.decodeByteArray(item2.getImage(), 0, item2.getImage().length,options);
                imageView.setImageBitmap(image);
            }
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("CONFIRMATION").setMessage("Do you want to Add the Medicine?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            String Name          =editTextName    .getText().toString().trim();
                            float pricePerItem   =Float.parseFloat(editPricePerItem.getText().toString().trim());
                            String ItemType      =editItemType    .getText().toString().trim();
                            String Description   =editDescription .getText().toString().trim();
                            String Usage         =editUsage       .getText().toString().trim();
                            String Ingredients   =editIngredients.getText().toString() .trim();
                            String SideEffects   =editSideEffects .getText().toString().trim();

                            if(TextUtils.isEmpty(Name)|| TextUtils.isEmpty(Description)||TextUtils.isEmpty(Usage)
                                    ||TextUtils.isEmpty(Ingredients)||TextUtils.isEmpty(SideEffects)||TextUtils.isEmpty(ItemType)){
                                Toast.makeText(getContext(),"Fill All the Details", Toast.LENGTH_SHORT).show();
                            }else if(imageView.getDrawable() == null ){
                                Toast.makeText(getContext(),"Attach an Image", Toast.LENGTH_SHORT).show();
                            }else{



                                item = new MedicineItemClass();
                                item.setNameMedicine(Name);
                                item.setPrice(pricePerItem);
                                item.setPriceItemType(ItemType);
                                item.setDescription(Description);
                                item.setUsage(Usage);
                                item.setIngredients(Ingredients);
                                item.setSideEffects(SideEffects);


                                if(imageChanged) {
                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                    item.setImage(outputStream.toByteArray());

                                    imgStr = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                                    item.setImageBase64(imgStr);
                                }else{

                                    item.setImage(imageByte);
                                    imgStr = Base64.encodeToString( imageByte, Base64.DEFAULT);
                                    item.setImageBase64(imgStr);

                                }


                                DBHandler db = new DBHandler(getContext());
                                if(db.addMedicine(item)==1 ){
                                    Toast.makeText(getContext(),"Medicine Added", Toast.LENGTH_SHORT).show();

                                    item.setImage(null);
                                    DBRef = FirebaseDatabase.getInstance().getReference().child("Medicine");
                                    Query query = DBRef.orderByChild("nameMedicine").equalTo(item.getNameMedicine());


                                    ValueEventListener valueEventListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                                                //       DBRef.child(postSnapshot.getKey()).setValue(item);
                                                DBRef.child(postSnapshot.getKey()).removeValue();
                                            }
                                            DBRef = FirebaseDatabase.getInstance().getReference().child("Medicine");
                                            item.setImage(null);
                                            DBRef.push().setValue(item);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    };
                                    query.addListenerForSingleValueEvent(valueEventListener);

                                    clearAll(v);

                                }else if(db.addMedicine(item)==2 ) {

                                    Toast.makeText(getContext(), "Medicine Updated", Toast.LENGTH_SHORT).show();


                                    DBRef = FirebaseDatabase.getInstance().getReference().child("Medicine");
                                    Query query = DBRef.orderByChild("nameMedicine").equalTo(item.getNameMedicine());

                                    item.setImage(null);
                                    ValueEventListener valueEventListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                                                DBRef.child(postSnapshot.getKey()).setValue(item);
                                                //         DBRef.child(postSnapshot.getKey()).removeValue();
                                            }
                                            //     DBRef = FirebaseDatabase.getInstance().getReference().child("Medicine");
                                            //     item.setImage(null);
                                            //     DBRef.push().setValue(item);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    };
                                    query.addListenerForSingleValueEvent(valueEventListener);

                                    clearAll(v);





                                }else{
                                    Toast.makeText(getContext(),"Medicine Not Added", Toast.LENGTH_SHORT).show();
                                }
                                BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_nav_menu);
                                bottomNav.setSelectedItemId(R.id.nav_all_medicine);



                            }
                        }catch (NumberFormatException e){
                            Toast.makeText(getContext(),"Enter a Valid Price", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("CONFIRMATION").setMessage("Do you want to Clear the Fields?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((PharmacyAdmin)getActivity()).getSupportActionBar().setTitle("Add Medicine");
                        editTextName.setFocusable(true);
                        editTextName.setFocusableInTouchMode(true);
                        editTextName.setInputType( InputType.TYPE_CLASS_TEXT);
                        editTextName.setEnabled(true);
                        editTextName.setCursorVisible(true);

                        editTextName.setBackground(editTextBackground);

                        btnAdd.setText("ADD");
                        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_menu);
                        bottomNavigationView.setSelectedItemId(R.id.nav_add_medicine);
                        clearAll(v);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("CONFIRMATION").setMessage("Do you want to Delete the Medicine?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHandler db =  new DBHandler(getContext());
                        String nameToDelete = editTextName.getText().toString();
                        if(!TextUtils.isEmpty(nameToDelete)) {
                            if (db.deleteMedicine(nameToDelete)) {

                                DBRef = FirebaseDatabase.getInstance().getReference().child("Medicine");

                                Query query = DBRef.orderByChild("nameMedicine").equalTo(nameToDelete);
                                ValueEventListener valueEventListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                            DBRef.child(postSnapshot.getKey()).removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                };
                                query.addListenerForSingleValueEvent(valueEventListener);

                                Toast.makeText(getContext(), "Medicine Deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Medicine Not Deleted", Toast.LENGTH_SHORT).show();
                            }
                            clearAll(v);
                            BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_nav_menu);
                            bottomNav.setSelectedItemId(R.id.nav_all_medicine);
                        }
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

    private void clearAll(View v){
        try {

            float pricePerItem = Float.parseFloat(editPricePerItem.getText().toString().trim());

        }catch (NumberFormatException e){

        }
        String Name = editTextName.getText().toString().trim();
        String ItemType = editItemType.getText().toString().trim();
        String Description = editDescription.getText().toString().trim();
        String Usage = editUsage.getText().toString().trim();
        String Ingredients = editIngredients.getText().toString().trim();
        String SideEffects = editSideEffects.getText().toString().trim();

        imageView.setImageResource(0);
        editTextName.setText("");
        editPricePerItem.setText("");
        editItemType.setText("");
        editDescription.setText("");
        editUsage.setText("");
        editIngredients.setText("");
        editSideEffects.setText("");
    }
}
