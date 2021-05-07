package com.example.qlbhkk.Thongke.tkslhang_nhap_xuat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.ArrayList;

public class Adapter_hang extends ArrayAdapter<hang> {
    ArrayList<hang> arr=new ArrayList<>();
    Activity context;
    int layout;
    public Adapter_hang(Context context, int resource, ArrayList<hang> obj){
        super(context, resource, obj);
        this.context = (Activity) context;
        arr = obj;
        layout = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout , null);
        TextView tvthang =(TextView)view.findViewById(R.id.tvt);
        TextView tvsln=(TextView)view.findViewById(R.id.tvsln);
        TextView tvslb =(TextView)view.findViewById(R.id.tvslb);


        hang tv= arr.get(position);
        tvthang.setText(String.valueOf(tv.thang));
        tvsln.setText(String.valueOf(tv.slnhap));
        tvslb.setText(String.valueOf(tv.slban));

        return view;
    }
}
