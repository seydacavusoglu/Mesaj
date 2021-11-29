package com.example.mesaj;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Users> usersList;


    public UserAdapter(Activity activity, ArrayList<Users> usersList) {

        this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.usersList = usersList;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.phone_recycler, null);
        TextView user = (TextView) convertView.findViewById(R.id.textView1);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
        Users oneUser = usersList.get(position);
        user.setText(oneUser.getName());

        if (usersList.get(position).getImage() != null)
            image.setImageBitmap(usersList.get(position).getImage());
        else {
            image.setImageResource(R.drawable.user);
        }
        convertView.setTag(usersList.get(position).getName());
        return convertView;
    }


}