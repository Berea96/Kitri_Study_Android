package com.example4.bereakj.sms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vo.Message;

public class msgAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private ArrayList<Message> list;

    public msgAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.list = (ArrayList<Message>)objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflate =
                    (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflate.inflate(resource, null);
        }
        Message m = list.get(position);

        TextView tel = (TextView)convertView.findViewById(R.id.textView3);
        TextView msg = (TextView)convertView.findViewById(R.id.textView5);

        tel.setText(m.getTel());
        msg.setText(m.getMsg().substring(0, 5));

        return convertView;
    }
}
