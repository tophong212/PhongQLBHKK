package com.example.qlbhkk.HoaDonNhap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import com.example.qlbhkk.NhaCungCap.NhaCungCap;
import com.example.qlbhkk.NhanVien.NhanVien;
import com.example.qlbhkk.R;
import com.example.qlbhkk.chonhoadon;

import java.util.ArrayList;

public class QLHoaDonNhap extends AppCompatActivity {
    ArrayList<HoaDonNhap> arrhdn = null;
    public Adapter_HDN adpHDN;
    ListView lv;
    ImageButton them, chonHD, timkiem;
    int ps;
    DatabaseHandler db = new DatabaseHandler(this);
    EditText tk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlhoadonnhap);
        db.copyDB2SDCard();
        //anh xa
        anhxa();
        ////////////////////////////////
        db2ListView();

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(QLHoaDonNhap.this, ThemHDN.class);
                startActivity(in);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent(QLHoaDonNhap.this, TTHoaDonNhap.class);
                intent.putExtra("Mahdn", arrhdn.get(i).getMaHDN());
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                ps = position;
                HoaDonNhap sp = arrhdn.get(ps);
                Xoa(sp.maHDN);
                //sua_xoa();
                return true;
            }
        });
        //về menu chọn hoá đơn
        chonHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLHoaDonNhap.this, MainActivity.class);
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
        lv = findViewById(R.id.lv_HDN);
        them = (ImageButton) findViewById(R.id.imbt_ThemHDN);
        chonHD = (ImageButton) findViewById(R.id.imbt_BackHDN);
        timkiem = (ImageButton) findViewById(R.id.imbt_TimHDN);
        tk = findViewById(R.id.edt_timHDN);
    }

    public void db2ListView() {
        arrhdn = new ArrayList<HoaDonNhap>();
        HoaDonNhap row;
        Cursor cursor = db.getCursor("SELECT * FROM hoadonnhap");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String maHDN = cursor.getString(0);
            String maNV = cursor.getString(1);
            String maNCC = cursor.getString(2);
            String ngay = cursor.getString(3);
            String tenncc = tenncc(maNCC);
            String tennv = tennv(maNV);
            row = new HoaDonNhap(maHDN, tennv, tenncc, ngay);
            arrhdn.add(row);
            cursor.moveToNext();
        }

        adpHDN = new Adapter_HDN(this, R.layout.item_hdn, arrhdn);
        lv.setAdapter(adpHDN);
        adpHDN.notifyDataSetChanged();
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

    //Lấy tên NCC từ mã
    ArrayList<NhaCungCap> arrncc;

    public ArrayList<NhaCungCap> GetNCC() {
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT * FROM nhacungcap");
        c.moveToFirst();
        do {
            String MaNCC = c.getString(0);
            String TenNCC = c.getString(1);
            String DiaChi = c.getString(2);
            String SDT = c.getString(3);
            arrncc.add(new NhaCungCap(MaNCC, TenNCC, DiaChi, SDT));
        } while (c.moveToNext());
        return arrncc;
    }

    public String tenncc(String ma) {
        arrncc = new ArrayList<>();
        arrncc = GetNCC();
        String t = "...";
        for (int i = 0; i < arrncc.size(); i++) {
            if (arrncc.get(i).getMaNCC().toLowerCase().equals(ma.toLowerCase()))
                t = arrncc.get(i).getTenNCC();
        }
        return t;
    }

    public void Xoa(final String Ma) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QLHoaDonNhap.this);
        builder.setTitle("Xóa !");
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrhdn.remove(ps);
                adpHDN.notifyDataSetChanged();
                //adapter.notifyDataSetChanged();
                Toast.makeText(QLHoaDonNhap.this, "Dữ liệu đã bị xóa", Toast.LENGTH_LONG).show();
                db.executeSQL("Delete FROM hoadonnhap where Mahdn ='" + Ma + "'");
                db2ListView();
            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(QLHoaDonNhap.this, "Dữ liệu không bị xóa", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    public void Timkiem() {
        HoaDonNhap r;
        String tim = tk.getText().toString();
        if (db.GetCount("SELECT h.* FROM hoadonnhap h inner join nhanvien nv on nv.Manv=h.Manv inner join nhacungcap c on c.Mancc=h.Mancc WHERE Mahdn like '%" + tim + "%' or h.Manv like '%" + tim +
                "%'or h.Mancc like '%" + tim + "%' or ngaynhap like '%" + tim + "%' or tennv like '%" + tim + "%' or tenncc like '%" + tim + "%'") == 0) {
            Toast.makeText(this, "Thông tin chưa tồn tại", Toast.LENGTH_LONG).show();
        } else {
            arrhdn = new ArrayList<HoaDonNhap>();
            Cursor c = db.getCursor("SELECT h.* FROM hoadonnhap h inner join nhanvien nv on nv.Manv=h.Manv inner join nhacungcap c on c.Mancc=h.Mancc WHERE Mahdn like '%" + tim + "%' or h.Manv like '%" + tim +
                    "%'or h.Mancc like '%" + tim + "%' or ngaynhap like '%" + tim + "%' or tennv like '%" + tim + "%' or tenncc like '%" + tim + "%'");
            c.moveToFirst();
            do {
                String mahdn = c.getString(0);
                String manv = c.getString(1);
                String mancc = c.getString(2);
                String ngay = c.getString(3);
                String tenNV = tennv(manv);
                String tenNCC = tenncc(mancc);
                arrhdn.add(new HoaDonNhap(mahdn, tenNV, tenNCC, ngay));
            } while (c.moveToNext());
            adpHDN = new Adapter_HDN(this, R.layout.item_hdn, arrhdn);
            lv.setAdapter(adpHDN);
        }
    }
}
