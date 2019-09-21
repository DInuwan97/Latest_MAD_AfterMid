package com.example.myapplication;

import android.content.Context;
import android.icu.util.ValueIterator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.MedicineItemClass;

import java.util.ArrayList;

public class cartListAdapter extends ArrayAdapter<MedicineItemClass> {
    private LayoutInflater layoutInflater;
    private ArrayList<MedicineItemClass> itemArray;

    public cartListAdapter(Context context, ArrayList<MedicineItemClass> data) {
        super(context, R.layout.list_cart_item,data);
        this.layoutInflater = LayoutInflater.from(context);
        itemArray = data;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        final MedicineItemClass item = getItem(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_cart_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.txtViewName.setText(item.getNameMedicine());
        float calculate = item.getAmount()*item.getPrice();

        viewHolder.calculatedPrice.setText("Rs. " + calculate);
        viewHolder.Amount.setText(item.getAmount()+"");
        viewHolder.txtViewItemType.setText("Rs. "+item.getPrice()+" "+item.getPriceItemType());


        viewHolder.btnAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float amount = Float.parseFloat(viewHolder.Amount.getText().toString());

                amount = amount + 1;

                String amountStr = amount+"";
                viewHolder.Amount.setText(amountStr);
                DBHandler dh = new DBHandler(getContext());
                if(dh.cartAddItem(item,amount)){
                    float calculateAfterAmountChange = amount*item.getPrice();
                    viewHolder.calculatedPrice.setText("Rs. "+calculateAfterAmountChange);


                }else{
                    if(amount >0) {
                        amount = amount - 1;
                    }else{
                        amount=0;
                    }
                    amountStr = amount+"";
                    viewHolder.Amount.setText(amountStr);
                }
            }
        });
        viewHolder.btnMinusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float amount = Float.parseFloat(viewHolder.Amount.getText().toString());
                if(amount > 0) {
                    amount = amount - 1;
                }else{
                    amount = 0;
                }
                String amountStr = amount+"";
                viewHolder.Amount.setText(amountStr);
                DBHandler dh = new DBHandler(getContext());
                if(dh.cartAddItem(item,amount)){
                    float calculateAfterAmountChange = amount*item.getPrice();
                    viewHolder.calculatedPrice.setText("Rs. "+calculateAfterAmountChange);


                }else{

                    amount = amount + 1;

                    amountStr = amount+"";
                    viewHolder.Amount.setText(amountStr);
                }
            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler db = new DBHandler(getContext());
                if(db.cartDeleteItem(item.getNameMedicine())){

                    remove(item);
                    notifyDataSetChanged();
                }else{
                    Log.i("testing","medicine cart not delete");
                }

            }
        });

        return  convertView;
    }



    private class ViewHolder{
        TextView txtViewName;
        Button btnAddOne;
        Button btnMinusOne;
        Button btnDelete;
        TextView Amount;
        TextView calculatedPrice;
        TextView txtViewItemType;

        public ViewHolder(View view){
            txtViewName = view.findViewById(R.id.txtView_cart_name);
            calculatedPrice = view.findViewById(R.id.txtView_calculated_Price);
            Amount = view.findViewById(R.id.editText_cart_amount);
            btnAddOne = view.findViewById(R.id.btn_cart_plus);
            btnMinusOne = view.findViewById(R.id.btn_cart_minus);
            btnDelete = view.findViewById(R.id.btn_cart_delete);
            txtViewItemType = view.findViewById(R.id.txtViewItemType);
        }
    }
}
