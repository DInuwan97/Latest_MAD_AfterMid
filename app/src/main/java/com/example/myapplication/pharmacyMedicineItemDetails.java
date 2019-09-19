package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.MedicineItemClass;


public class pharmacyMedicineItemDetails extends Fragment {
    final static String DATA_RECIEVE = "data_recieve";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pharmacy_medicine_item_details, container, false);

        TextView txtViewName = v.findViewById(R.id.Name_field);
        TextView txtPrice = v.findViewById(R.id.Price_field);
        TextView txtPriceType = v.findViewById(R.id.price_type);
        TextView txtDescription = v.findViewById(R.id.description_feild);
        TextView txtUsage = v.findViewById(R.id.Usage_field);
        TextView txtIngredient = v.findViewById(R.id.Ingredient_field);
        TextView txtSideEffect = v.findViewById(R.id.Side_Effects_field);
        final EditText editTextAmount = v.findViewById(R.id.noOfItems);
        final TextView txtPriceCalculated = v.findViewById(R.id.Price_calculated);

        Bundle args = getArguments();
        String medicineName = args.getString(DATA_RECIEVE);

        DBHandler dh = new DBHandler(getActivity().getApplicationContext());
        final MedicineItemClass item = dh.selectMedicineItem(medicineName);

        String price = "Rs. "+ item.getPrice();
        txtViewName.setText(item.getNameMedicine());
        txtPrice .setText(price);
        txtPriceType.setText(item.getPriceItemType());
        txtDescription.setText(item.getDescription());
        txtUsage.setText(item.getUsage());
        txtIngredient.setText(item.getIngredients());
        txtSideEffect.setText(item.getSideEffects());

        editTextAmount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try{
                    Float amount = Float.parseFloat(editTextAmount.getText().toString());
                    amount = amount*item.getPrice();

                    txtPriceCalculated.setText(amount.toString());
                }catch (NumberFormatException e){

                }



                return false;
            }
        });

        return v;
    }


}
