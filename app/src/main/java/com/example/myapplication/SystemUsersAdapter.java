package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.icu.util.ValueIterator;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.DialogBoxes.UserDeleteDialogBox;

import java.util.ArrayList;
import java.util.List;

public class SystemUsersAdapter extends ArrayAdapter<SystemUsers> {

    private Context mContext;
    private ArrayList<SystemUsers> mSystemUserList;
    private LayoutInflater layoutInflater;;

    public SystemUsersAdapter(Context mContext, ArrayList<SystemUsers> mSystemUserList) {
        super(mContext,R.layout.item_sysytem_user_list,mSystemUserList);
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mSystemUserList = mSystemUserList;
    }

    @Override
    public View getView(final int position, View converttView, ViewGroup parent){

        final ViewHolder viewHolder;
        final SystemUsers item = getItem(position);


        if (converttView == null) {
            converttView = layoutInflater.inflate(R.layout.item_sysytem_user_list, parent, false);
            viewHolder = new ViewHolder(converttView);
            converttView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)converttView.getTag();
        }

        viewHolder.txtName.setText(mSystemUserList.get(position).getUsername());
        viewHolder.txtEmail.setText(mSystemUserList.get(position).getEmail().toString());
        viewHolder.txtDesignation.setText(mSystemUserList.get(position).getDesignation().toString());

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openDialog(item);
            }
        });

        return converttView;
    }

    public void openDialog(final SystemUsers item){

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.dialogbox_delete_user_confirmation, null);

        TextView txtDeleteUserDialogBoxConfirmation = (TextView) subView.findViewById(R.id.txtDeleteUser);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Do you want to Delete this User?");
        //builder.setMessage("Successfully Deleted");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHandler db = new DBHandler(getContext());
                if(db.deleteUsers(item.getEmail())){
                    remove(item);
                    notifyDataSetChanged();
                }else{
                    Log.i("testing","User not Deleted");
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private class ViewHolder{
        TextView txtName;
        TextView txtEmail;
        TextView txtDesignation;
        Button btnDelete,btnView;

        TextView txtDeleteUserDialogBoxConfirmation;

        public ViewHolder(View v){

            txtName = (TextView)v.findViewById(R.id.txtName);
            txtEmail = (TextView)v.findViewById(R.id.txtEmail);
            txtDesignation = (TextView)v.findViewById(R.id.txtDesignation);
            btnDelete = (Button) v.findViewById(R.id.buttonDelete);


        }
    }


}
