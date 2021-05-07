package com.example.qlbhkk.HoaDonNhap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

import java.util.ArrayList;

public class TTHoaDonNhap extends AppCompatActivity {
    private ListView listCTHDN;
    private Adapter_CTHDN adapCTHDN;
    private ArrayList<CTHDN> arCTHDN;
    public DatabaseHandler db = new DatabaseHandler(this);
    // public static float TongTien = 0;
    private TextView tvmaHDN, tvTennv, tvTenncc, tvttNhap, tvTGNhap;
    private String MaHD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cthoadonnhap);
        arCTHDN = new ArrayList<>();
        // Nút back tên thanh toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        AnhXa();
        //lấy dữ liệu
        LayDL();
    }

    // Lấy dữ liệu từ 1 Hóa đơn nhập sang cthd
    private void LayDL() {
        // Nhận Intent từ activity chuyển qua
        Intent it = getIntent();
        // Lấy ra dữ liệu từ Intent
        MaHD = it.getStringExtra("Mahdn");

        tvmaHDN.setText(MaHD);
        // Lấy tên NCC và TG Nhập để hiện
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT * FROM hoadonnhap WHERE Mahdn='" + MaHD + "' ");
        c.moveToFirst();
        HoaDonNhap h = new HoaDonNhap();
        h.setMaNV(c.getString(1));
        h.setMaNCC(c.getString(2));
        h.setNgaynhap(c.getString(3));

        tvTennv.setText("" + tennv(h.getMaNV()));
        tvTenncc.setText("" + tenncc(h.getMaNCC()));
        tvTGNhap.setText("" + h.getNgaynhap());
        tvttNhap.setText("" + TongTien(c.getString(0)));
        c.close();
    }

    // lấy tên NCC từ Mã NCC
    private String tenncc(String ma) {
        String ten = null;
        db.copyDB2SDCard();
        Cursor c = db.getCursor("select tenncc from nhacungcap where Mancc='" + ma + "' ");
        c.moveToFirst();
        ten = c.getString(0);
        c.close();
        return ten;
    }

    // lấy tên NV từ Mã NV
    private String tennv(String ma) {
        String ten = null;
        db.copyDB2SDCard();
        Cursor c = db.getCursor("select tennv from nhanvien where Manv='" + ma + "' ");
        c.moveToFirst();
        ten = c.getString(0);
        c.close();
        return ten;
    }

    public int TongTien(String ma) {
        arCTHDN = new ArrayList<>();
        arCTHDN = DoccthdN(ma);
        int tien = 0;
        for (int i = 0; i < arCTHDN.size(); i++) {
            tien = (arCTHDN.get(i).getGianhap() * arCTHDN.get(i).getSoluong()) + tien;
        }
        return tien;
    }

    //Nút back trở về trang trc
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent it = new Intent(TTHoaDonNhap.this, QLHoaDonNhap.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }

    //   Đọc dữ liệu cho arrayList CTHDN theo Mã HDN truyền qua
    public ArrayList<CTHDN> DoccthdN(String maHDN) {
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT * FROM cthoadonnhap WHERE Mahdn='" + maHDN + "' ");
        c.moveToFirst();
        do {
            String mahdn = c.getString(0);
            String masp = c.getString(1);
            int solg = c.getInt(2);
            int giaN = c.getInt(3);
            String ten = LayTen(masp);
            arCTHDN.add(new CTHDN(mahdn, ten, solg, giaN));
        } while (c.moveToNext());
        adapCTHDN = new Adapter_CTHDN(this, R.layout.item_cthdn, arCTHDN);
        listCTHDN.setAdapter(adapCTHDN);
        return arCTHDN;
    }

    // lấy tên từ Mã
    public String LayTen(String ma) {
        String ten = " ";
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT tensp from sanpham where Masp='" + ma.toUpperCase() + "'");
        c.moveToFirst();
        ten = c.getString(0);
        c.close();
        return ten;
    }

    private void AnhXa() {
        listCTHDN = findViewById(R.id.listviewCTHDN);
        tvmaHDN = (TextView) findViewById(R.id.tvMaCTHDN);
        tvTennv = (TextView) findViewById(R.id.tvtenNVN);
        tvTenncc = (TextView) findViewById(R.id.tvNCCcthdn);
        tvTGNhap = (TextView) findViewById(R.id.tvTGNhapcthdn);
        tvttNhap = (TextView) findViewById(R.id.tvTTNhapcthdn);
    }
}
