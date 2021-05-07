package com.example.qlbhkk.Thongke;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.R;
import com.example.qlbhkk.Thongke.tkncc.tkncc;
import com.example.qlbhkk.Thongke.tkngay.thunhap;
import com.example.qlbhkk.Thongke.tksanphambc.tksanpham;
import com.example.qlbhkk.Thongke.tkslhang_nhap_xuat.tksl_nhapxuat;
import com.example.qlbhkk.Thongke.tkthang.tkthang;

public class Thongke extends AppCompatActivity {
    Button ngay, thang, sanpham,ncc,soluong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chonthongke);
        ngay = (Button) findViewById(R.id.bttkdoanhthun);
        thang = (Button) findViewById(R.id.bttkdoanhthut);
        sanpham=(Button) findViewById(R.id.bttksanpham);
        ncc=(Button) findViewById(R.id.bttkmh);
        soluong=(Button) findViewById(R.id.bttkslh);
        ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Thongke.this, thunhap.class);
                startActivity(it);
            }
        });
        thang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Thongke.this, tkthang.class);
                startActivity(it);
            }
        });
        sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Thongke.this, tksanpham.class);
                startActivity(it);
            }
        });
        ncc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Thongke.this, tkncc.class);
                startActivity(it);
            }
        });
        soluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Thongke.this, tksl_nhapxuat.class);
                startActivity(it);
            }
        });
    }
}
