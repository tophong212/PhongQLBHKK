package com.example.qlbhkk.SanPham;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

public class Themsp extends AppCompatActivity {
    EditText masp, mamh, tensp, nhanhieu, kichco, soluong, gia;
    Button them, huy;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themsp);

        anhxa();
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addsp();
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Themsp.this, QLsanpham.class);
                startActivity(it);
            }
        });
    }

    private void anhxa() {
        masp = findViewById(R.id.edt_masp);
        mamh = findViewById(R.id.edt_mamh_sp);
        tensp = findViewById(R.id.edt_tensp);
        nhanhieu = findViewById(R.id.edt_nhanhieu);
        kichco = findViewById(R.id.edt_size);
        soluong = findViewById(R.id.edt_soluong);
        gia = findViewById(R.id.edt_giaban);
        them = findViewById(R.id.bt_themsp);
        huy = findViewById(R.id.bt_huysp);
    }

    public boolean checkkc() {
        String kc = kichco.getText().toString();
        if (kc.toUpperCase().equals("Nhỏ") || kc.toUpperCase().equals("Vừa") || kc.toUpperCase().equals("Lớn"))
            return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void addsp() {
        String mas = masp.getText().toString().trim();
        String mah = mamh.getText().toString().trim();
        String tens = tensp.getText().toString();
        String nh = nhanhieu.getText().toString();
        String kc = kichco.getText().toString();
        int sl = Integer.valueOf(soluong.getText().toString());
        int gb = Integer.valueOf(gia.getText().toString());
        if (masp.length() == 0) {
            Toast.makeText(Themsp.this, "Nhập dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra sự tồn tại của mã sản phẩm
            int checkmasp = db.GetCount("Select * from sanpham where Masp=\"" + mas + "\"");
            int checkmamh = db.GetCount("Select * from mathang where Mamh=\"" + mah + "\"");

            if (checkmasp == 1) {
                // Nếu mã sản phẩm đã tồn tại đưa ra thông báo
                AlertDialog.Builder al = new AlertDialog.Builder(this);
                al.setTitle(" Thông báo ");
                al.setMessage("Mã sản phẩm đã tồn tại ! ");
                al.create().show();
            } else if (checkmasp == 0) {
                if (checkmamh == 0) {
                    Toast.makeText(Themsp.this, "Mã mặt hàng không tồn tại", Toast.LENGTH_SHORT).show();
                } else if (tens.length() == 0) {
                    Toast.makeText(Themsp.this, "Nhập dữ liệu cho tên sản phẩm", Toast.LENGTH_SHORT).show();
                } else if (nh.length() == 0) {
                    Toast.makeText(Themsp.this, "Nhập dữ liệu cho nhãn hiệu", Toast.LENGTH_SHORT).show();
                } else if (checkkc() == true) {
                    Toast.makeText(Themsp.this, "Nhập dữ liệu cho kích cỡ", Toast.LENGTH_SHORT).show();
                } else if (sl <= 0) {
                    Toast.makeText(Themsp.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                } else if (gb < 0) {
                    Toast.makeText(Themsp.this, "Giá bán phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm dữ liệu vào database
                    db.executeSQL("INSERT INTO sanpham VALUES('" + mas + "','" + mah + "','" + tens + "','" + nh + "','"
                            + kc + "','" + sl + "','" + gb + "')");
                    Toast.makeText(getApplicationContext(), "Thêm thành công!!!", Toast.LENGTH_LONG).show();
                    Intent back = new Intent(Themsp.this, QLsanpham.class);
                    startActivity(back);
                }
            }
        }
    }
}
