package com.example.qlbhkk.HoaDonXuat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qlbhkk.R;

import java.util.List;

public class Adapter_CTHDX extends BaseAdapter {
    private Context context;
    private int layoutcthdX;
    private List<CTHDX> list;

    public Adapter_CTHDX(Context context, int layoutcthdX, List<CTHDX> list) {
        this.context = context;
        this.layoutcthdX = layoutcthdX;
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
        view = inflater.inflate(layoutcthdX, null);
        TextView tvdg = (TextView) view.findViewById(R.id.tvdgXuat);
        TextView tvmaqaN = (TextView) view.findViewById(R.id.tvqaX);
        TextView tvslgN = (TextView) view.findViewById(R.id.tvslgX);
        //
        CTHDX cthdx = list.get(i);
        tvmaqaN.setText(cthdx.getMaSP());
        tvslgN.setText(String.valueOf(cthdx.getSoluong()));
        tvdg.setText(String.valueOf(cthdx.getGiaban()));
        return view;
    }
}
