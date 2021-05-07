package com.example.qlbhkk.SanPham;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.ArrayList;

public class Adapter_SanPham extends ArrayAdapter<SanPham> {
    ArrayList<SanPham> arr=new ArrayList<>();
    Activity context;
    int layout;
    public Adapter_SanPham(Context context, int resource, ArrayList<SanPham> obj){
        super(context, resource, obj);
        this.context = (Activity) context;
        arr = obj;
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout , null);
        TextView tvmasp =(TextView)view.findViewById(R.id.tvmasp);
        TextView tvtensp=(TextView)view.findViewById(R.id.tvtensp);
        TextView tvgiasp =(TextView)view.findViewById(R.id.tvgiasp);

        SanPham tv= arr.get(position);
        tvmasp.setText(String.valueOf(tv.maSP));
        tvtensp.setText(String.valueOf(tv.tenSP));
        tvgiasp.setText(String.valueOf(tv.giaBan));

        return view;
    }
}
