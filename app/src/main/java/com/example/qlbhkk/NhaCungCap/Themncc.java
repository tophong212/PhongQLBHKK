package com.example.qlbhkk.NhaCungCap;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

public class Themncc extends AppCompatActivity {
    EditText mancc, tenncc, diachi, sdt;
    Button them, huy;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themncc);
        mancc = findViewById(R.id.edt_themmancc);
        tenncc = findViewById(R.id.edt_themtenncc);
        diachi = findViewById(R.id.edt_themdcncc);
        sdt = findViewById(R.id.edt_themsdtncc);
        them = findViewById(R.id.bt_themncc);
        huy = findViewById(R.id.bt_huyncc);

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addncc();
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Themncc.this, QLnhacungcap.class);
                startActivity(it);
            }
        });
    }

    public void addncc() {
        String ma = mancc.getText().toString().trim();
        String ten = tenncc.getText().toString().trim();
        String dc = diachi.getText().toString();
        String dt = sdt.getText().toString();
        if (mancc.length() == 0) {
            Toast.makeText(Themncc.this, "Nhập dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra sự tồn tại của mã nhân viên
            int checkmancc = db.GetCount("Select * from nhacungcap where Mancc=\"" + ma + "\"");
            if (checkmancc == 1) {
                // Nếu mã nhân viên đã tồn tại đưa ra thông báo
                AlertDialog.Builder al = new AlertDialog.Builder(this);
                al.setTitle(" Thông báo ");
                al.setMessage("Mã nhân viên đã tồn tại ! ");
                al.create().show();
            } else if (checkmancc == 0) {
                if (ten.length() == 0) {
                    Toast.makeText(Themncc.this, "Nhập dữ liệu cho tên nhà cung cấp", Toast.LENGTH_SHORT).show();
                } else if (dc.length() == 0) {
                    Toast.makeText(Themncc.this, "Nhập dữ liệu cho địa chỉ", Toast.LENGTH_SHORT).show();
                } else if (dt.length() != 10) {
                    Toast.makeText(Themncc.this, "Số điện thoại phải có 10 chữ số", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm dữ liệu vào database
                    db.executeSQL("insert into nhacungcap values('" + ma + "','" + ten + "','"
                            + dc + "','" + dt + "')");
                    Toast.makeText(getApplicationContext(), "Thêm thành công!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Themncc.this, QLnhacungcap.class);
                    startActivity(intent);
                }
            }
        }
    }
}
