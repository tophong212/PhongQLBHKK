package com.example.qlbhkk.HoaDonXuat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.ArrayList;

public class Adapter_HDX extends ArrayAdapter<HoaDonXuat> {
    ArrayList<HoaDonXuat> arr=new ArrayList<>();
    Activity context;
    int layout;
    public Adapter_HDX(Context context, int resource, ArrayList<HoaDonXuat> obj){
        super(context, resource, obj);
        this.context = (Activity) context;
        arr = obj;
        layout = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout , null);
        TextView tvma =(TextView)view.findViewById(R.id.tvMaHDX);
        TextView tvmanv=(TextView)view.findViewById(R.id.tvNV);
        TextView tvngaynhap=(TextView)view.findViewById(R.id.tvTGBan) ;


        HoaDonXuat tv= arr.get(position);
        tvma.setText(tv.maHDX);
        tvmanv.setText(tv.maNV);
        tvngaynhap.setText(tv.ngayBan);

        return view;
    }
}
