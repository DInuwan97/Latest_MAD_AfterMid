package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Database.DBHandler;

import java.util.ArrayList;

public class SystemUsersAdapter extends ArrayAdapter<SystemUsers> {

    private Context mContext;
    private ArrayList<SystemUsers> mSystemUserList;
    private LayoutInflater layoutInflater;


    TextView txtPatientName,txtPatientEmail,txtPatientGender,txtPatientMobile,txtPatientAddress;

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


        //viewHolder.txtPatientName.setText(mSystemUserList.get(position).getUsername());

        txtPatientName = (TextView)converttView.findViewById(R.id.txtPatientName);


        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openDialog(item);
            }
        });

        viewHolder.btnView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                loadPatientDetailsDialog(item);
            }
        });

        return converttView;
    }


//for dialogbox
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


    public void loadPatientDetailsDialog(final SystemUsers item) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.dialogbox_view_patient_profile, null);



      //  txtPatientName.setText(item.getUsername());


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(subView);
        builder.create();
        builder.show();

    }



    private class ViewHolder{
        TextView txtName;
        TextView txtEmail;
        TextView txtDesignation;
        Button btnDelete,btnView;


        TextView txtPatientName,txtPatientEmail,txtPatientGender,txtPatientMobile,txtPatientAddress;

        TextView txtDeleteUserDialogBoxConfirmation;

        public ViewHolder(View v){

            txtName = (TextView)v.findViewById(R.id.txtName);
            txtEmail = (TextView)v.findViewById(R.id.txtEmail);
            txtDesignation = (TextView)v.findViewById(R.id.txtDesignation);
            btnDelete = (Button) v.findViewById(R.id.buttonDelete);
            btnView = (Button) v.findViewById(R.id.btnUpdateUser);




        }
    }


}
