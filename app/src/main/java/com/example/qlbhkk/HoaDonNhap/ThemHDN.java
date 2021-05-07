package com.example.qlbhkk.HoaDonNhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.HoaDonXuat.QLHoaDonXuat;
import com.example.qlbhkk.HoaDonXuat.ThemHDX;
import com.example.qlbhkk.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThemHDN extends AppCompatActivity {
    private EditText edtMaNCC, edtMaNV, edtmasp, edtslg, edtgianhap;
    public ListView listHienCTHDN, lv;
    private EditText edtThemmaHDN;
    private Button btThemHDN, btThemCTHDN;
    public String masp, manv, mancc, maHDN, tgNhap;
    public int slgN;
    public int giaN;
    private Adapter_CTHDN adapCTHDN;
    private ArrayList<CTHDN> arCTHDN = new ArrayList<>();
    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themhdn);
        /////////////////////////////////////
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //ánh xạ
        AnhXa();
        //thoigian
        TG();

        btThemHDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manv = edtMaNV.getText().toString().trim().toUpperCase();
                mancc = edtMaNCC.getText().toString().trim().toUpperCase();
                if (CheckNV(manv) && CheckNCC(mancc)) ThemHDN();
                Intent it = new Intent(ThemHDN.this, QLHoaDonNhap.class);
                startActivity(it);
            }
        });

        btThemCTHDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDLCTHD()) {
                    ThemCTHDN();
                } else {
                    Toast.makeText(ThemHDN.this, "Bạn chưa nhập đủ hoặc sai thông tin. Hãy nhập lại !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent t = new Intent(ThemHDN.this, QLHoaDonNhap.class);
            startActivity(t);
        }
        return super.onOptionsItemSelected(item);
    }

    public void ThemHDN() {
        maHDN = edtThemmaHDN.getText().toString().toUpperCase();
        mancc = edtMaNCC.getText().toString().trim().toUpperCase();
        manv = edtMaNV.getText().toString().trim().toUpperCase();
        db.executeSQL("INSERT INTO hoadonnhap VALUES ( '" + maHDN + "','" + manv + "','" + mancc + "','" + tgNhap + "')");
        Toast.makeText(this, "Đã thêm thông tin Hóa đơn nhập", Toast.LENGTH_SHORT).show();
        edtMaNCC.setText("");
        edtMaNV.setText("");
        edtThemmaHDN.setText("");
    }

    private boolean getDLCTHD() {
        if (edtmasp.getText().toString().trim().equals("")) return false;
        else masp = edtmasp.getText().toString().trim();
        if (edtslg.getText().toString().trim().equals("")) {
            return false;
        } else slgN = Integer.valueOf(edtslg.getText().toString().trim());
        if (slgN <= 0) return false;
        if (edtgianhap.getText().toString().trim().equals("")) {
            return false;
        } else giaN = Integer.valueOf(edtgianhap.getText().toString().trim());
        if (giaN < 1000) return false;
        return true;
    }

    private boolean CheckNV(String ma) {
        if (db.GetCount("SELECT * FROM nhanvien WHERE Manv='" + ma + "' ") == 0) {
            Toast.makeText(this, "Bạn chưa nhập hoặc mã Nhân Viên không tồn tại !", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private boolean CheckNCC(String ma) {
        if (db.GetCount("SELECT * FROM nhacungcap WHERE Mancc='" + ma + "' ") == 0) {
            Toast.makeText(this, "Bạn chưa nhập hoặc mã Nhà cung cấp không tồn tại !", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    public void TG() {
        // Lấy ngày hiện tại
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        tgNhap = dateFormat.format(today);
    }

    private void ThemCTHDN() {
        maHDN = edtThemmaHDN.getText().toString().toUpperCase();
        db.executeSQL("INSERT INTO cthoadonnhap VALUES ( '" + maHDN + "','" + masp + "', '" + slgN + "', '" + giaN + "')");
        if (db.GetCount("SELECT * FROM sanpham WHERE Masp='" + masp.toUpperCase() + "' ") == 1) {
            db.executeSQL("UPDATE sanpham SET soluong= soluong+ '" + slgN + "' WHERE Masp='" + masp.toUpperCase() + "' ");
        } else {
            Intent it = new Intent(ThemHDN.this, Themsp_hdn.class);
            startActivity(it);
        }
        // Hiện thông tin chi tiet hóa đơn vừa nhập
        arCTHDN.add(new CTHDN(maHDN, masp, slgN, giaN));
        adapCTHDN = new Adapter_CTHDN(this, R.layout.item_themcthdn, arCTHDN);
        listHienCTHDN.setAdapter(adapCTHDN);
        adapCTHDN.notifyDataSetChanged();
        // Xóa dữ liệu ở edt
        edtmasp.setText("");
        edtslg.setText("");
        edtgianhap.setText("");
    }

    private void AnhXa() {
        edtThemmaHDN = (EditText) findViewById(R.id.edtthemmaHDN);
        edtMaNCC = (EditText) findViewById(R.id.edtmaNCC);
        edtMaNV = (EditText) findViewById(R.id.edtthemmaNV);
        edtmasp = (EditText) findViewById(R.id.ctnMasp);
        edtslg = (EditText) findViewById(R.id.ctnSLg);
        edtgianhap = (EditText) findViewById(R.id.ctnGia);
        btThemCTHDN = (Button) findViewById(R.id.btthemChitietN);
        btThemHDN = (Button) findViewById(R.id.btThemhdn);
        listHienCTHDN = (ListView) findViewById(R.id.listHienCTHDN);
    }

    //về Menu quản lý hoá đơn nhập
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ThemHDN.this, QLHoaDonNhap.class);
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }
}
