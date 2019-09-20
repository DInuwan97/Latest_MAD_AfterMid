package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST) {

            Bitmap bitmap;

            try {
                Uri target =data.getData();
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(target));
                imageView.setImageBitmap(bitmap);

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
                //Uri target =data.getData();
                //bitmap =BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(target));
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
                    }else{
                        MedicineItemClass item = new MedicineItemClass();
                        item.setNameMedicine(Name);
                        item.setPrice(pricePerItem);
                        item.setPriceItemType(ItemType);
                        item.setDescription(Description);
                        item.setUsage(Usage);
                        item.setIngredients(Ingredients);
                        item.setSideEffects(SideEffects);

                        DBHandler db = new DBHandler(getContext());
                        if(db.addMedicine(item)){
                            Toast.makeText(getContext(),"Medicine Added", Toast.LENGTH_SHORT).show();
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
