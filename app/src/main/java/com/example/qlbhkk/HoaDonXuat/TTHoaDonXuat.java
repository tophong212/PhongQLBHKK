package com.example.qlbhkk.HoaDonXuat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

import java.util.ArrayList;

public class TTHoaDonXuat extends AppCompatActivity {
    private ListView listCTHDX;
    private Adapter_CTHDX adapCTHDX;
    private ArrayList<CTHDX> arCTHDX;
    public DatabaseHandler db = new DatabaseHandler(this);
    private TextView tvmaHDX,tvTennv, tvttXuat, tvTGXuat;
    private String MaHD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cthoadonxuat);
        //////////////////////////////////
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //ánh xạ
        AnhXa();
        //Lấy dữ liệu
        LayDL();
    }
    private void LayDL() {
        // Nhận Intent từ activity chuyển qua
        Intent it = getIntent();
        // Lấy ra dữ liệu từ Intent
        MaHD = it.getStringExtra("Mahdx");

        tvmaHDX.setText(MaHD);
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT * FROM hoadonxuat WHERE Mahdx='" + MaHD.toUpperCase() + "' ");
        c.moveToFirst();
        HoaDonXuat h=new HoaDonXuat();
        h.setMaNV(c.getString(1));
        h.setNgayBan(c.getString(2));

        tvTennv.setText(""+tennv(h.getMaNV()));
        tvTGXuat.setText(""+h.getNgayBan());
        tvttXuat.setText(""+TongTien(MaHD));
        c.close();
    }

    // lấy tên NV từ Mã NV
    private String tennv(String ma) {
        String ten = null;
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT tennv FROM nhanvien WHERE Manv='" + ma.toUpperCase() + "' ");
        c.moveToFirst();
        ten = c.getString(0);
        c.close();
        return ten;
    }

    public int TongTien(String ma) {
        //arCTHDN = new ArrayList<>();
        arCTHDX = DoccthdX(ma);

        int tien = 0;
        for (int i = 0; i < arCTHDX.size(); i++) {
            tien = (arCTHDX.get(i).getGiaban() * arCTHDX.get(i).getSoluong()) + tien;
        }
        return tien;
    }
    //Nút back trở về trang trc
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent it = new Intent(TTHoaDonXuat.this, QLHoaDonXuat.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }

    private void AnhXa() {
        arCTHDX = new ArrayList<>();
        listCTHDX = findViewById(R.id.listviewCTHDX);
        tvmaHDX = (TextView) findViewById(R.id.tvMaCTHDX);
        tvTennv=(TextView) findViewById(R.id.tvtenNVX);
        tvTGXuat = (TextView) findViewById(R.id.tvTGXuatcthdx);
        tvttXuat = (TextView) findViewById(R.id.tvTTXuatcthdx);
    }
    private int GiaSP(String ma) {
        int gia = 0;
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT giaban FROM sanpham WHERE Masp='" + ma.toUpperCase() + "' ");
        c.moveToFirst();
        gia = c.getInt(0);
        return gia;
    }
    //   Đọc dữ liệu cho arrayList CTHDX theo Mã HD truyền qua
    public ArrayList<CTHDX> DoccthdX(String maHD) {
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT * FROM cthoadonxuat WHERE Mahdx='" + maHD.toUpperCase() + "' ");
        c.moveToFirst();
        do {
            String mahdx = c.getString(0);
            String masp = c.getString(1);
            int solg = c.getInt(2);
            String ten = LayTen(masp);
            int gia=GiaSP(masp);
            arCTHDX.add(new CTHDX( mahdx,ten, solg, gia));
        } while (c.moveToNext());
        adapCTHDX = new Adapter_CTHDX(this, R.layout.item_cthdx, arCTHDX);
        listCTHDX.setAdapter(adapCTHDX);
        return arCTHDX;
    }

    // lấy tên từ Mã
    public String LayTen(String ma) {
        String ten = " ";
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT tensp FROM sanpham WHERE Masp='" + ma.toUpperCase() + "'");
        c.moveToFirst();
        ten = c.getString(0);
        c.close();
        return ten;
    }
}
