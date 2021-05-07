package com.example.qlbhkk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.qlbhkk.MatHang.QLmathang;
import com.example.qlbhkk.NhaCungCap.QLnhacungcap;
import com.example.qlbhkk.NhanVien.QLnhanvien;
import com.example.qlbhkk.SanPham.QLsanpham;
import com.example.qlbhkk.Thongke.Thongke;

public class MainActivity extends AppCompatActivity {
    ImageButton mathang, sanpham, nhanvien, nhacungcap, hoadon, thongke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.gdchinh);
        setContentView(R.layout.activity_main);
        Init();
        Event();
    }

    private void Event() {
        mathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, QLmathang.class);
                startActivity(it);
            }
        });
        sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, QLsanpham.class);
                startActivity(it);
            }
        });
        nhacungcap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, QLnhacungcap.class);
                startActivity(it);
            }
        });
        nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, QLnhanvien.class);
                startActivity(it);
            }
        });
        hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, chonhoadon.class);
                startActivity(it);
            }
        });
        thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, Thongke.class);
                startActivity(it);
            }
        });
    }

    private void Init() {
        mathang = findViewById(R.id.imbtn_MatHang);
        sanpham = findViewById(R.id.imbtn_SanPham);
        nhanvien = findViewById(R.id.imbtn_NhanVien);
        nhacungcap = findViewById(R.id.imbtn_NhaCungCap);
        hoadon = findViewById(R.id.imbtn_HoaDon);
        thongke = findViewById(R.id.imbtn_ThongKe);
    }
}
