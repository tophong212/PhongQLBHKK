package com.example.qlbhkk.NhanVien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.MainActivity;
import com.example.qlbhkk.R;

import java.util.ArrayList;

public class QLnhanvien extends AppCompatActivity {
    ArrayList<NhanVien> arrnv = null;
    Adapter_NhanVien adapter = null;
    NhanVien nv = new NhanVien();
    ListView lv;
    ImageButton them, trangchu, timkiem;
    int ps;
    DatabaseHandler db = new DatabaseHandler(this);
    EditText tk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlnhanvien);
        db.copyDB2SDCard();
        //anh xa
        anhxa();
        ////////////////////////////////
        db2ListView();

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QLnhanvien.this, Themnv.class);
                startActivity(intent);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(QLnhanvien.this, CTnhanvien.class);
                intent.putExtra("Manv", arrnv.get(i).getMaNV().toString());
                startActivity(intent);

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                ps = position;
                sua_xoa();
                return true;
            }
        });
        trangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLnhanvien.this, MainActivity.class);
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
        lv = findViewById(R.id.lv_NV);
        them = (ImageButton) findViewById(R.id.imbt_ThemNV);
        trangchu = (ImageButton) findViewById(R.id.imbt_BackNV);
        timkiem = (ImageButton) findViewById(R.id.imbt_TimNV);
        tk = findViewById(R.id.edt_timNV);
    }

    public void db2ListView() {
        arrnv = new ArrayList<NhanVien>();
        NhanVien row;
        Cursor cursor = db.getCursor("SELECT * FROM nhanvien");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new NhanVien();
            row.maNV = cursor.getString(0);
            row.tenNV = cursor.getString(1);
            row.gioiTinh = cursor.getString(2);
            row.namSinh = cursor.getInt(3);
            row.diaChi = cursor.getString(4);
            row.soDT = cursor.getString(5);
            arrnv.add(row);
            cursor.moveToNext();
        }
        adapter = new Adapter_NhanVien(this, R.layout.item_nhanvien, arrnv);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    Dialog dialog1;

    public void sua_xoa() {
        dialog1 = new Dialog(QLnhanvien.this);
        dialog1.setContentView(R.layout.sua_xoanv);
        dialog1.show();
        final NhanVien nv = arrnv.get(ps);
        final TextView manv;
        final EditText tennv, gioitinh, namsinh, diachi, sdt;
        final Button btnSua, btnXoa;
        manv = dialog1.findViewById(R.id.tvmanv);
        tennv = dialog1.findViewById(R.id.edttennv);
        gioitinh = dialog1.findViewById(R.id.edtgt);
        namsinh = dialog1.findViewById(R.id.edtns);
        diachi = dialog1.findViewById(R.id.edtdiachinv);
        sdt = dialog1.findViewById(R.id.edtsdtnv);

        btnSua = (Button) dialog1.findViewById(R.id.bt_suanv);
        btnXoa = (Button) dialog1.findViewById(R.id.bt_xoanv);
        String m = nv.getMaNV();
        String t = nv.getTenNV();
        Integer n = nv.getNamSinh();
        String g = nv.getGioiTinh();
        String d = nv.getDiaChi();
        String s = nv.getSoDT();
        manv.setText(m);
        tennv.setText(t);
        namsinh.setText(n.toString());
        diachi.setText(d);
        gioitinh.setText(g);
        sdt.setText(s);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = manv.getText().toString();
                String ten = tennv.getText().toString();
                String gt = gioitinh.getText().toString();
                int ns = Integer.valueOf(namsinh.getText().toString());
                String dc = diachi.getText().toString();
                String dt = sdt.getText().toString();
                if (ten.length() == 0) {
                    Toast.makeText(QLnhanvien.this, "Nhập dữ liệu cho tên nhân viên", Toast.LENGTH_SHORT).show();
                } else if (!(gt.equals("Nam") || gt.equals("Nữ")|| gt.equals("Nu"))) {
                    Toast.makeText(QLnhanvien.this, "Giới tính không hợp lệ", Toast.LENGTH_SHORT).show();
                } else if (ns < 1985 || ns > 2001) {
                    Toast.makeText(QLnhanvien.this, "Nhập dữ liệu cho năm sinh", Toast.LENGTH_SHORT).show();
                } else if (dc.length() == 0) {
                    Toast.makeText(QLnhanvien.this, "Nhập dữ liệu cho địa chỉ", Toast.LENGTH_SHORT).show();
                } else if (dt.length() != 10) {
                    Toast.makeText(QLnhanvien.this, "Số điện thoại phải 10 chữ số", Toast.LENGTH_SHORT).show();
                } else {
                    db.executeSQL("UPDATE nhanvien SET tennv='" + ten + "',gioitinh='" + gt + "',namsinh=" + ns + ",diachi='" + dc + "',sdt='" + dt
                            + "' WHERE  manv='" + ma + "'");
                }
                db2ListView();
                adapter.notifyDataSetChanged();
                dialog1.cancel();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xoa(nv.maNV);
                dialog1.cancel();
            }
        });
    }

    public void Xoa(final String Ma) {

        AlertDialog.Builder builder = new AlertDialog.Builder(QLnhanvien.this);
        builder.setTitle("Xóa !");
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrnv.remove(nv);
                adapter.notifyDataSetChanged();
                Toast.makeText(QLnhanvien.this, "Dữ liệu đã bị xóa", Toast.LENGTH_LONG).show();
                db.executeSQL("Delete FROM nhanvien where Manv ='" + Ma + "'");
                db2ListView();
            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(QLnhanvien.this, "Dữ liệu không bị xóa", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();

    }

    public void Timkiem() {
        NhanVien r;
        String tim = tk.getText().toString();
        if (db.GetCount("SELECT * FROM nhanvien WHERE Manv like '%" + tim + "%' or tennv like '%" + tim + "%' or gioitinh like '%" + tim + "%' or namsinh='" + tim +
                "' or sdt like '%" + tim + "%' or diachi like '%" + tim + "%'") == 0) {
            Toast.makeText(this, "Thông tin chưa tồn tại", Toast.LENGTH_LONG).show();
        } else {
            arrnv = new ArrayList<NhanVien>();
            Cursor c = db.getCursor("SELECT * FROM nhanvien WHERE Manv like '%" + tim + "%' or tennv like '%" + tim + "%' or gioitinh like '%" + tim + "%' or namsinh='" + tim +
                    "' or sdt like '%" + tim + "%' or diachi like '%" + tim + "%'");
            c.moveToFirst();
            do {
                r = new NhanVien();
                r.maNV = c.getString(0);
                r.tenNV = c.getString(1);
                r.gioiTinh = c.getString(2);
                r.namSinh = c.getInt(3);
                r.diaChi = c.getString(4);
                r.soDT = c.getString(5);
                arrnv.add(r);
            } while (c.moveToNext());
            adapter = new Adapter_NhanVien(this, R.layout.item_nhanvien, arrnv);
            lv.setAdapter(adapter);
        }
    }
}
