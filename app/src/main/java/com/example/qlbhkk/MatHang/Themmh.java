package com.example.qlbhkk.MatHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

public class Themmh extends AppCompatActivity {
    //biến toàn cục
//    ArrayList<MatHang> arrList = null;
//    ArrayAdapter<MatHang> adap = null;
    EditText ma, ten, mota;

    Button them, huy;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // anh xa
        setContentView(R.layout.themmh);
        anhxa();

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addmh();
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Themmh.this, QLmathang.class);
                startActivity(it);
            }
        });
    }

    private void anhxa() {
        ma = (EditText) findViewById(R.id.edtmamh);
        ten = (EditText) findViewById(R.id.edttenmh);
        mota = (EditText) findViewById(R.id.edtmota);
        them = (Button) findViewById(R.id.bt_themmh);
        huy = (Button) findViewById(R.id.bt_huymh);
    }

    public void addmh() {
        String mamh = ma.getText().toString().trim();
        String tenmh = ten.getText().toString();
        String motamh = mota.getText().toString();
        if (mamh.length() == 0) {
            Toast.makeText(Themmh.this, "Nhập dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra sự tồn tại của mã sản phẩm
            int checkma = db.GetCount("Select * from mathang where Mamh=\"" + mamh + "\"");
            if (checkma == 1) {
                // Nếu mã cây đã tồn tại đưa ra thông báo
                AlertDialog.Builder al = new AlertDialog.Builder(this);
                al.setTitle(" Thông báo ");
                al.setMessage("Mã mặt hàng đã tồn tại ! ");
                al.create().show();
            } else if (checkma == 0) {
                if (tenmh.length() == 0) {
                    Toast.makeText(Themmh.this, "Nhập dữ liệu", Toast.LENGTH_SHORT).show();
                } else if (motamh.length() == 0) {
                    Toast.makeText(Themmh.this, "Nhập dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm dữ liệu vào database
                    db.executeSQL("insert into mathang values('" + mamh + "','" + tenmh + "','" + motamh + "')");
                    Toast.makeText(getApplicationContext(), "Thêm thành công!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Themmh.this, QLmathang.class);
                    startActivity(intent);
                }
            }
        }
    }
}
