package com.example.qlbhkk.Thongke.tksanphambc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.ArrayList;

public class Adapter_spbc extends ArrayAdapter<spbc> {
    ArrayList<spbc> arr=new ArrayList<>();
    Activity context;
    int layout;
    public Adapter_spbc(Context context, int resource, ArrayList<spbc> obj){
        super(context, resource, obj);
        this.context = (Activity) context;
        arr = obj;
        layout = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout , null);
        TextView tvmasp =(TextView)view.findViewById(R.id.tvmaspbc);
        TextView tvtensp=(TextView)view.findViewById(R.id.tvtenspbc);
        TextView tvsl =(TextView)view.findViewById(R.id.tvslb);


        spbc tv= arr.get(position);
        tvmasp.setText(String.valueOf(tv.masp));
        tvtensp.setText(String.valueOf(tv.tensp));
        tvsl.setText(String.valueOf(tv.soluong));

        return view;
    }
}
