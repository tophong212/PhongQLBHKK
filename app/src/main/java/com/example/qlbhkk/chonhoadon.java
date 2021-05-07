package com.example.qlbhkk;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.HoaDonNhap.QLHoaDonNhap;
import com.example.qlbhkk.HoaDonNhap.ThemHDN;
import com.example.qlbhkk.HoaDonXuat.QLHoaDonXuat;

public class chonhoadon extends AppCompatActivity {
    ImageButton hdn, hdx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chonhoadon);
        hdn = findViewById(R.id.imbhdn);
        hdx = findViewById(R.id.imbhdx);

        hdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(com.example.qlbhkk.chonhoadon.this, QLHoaDonNhap.class);
                startActivity(it);
            }
        });

        hdx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(com.example.qlbhkk.chonhoadon.this, QLHoaDonXuat.class);
                startActivity(it);
            }
        });
    }
}
