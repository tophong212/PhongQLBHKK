package com.example.qlbhkk.HoaDonNhap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.List;

public class Adapter_CTHDN extends BaseAdapter {
    private Context context;
    private int layoutcthdN;
    private List<CTHDN> list;

    public Adapter_CTHDN(Context context, int layoutcthdN, List<CTHDN> list) {
        this.context = context;
        this.layoutcthdN = layoutcthdN;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layoutcthdN, null);
        // Ánh xạ tới layout dong_cthdnhap
        TextView tvmaspN = (TextView) view.findViewById(R.id.tvspN);
        TextView tvslgN = (TextView) view.findViewById(R.id.tvslgN);
        TextView tvgiaN = (TextView) view.findViewById(R.id.tvgiaN);
        //
        CTHDN cthdn = list.get(i);
        tvmaspN.setText(cthdn.getMaSP());
        tvslgN.setText(String.valueOf(cthdn.getSoluong()));
        tvgiaN.setText(String.valueOf(cthdn.getGianhap()));
        return view;
    }

}
