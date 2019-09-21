package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.MedicineItemClass;
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


            DBHandler db = new DBHandler(getContext());
            MedicineItemClass item  = db.selectMedicineItem(medicineName);
            imageView.setImageResource(0);
            editTextName.setText(item.getNameMedicine());
            editPricePerItem.setText(item.getPrice()+"");
            editItemType.setText(item.getPriceItemType());
            editDescription.setText(item.getDescription());
            editUsage.setText(item.getUsage());
            editIngredients.setText(item.getIngredients());
            editSideEffects.setText(item.getSideEffects());

            if(item.getImage()!=null) {
                Bitmap image = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
                imageView.setImageBitmap(image);
            }
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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



                        final MedicineItemClass item = new MedicineItemClass();
                        item.setNameMedicine(Name);
                        item.setPrice(pricePerItem);
                        item.setPriceItemType(ItemType);
                        item.setDescription(Description);
                        item.setUsage(Usage);
                        item.setIngredients(Ingredients);
                        item.setSideEffects(SideEffects);

                        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                        item.setImage(outputStream.toByteArray());


                        final String imgStr = Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);
                        item.setImageBase64(imgStr);

                        DBHandler db = new DBHandler(getContext());
                        if(db.addMedicine(item)==1 ){
                            Toast.makeText(getContext(),"Medicine Added", Toast.LENGTH_SHORT).show();

                            DBRef = FirebaseDatabase.getInstance().getReference().child("Medicine");
                            Query query = DBRef.orderByChild("nameMedicine").equalTo(item.getNameMedicine());


                            ValueEventListener valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

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


                            ValueEventListener valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

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

                        }else{
                            Toast.makeText(getContext(),"Medicine Not Added", Toast.LENGTH_SHORT).show();
                        }


                    }
                }catch (NumberFormatException e){
                    Toast.makeText(getContext(),"Enter a Valid Amount", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll(v);
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
