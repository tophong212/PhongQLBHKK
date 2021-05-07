package com.example.qlbhkk.HoaDonXuat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.MainActivity;
import com.example.qlbhkk.NhanVien.NhanVien;
import com.example.qlbhkk.R;
import com.example.qlbhkk.chonhoadon;

import java.util.ArrayList;

public class QLHoaDonXuat extends AppCompatActivity {
    ArrayList<HoaDonXuat> arrhdx;
    private Adapter_HDX adpHDX;
    ListView lv;
    ImageButton them, chonHD, timkiem;
    int ps;
    DatabaseHandler db = new DatabaseHandler(this);
    EditText tk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlhoadonxuat);
        db.copyDB2SDCard();
        //anh xa
        anhxa();
        ////////////////////////////////
        db2ListView();

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(QLHoaDonXuat.this, ThemHDX.class);
                startActivity(in);

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(QLHoaDonXuat.this, TTHoaDonXuat.class);
                intent.putExtra("Mahdx", arrhdx.get(i).getMaHDX());
                startActivity(intent);

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                ps = position;
                HoaDonXuat sp = arrhdx.get(ps);
                Xoa(sp.maHDX);
                //sua_xoa();
                return true;
            }
        });

        //Về Menu chọn hoá đơn
        chonHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLHoaDonXuat.this, chonhoadon.class);
                startActivity(intent);
            }
        });
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timkiem();
            }
        });
    }

    private void anhxa() {
        lv = findViewById(R.id.lv_HDX);
        them = (ImageButton) findViewById(R.id.imbt_ThemHDX);
        chonHD = (ImageButton) findViewById(R.id.imbt_BackHDX);
        timkiem = (ImageButton) findViewById(R.id.imbt_TimHDX);
        tk = findViewById(R.id.edt_timHDX);
    }

    public void db2ListView() {
        arrhdx = new ArrayList<HoaDonXuat>();
        HoaDonXuat row;
        Cursor cursor = db.getCursor("SELECT * FROM hoadonxuat");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            String maHDX = cursor.getString(0);
            String maNV = cursor.getString(1);
            String ngay = cursor.getString(2);
            //int tt=TongTien(maHDN);
            String tennv = tennv(maNV);
            row = new HoaDonXuat(maHDX, tennv, ngay);

            arrhdx.add(row);
            cursor.moveToNext();
        }
        adpHDX = new Adapter_HDX(this, R.layout.item_hdx, arrhdx);
        lv.setAdapter(adpHDX);
        adpHDX.notifyDataSetChanged();
        cursor.close();
    }

    //Lấy tên NV từ mã
    ArrayList<NhanVien> arrnv;

    public ArrayList<NhanVien> GetNV() {
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT * FROM nhanvien");
        c.moveToFirst();
        do {
            String MaNV = c.getString(0);
            String TenNV = c.getString(1);
            String GioiTinh = c.getString(2);
            int NamSinh = c.getInt(3);
            String SDT = c.getString(5);
            String DiaChi = c.getString(4);
            arrnv.add(new NhanVien(MaNV, TenNV, GioiTinh, NamSinh, DiaChi, SDT));
        } while (c.moveToNext());
        return arrnv;
    }

    public String tennv(String ma) {
        arrnv = new ArrayList<>();
        arrnv = GetNV();
        String t = "...";
        for (int i = 0; i < arrnv.size(); i++) {
            if (arrnv.get(i).getMaNV().toLowerCase().equals(ma.toLowerCase()))
                t = arrnv.get(i).getTenNV();
        }
        return t;
    }

    public void Xoa(final String Ma) {

        AlertDialog.Builder builder = new AlertDialog.Builder(QLHoaDonXuat.this);
        builder.setTitle("Xóa !");
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrhdx.remove(ps);
                adpHDX.notifyDataSetChanged();
                Toast.makeText(QLHoaDonXuat.this, "Dữ liệu đã bị xóa", Toast.LENGTH_LONG).show();
                db.executeSQL("Delete FROM hoadonxuat where Mahdx ='" + Ma + "'");
                db2ListView();

            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(QLHoaDonXuat.this, "Dữ liệu không bị xóa", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    public void Timkiem() {
        HoaDonXuat r;
        String tim = tk.getText().toString();
        if (db.GetCount("SELECT h.* FROM hoadonxuat h inner join nhanvien nv on nv.manv=h.manv WHERE Mahdx like '%" + tim + "%' or h.Manv like '%" + tim +
                "%' or ngayban like '%" + tim + "%' or tennv like '%" + tim + "%'") == 0) {
            Toast.makeText(this, "Thông tin chưa tồn tại", Toast.LENGTH_LONG).show();
        } else {
            arrhdx = new ArrayList<HoaDonXuat>();
            Cursor c = db.getCursor("SELECT h.* FROM hoadonxuat h inner join nhanvien nv on nv.manv=h.manv WHERE Mahdx like '%" + tim + "%' or h.Manv like '%" + tim +
                    "%' or ngayban like '%" + tim + "%' or tennv like '%" + tim + "%'");
            c.moveToFirst();
            do {
                String mahdn = c.getString(0);
                String manv = c.getString(1);
                String ngay = c.getString(2);
                String tenNV = tennv(manv);
                //int tt=TongTien(mahdn);
                arrhdx.add(new HoaDonXuat(mahdn, tenNV, ngay));
            } while (c.moveToNext());
            adpHDX = new Adapter_HDX(this, R.layout.item_hdx, arrhdx);
            lv.setAdapter(adpHDX);
        }
    }
}
