package com.example.qlbhkk.NhanVien;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

public class Themnv extends AppCompatActivity {
    EditText manv,tennv, gioitinh, namsinh,diachi,sdt;
    Button them, huy;
    DatabaseHandler db = new DatabaseHandler( this );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themnv);
        manv=findViewById(R.id.edtmanv);
        tennv = findViewById(R.id.edttennv);
        gioitinh = findViewById(R.id.edtgt);
        namsinh = findViewById(R.id.edtns);
        diachi = findViewById(R.id.edtdiachi);
        sdt = findViewById(R.id.edtsodienthoai);
        them=findViewById(R.id.bt_themnv);
        huy=findViewById(R.id.bt_huynv);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnv();
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(Themnv.this,QLnhanvien.class);
                startActivity(it);
            }
        });
    }

    public void addnv() {
        String ma = manv.getText().toString().trim();
        String ten = tennv.getText().toString().trim();
        String gt= gioitinh.getText().toString();
        int ns = Integer.valueOf(namsinh.getText().toString());
        String dc = diachi.getText().toString();
        String dt = sdt.getText().toString();
        if (manv.length() == 0) {
            Toast.makeText( Themnv.this, "Nhập dữ liệu", Toast.LENGTH_SHORT ).show();
        } else {
            // Kiểm tra sự tồn tại của mã nhân viên
            int checkmanv = db.GetCount( "Select * from nhanvien where Manv=\"" + ma + "\"" );
            if (checkmanv == 1) {
                // Nếu mã nhân viên đã tồn tại đưa ra thông báo
                AlertDialog.Builder al = new AlertDialog.Builder( this );
                al.setTitle( " Thông báo " );
                al.setMessage( "Mã nhân viên đã tồn tại ! " );
                al.create().show();
            } else if (checkmanv == 0) {
                if (ten.length() == 0) {
                    Toast.makeText( Themnv.this, "Nhập dữ liệu cho tên nhân viên", Toast.LENGTH_SHORT ).show();
                } else if (!(gt.equalsIgnoreCase("Nam")||gt.equalsIgnoreCase("Nữ")||gt.equalsIgnoreCase("Nu"))) {
                    Toast.makeText( Themnv.this, "Giới tính không hợp lệ", Toast.LENGTH_SHORT ).show();
                } else if(ns<1985 || ns>2002){
                    Toast.makeText( Themnv.this, "Nhập dữ liệu cho năm sinh", Toast.LENGTH_SHORT ).show();
                } else if(dc.length()==0){
                    Toast.makeText( Themnv.this, "Nhập dữ liệu cho địa chỉ", Toast.LENGTH_SHORT ).show();
                } else if(dt.length()!=10) {
                    Toast.makeText(Themnv.this, "Số điện thoại phải 10 chữ số", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Thêm dữ liệu vào database
                    db.executeSQL( "INSERT INTO nhanvien VALUES('" + ma + "','" + ten + "','" + gt +  "'," + ns + ",'"
                            + dc + "','" + dt  +"')" );
                    Toast.makeText( getApplicationContext(), "Thêm thành công!!!", Toast.LENGTH_LONG ).show();
                    Intent intent = new Intent( Themnv.this, QLnhanvien.class );
                    startActivity( intent );
                    //finish();
                }
            }
        }
    }
}
