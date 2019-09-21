package com.example.myapplication;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

public class SystemUsersAdapter extends BaseAdapter {

    private Context mContext;
    private List<SystemUsers> mSystemUserList;

    public SystemUsersAdapter(Context mContext, List<SystemUsers> mSystemUserList) {
        this.mContext = mContext;
        this.mSystemUserList = mSystemUserList;
    }



    public int getCount(){
        return mSystemUserList.size();
    }

    public Object getItem(int position){

        return mSystemUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public long getItemID(int position){
        return position;
    }

    public View getView(int position, View contentView, ViewGroup parent){
        View v = View.inflate(mContext,R.layout.item_sysytem_user_list,null);
        TextView txtName = (TextView)v.findViewById(R.id.txtName);
        TextView txtEmail = (TextView)v.findViewById(R.id.txtEmail);
        TextView txtDesignation = (TextView)v.findViewById(R.id.txtDesignation);

        txtName.setText(mSystemUserList.get(position).getUsername().toString());
        txtEmail.setText(/*String.valueOf*/(mSystemUserList.get(position).getEmail().toString()));
        txtDesignation.setText(mSystemUserList.get(position).getDesignation().toString());

        v.setTag(mSystemUserList.get(position).getId());

        return v;
    }


}
