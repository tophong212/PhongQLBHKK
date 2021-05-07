package com.example.qlbhkk.Thongke.tkthang;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.ArrayList;

public class Adapter_dt extends ArrayAdapter<dt> {
    ArrayList<dt> arr=new ArrayList<>();
    Activity context;
    int layout;
    public Adapter_dt(Context context, int resource, ArrayList<dt> obj){
        super(context, resource, obj);
        this.context = (Activity) context;
        arr = obj;
        layout = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout , null);
        TextView tvthang =(TextView)view.findViewById(R.id.tvThang);
        TextView tvchiphi=(TextView)view.findViewById(R.id.tvChiPhi);
        TextView tvdoanhthu =(TextView)view.findViewById(R.id.tvDoanhThu);
        TextView tvloinhuan=(TextView)view.findViewById(R.id.tvLoiNhuan) ;


        dt tv= arr.get(position);
        tvthang.setText(String.valueOf(tv.thang));
        tvchiphi.setText(String.valueOf(tv.chiphi));
        tvdoanhthu.setText(String.valueOf(tv.doanhthu));
        tvloinhuan.setText(String.valueOf(tv.loinhuan));

        return view;
    }
}
