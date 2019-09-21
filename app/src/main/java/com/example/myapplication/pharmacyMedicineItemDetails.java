package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.MedicineItemClass;


public class pharmacyMedicineItemDetails extends Fragment {
    final static String DATA_RECIEVE = "data_receive";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_pharmacy_medicine_item_details, container, false);

        TextView txtViewName = v.findViewById(R.id.Name_field);
        TextView txtPrice = v.findViewById(R.id.Price_field);
        TextView txtPriceType = v.findViewById(R.id.price_type);
        TextView txtDescription = v.findViewById(R.id.description_feild);
        TextView txtUsage = v.findViewById(R.id.Usage_field);
        TextView txtIngredient = v.findViewById(R.id.Ingredient_field);
        TextView txtSideEffect = v.findViewById(R.id.Side_Effects_field);

        ImageView imageView = v.findViewById(R.id.MedicineImageImageView);

        final EditText editTextAmount = v.findViewById(R.id.noOfItems);
        final TextView txtPriceCalculated = v.findViewById(R.id.Price_calculated);

        Button addToCart = v.findViewById(R.id.Add_to_Cart);

        Bundle args = getArguments();
        String medicineName = args.getString(DATA_RECIEVE);

        final DBHandler dh = new DBHandler(getActivity().getApplicationContext());
        final MedicineItemClass item = dh.selectMedicineItem(medicineName);

        String price = "Rs. "+ item.getPrice();
        txtViewName.setText(item.getNameMedicine());
        txtPrice .setText(price);
        txtPriceType.setText(item.getPriceItemType());
        txtDescription.setText(item.getDescription());
        txtUsage.setText(item.getUsage());
        txtIngredient.setText(item.getIngredients());
        txtSideEffect.setText(item.getSideEffects());
        if(item.getImage() == null){

        }else {
            Bitmap image = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
            imageView.setImageBitmap(image);
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextAmount.getText().toString() != "") {
                    float amount = 0;
                    try {
                        amount = Float.parseFloat(editTextAmount.getText().toString());
                    }catch (NumberFormatException e){
                        Toast.makeText(getActivity().getApplicationContext(),"Enter a Valid Amount",Toast.LENGTH_SHORT).show();
                    }
                    if(dh.cartAddItem(item,amount )){
                        Toast.makeText(getActivity().getApplicationContext(),"Added to Cart",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(),"Not Added to Cart. Try Again",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Enter a Valid Amount",Toast.LENGTH_SHORT).show();
                }

            }
        });


        editTextAmount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try{
                    Float amount = Float.parseFloat(editTextAmount.getText().toString());
                    amount = amount*item.getPrice();

                    txtPriceCalculated.setText("Rs. "+amount.toString());
                }catch (NumberFormatException e){

                }



                return false;
            }
        });



        return v;
    }




}
