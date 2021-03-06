package com.example.qlbhkk.HoaDonXuat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThemHDX extends AppCompatActivity {
    private EditText edtMaNV, edtmasp, edtslg;
    public ListView listHienCTHDX;
    private EditText edtThemmaHDX;
    private Button btThemHDX, btThemCTHDX;
    public String masp, manv, maHDX, tgXuat;
    public int slg, giaban;
    TextView dg;
    private Adapter_HDX adapHDX;
    private ArrayList<HoaDonXuat> arHDX = new ArrayList<>();
    private Adapter_CTHDX adapCTHDX;
    private ArrayList<CTHDX> arCTHDX = new ArrayList<>();
    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themhdx);
        /////////////////////////////////////////////
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        AnhXa();

        TG();
        btThemHDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manv = edtMaNV.getText().toString().trim().toUpperCase();
                if (CheckNV(manv)) ThemHDX();
                Intent it = new Intent(ThemHDX.this, QLHoaDonXuat.class);
                startActivity(it);
            }
        });
        btThemCTHDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GetDL()) {
                    ThemCTHDX();
                } else {
                    Toast.makeText(ThemHDX.this, "B???n ch??a nh???p ????? ho???c sai th??ng tin. H??y nh???p l???i !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ThemHDX() {
        maHDX = edtThemmaHDX.getText().toString().toUpperCase();
        manv = edtMaNV.getText().toString().trim().toUpperCase();
        db.executeSQL("INSERT INTO hoadonxuat VALUES ( '" + maHDX + "','" + manv + "','" + tgXuat + "')");
        Toast.makeText(this, "???? th??m th??ng tin H??a ????n xu???t", Toast.LENGTH_SHORT).show();
        edtMaNV.setText("");
        edtThemmaHDX.setText("");
    }

    public boolean GetDL() {
        if (edtmasp.getText().toString().trim().equals("") || edtslg.getText().toString().trim().equals("")) {
            Toast.makeText(this, "B???n ch??a nh???p ????? th??ng tin Chi ti???t h??a ????n !", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            masp = edtmasp.getText().toString().trim();
            slg = Integer.parseInt(edtslg.getText().toString().trim());
            if (CheckMa(masp)) {
                if (CheckSL(masp, slg)) return true;
                else return false;
            } else return false;
        }
    }

    private boolean CheckNV(String ma) {
        if (db.GetCount("SELECT * FROM nhanvien WHERE Manv='" + ma.toUpperCase() + "' ") == 0) {
            Toast.makeText(this, "B???n ch??a nh???p ho???c m?? Nh??n Vi??n kh??ng t???n t???i !", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private boolean CheckMa(String ma) {
        if (db.GetCount("SELECT * FROM sanpham WHERE Masp='" + ma.toUpperCase() + "' ") == 0) {
            Toast.makeText(this, "Kh??ng t???n t???i s??? ph???m c?? m?? " + ma + ". Vui l??ng nh???p l???i !", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private boolean CheckSL(String ma, int slg) {
        if (db.GetCount("SELECT * from sanpham where Masp='" + ma.toUpperCase() + "' and soluong >='" + slg + "' ") == 0) {
            Toast.makeText(this, "S??? l?????ng mua v?????t qu?? s??? l?????ng c??n trong kho !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void TG() {
        // L???y ng??y hi???n t???i
        DateFormat dateFormatter1 = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        tgXuat = dateFormatter1.format(today);
    }

    private void ThemCTHDX() {
        //giaban=Integer.valueOf(dg.getText().toString());
        maHDX = edtThemmaHDX.getText().toString().toUpperCase();
        db.executeSQL("INSERT INTO cthoadonxuat VALUES ( '" + maHDX + "','" + masp + "', '" + slg + "')");
        if (db.GetCount("SELECT * FROM sanpham WHERE Masp='" + masp.toUpperCase() + "' ") == 1) {
            db.executeSQL("UPDATE sanpham SET soluong= soluong- '" + slg + "' WHERE Masp='" + masp.toUpperCase() + "' ");
        } else {
            Toast.makeText(this, "S???n ph???m kh??ng t???n t???i", Toast.LENGTH_SHORT).show();
        }
        // Hi???n th??ng tin chi tiet h??a ????n v???a nh???p
        arCTHDX.add(new CTHDX(maHDX, masp, slg, GiaSP(masp)));
        adapCTHDX = new Adapter_CTHDX(this, R.layout.item_themcthdx, arCTHDX);
        listHienCTHDX.setAdapter(adapCTHDX);
        // X??a d??? li???u ??? edt
        edtmasp.setText("");
        //edttensp.setText("");
        edtslg.setText("");
    }

    private int GiaSP(String ma) {
        int gia;
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT giaban FROM sanpham WHERE Masp='" + ma.toUpperCase() + "' ");
        c.moveToFirst();
        gia = c.getInt(0);
        c.close();
        return gia;
    }

    private void AnhXa() {

        edtMaNV = (EditText) findViewById(R.id.edtthemmaNVX);
        edtmasp = (EditText) findViewById(R.id.ctxMasp);
        edtslg = (EditText) findViewById(R.id.ctxSLg);
        btThemCTHDX = (Button) findViewById(R.id.btthemChitietX);
        btThemHDX = (Button) findViewById(R.id.btThemhdx);
        listHienCTHDX = (ListView) findViewById(R.id.listHienCTHDX);
        edtThemmaHDX = (EditText) findViewById(R.id.tvthemmaHDX);
        dg = (TextView) findViewById(R.id.tvdgXuat);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ThemHDX.this, QLHoaDonXuat.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
