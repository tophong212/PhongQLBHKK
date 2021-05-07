package com.example.qlbhkk.MatHang;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.ArrayList;

public class Adapter_Mathang extends ArrayAdapter<MatHang> {
    ArrayList<MatHang> arr = new ArrayList<>();
    Activity context;
    int layout;
    public Adapter_Mathang(Context context, int resource, ArrayList<MatHang> obj){
        super(context, resource, obj);
        this.context = (Activity) context;
        arr = obj;
        layout = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout , null);
        TextView tvmamh =(TextView)view.findViewById(R.id.tvmamh);
        TextView tvtenmh=(TextView)view.findViewById(R.id.tvtenmh);
        TextView tvmota =(TextView)view.findViewById(R.id.tvmota);

        MatHang tv= arr.get(position);
        tvmamh.setText(String.valueOf(tv.maMH));
        tvtenmh.setText(String.valueOf(tv.tenMH));
        tvmota.setText(String.valueOf(tv.moTa));

        return view;
    }
}
