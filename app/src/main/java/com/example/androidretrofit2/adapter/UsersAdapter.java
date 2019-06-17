package com.example.androidretrofit2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidretrofit2.R;
import com.example.androidretrofit2.model.Users;

import java.util.ArrayList;
import java.util.List;


public class UsersAdapter extends BaseAdapter {
    private Context context;
    private List<Users> listUsers = new ArrayList<>();

    public  UsersAdapter(final Context applicationContext,final List<Users> listUsers){
        this.context = applicationContext;
        this.listUsers = listUsers;
    }


    @Override
    public int getCount() {
        return listUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return listUsers.get(position);
    }

    private Users parsetItem(int position){
        return this.listUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View layout, ViewGroup viewGroup) {


        if (layout == null) {
            layout = LayoutInflater.from(context).
                    inflate(R.layout.user,
                            viewGroup, false);
        }


        Users users = parsetItem(position);


        TextView txtId, txtUserName,txtPhone;



        txtId = layout.findViewById(R.id.txtId);
        txtUserName = layout.findViewById(R.id.txtUserName);
        txtPhone = layout.findViewById(R.id.txtPhone);

        txtId.setText(String.valueOf(users.getId()));
        txtUserName.setText(users.getName());
        txtPhone.setText(users.getPhone());


        return layout;
    }
}
