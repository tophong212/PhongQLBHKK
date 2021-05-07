package com.example.qlbhkk.NhaCungCap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.ArrayList;

public class Adapter_NCC extends ArrayAdapter<NhaCungCap> {
    ArrayList<NhaCungCap> arr=new ArrayList<>();
    Activity context;
    int layout;
    public Adapter_NCC(Context context, int resource, ArrayList<NhaCungCap> obj){
        super(context, resource, obj);
        this.context = (Activity) context;
        arr = obj;
        layout = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout , null);
        TextView tvmancc =(TextView)view.findViewById(R.id.tvmancc);
        TextView tvtenncc=(TextView)view.findViewById(R.id.tvtenncc);
        TextView tvdc =(TextView)view.findViewById(R.id.tvdcncc);

        NhaCungCap tv= arr.get(position);
        tvmancc.setText(String.valueOf(tv.maNCC));
        tvtenncc.setText(String.valueOf(tv.tenNCC));
        tvdc.setText(String.valueOf(tv.diaChi));

        return view;
    }
}
