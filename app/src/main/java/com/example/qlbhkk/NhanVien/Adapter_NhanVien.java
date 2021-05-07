package com.example.qlbhkk.NhanVien;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.ArrayList;

public class Adapter_NhanVien extends ArrayAdapter<NhanVien> {
    ArrayList<NhanVien> arr=new ArrayList<>();
    Activity context;
    int layout;
    public Adapter_NhanVien(Context context, int resource, ArrayList<NhanVien> obj){
        super(context, resource, obj);
        this.context = (Activity) context;
        arr = obj;
        layout = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout , null);
        TextView tvmanv =(TextView)view.findViewById(R.id.tvmanv);
        TextView tvtennv=(TextView)view.findViewById(R.id.tvtennv);
        TextView tvsdt =(TextView)view.findViewById(R.id.tvsdtnv);

        NhanVien tv= arr.get(position);
        tvmanv.setText(String.valueOf(tv.maNV));
        tvtennv.setText(String.valueOf(tv.tenNV));
        tvsdt.setText(String.valueOf(tv.soDT));

        return view;
    }
}
