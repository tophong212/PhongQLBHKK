package com.example.qlbhkk.HoaDonNhap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.ArrayList;

public class Adapter_HDN extends ArrayAdapter<HoaDonNhap> {
    ArrayList<HoaDonNhap> arr;
    Activity context;
    int layout;

    public Adapter_HDN(Context context, int resource, ArrayList<HoaDonNhap> obj) {
        super(context, resource, obj);
        this.context = (Activity) context;
        arr = obj;
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);
        TextView tvma = (TextView) view.findViewById(R.id.tvMaHDN);
        TextView tvmanv = (TextView) view.findViewById(R.id.tvNV);
        TextView tvmancc = (TextView) view.findViewById(R.id.tvNCC);
        TextView tvngaynhap = (TextView) view.findViewById(R.id.tvTGNhap);

        HoaDonNhap tv = arr.get(position);
        tvma.setText(tv.maHDN);
        tvmanv.setText(tv.maNV);
        tvmancc.setText(tv.maNCC);
        tvngaynhap.setText(tv.ngaynhap);

        return view;
    }
}
