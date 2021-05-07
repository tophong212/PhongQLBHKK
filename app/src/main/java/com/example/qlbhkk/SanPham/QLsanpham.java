package com.example.qlbhkk.SanPham;

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

public class QLsanpham extends AppCompatActivity {
    ArrayList<SanPham> arrsp = null;
    Adapter_SanPham adapter = null;
    SanPham sanPham = new SanPham();
    ListView lv;
    ImageButton them, trangchu, timkiem;
    int ps;
    DatabaseHandler db = new DatabaseHandler(this);
    EditText tk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlsanpham);
        db.copyDB2SDCard();
        //anh xa
        anhxa();
        ///////////////////////////////
        db2ListView();

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(QLsanpham.this, Themsp.class);
                startActivity(in);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent(QLsanpham.this, CTsanpham.class);
                intent.putExtra("Masp", arrsp.get(i).getMaSP());
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
                Intent intent = new Intent(QLsanpham.this, MainActivity.class);
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
        lv = findViewById(R.id.lv_SP);
        them = (ImageButton) findViewById(R.id.imbt_ThemSP);
        trangchu = (ImageButton) findViewById(R.id.imbt_BackSP);
        timkiem = (ImageButton) findViewById(R.id.imbt_TimSP);
        tk = findViewById(R.id.edt_timSP);
    }

    public void db2ListView() {
        arrsp = new ArrayList<SanPham>();
        SanPham row;
        Cursor cursor = db.getCursor("SELECT * FROM sanpham");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new SanPham();
            row.maSP = cursor.getString(0);
            row.maMH = cursor.getString(1);
            row.tenSP = cursor.getString(2);
            row.nhanHieu = cursor.getString(3);
            row.kichCo = cursor.getString(4);
            row.soluong = cursor.getInt(5);
            row.giaBan = cursor.getInt(6);
            arrsp.add(row);
            cursor.moveToNext();
        }
        adapter = new Adapter_SanPham(this, R.layout.item_sanpham, arrsp);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    Dialog dialog1;

    public void sua_xoa() {
        dialog1 = new Dialog(QLsanpham.this);
        dialog1.setContentView(R.layout.sua_xoasp);
        dialog1.show();
        final SanPham sp = arrsp.get(ps);
        final TextView masp;
        final EditText mamh, tensp, nhanhieu, kichco, chatlieu, soluong, gia;
        final Button btnSua, btnXoa;
        masp = (TextView) dialog1.findViewById(R.id.tvmasp);
        mamh = (EditText) dialog1.findViewById(R.id.edt_mamh_sp);
        tensp = (EditText) dialog1.findViewById(R.id.edt_tensp);
        nhanhieu = (EditText) dialog1.findViewById(R.id.edt_nhanhieu);
        kichco = (EditText) dialog1.findViewById(R.id.edt_size);
        soluong = (EditText) dialog1.findViewById(R.id.edt_soluong);
        gia = (EditText) dialog1.findViewById(R.id.edt_giaban);
        ///////
        btnSua = (Button) dialog1.findViewById(R.id.bt_suasp);
        btnXoa = (Button) dialog1.findViewById(R.id.bt_xoasp);
        ///////
        String msp = sp.getMaSP();
        String mmh = sp.getMaMH();
        String t = sp.getTenSP();
        String n = sp.getNhanHieu();
        String k = sp.getKichCo();
        Integer s = sp.getSoluong();
        Integer g = sp.getGiaBan();
        //////
        tensp.setText(t);
        mamh.setText(mmh);
        masp.setText(msp);
        nhanhieu.setText(n);
        kichco.setText(k);
        soluong.setText(s.toString());
        gia.setText(g.toString());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mas = masp.getText().toString();
                String ma = mamh.getText().toString();
                String nh = nhanhieu.getText().toString();
                String ten = tensp.getText().toString();
                String kc = kichco.getText().toString();
                Integer sl = Integer.valueOf(soluong.getText().toString());
                Integer gb = Integer.valueOf(gia.getText().toString());
                db.executeSQL("UPDATE sanpham SET tensp='" + ten + "',mamh='" + ma + "',nhanhieu='" + nh + "',kichco='" + kc
                        + "',soluong=" + sl + ",giaban=" + gb + " WHERE  masp='" + mas + "'");
                db2ListView();
                adapter.notifyDataSetChanged();
                dialog1.cancel();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Xoa(sp.maSP);
                dialog1.cancel();
            }
        });
    }

    public void Xoa(final String Ma) {

        AlertDialog.Builder builder = new AlertDialog.Builder(QLsanpham.this);
        builder.setTitle("Xóa !");
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrsp.remove(sanPham);
                adapter.notifyDataSetChanged();
                Toast.makeText(QLsanpham.this, "Dữ liệu đã bị xóa", Toast.LENGTH_LONG).show();
                db.executeSQL("Delete FROM sanpham where Masp ='" + Ma + "'");
                db2ListView();
            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(QLsanpham.this, "Dữ liệu không bị xóa", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    public void Timkiem() {
        SanPham r;
        String tim = tk.getText().toString();
        if (db.GetCount("SELECT * FROM sanpham WHERE Masp like '%" + tim + "%' or Mamh like '%" + tim + "%' or soluong='" + tim + "' or giaban='" + tim +
                "' or tensp like '%" + tim + "%' or kichco like '%" + tim + "%'") == 0) {
            Toast.makeText(this, "Thông tin chưa tồn tại", Toast.LENGTH_LONG).show();
        } else {
            arrsp = new ArrayList<SanPham>();
            Cursor c = db.getCursor("SELECT * FROM sanpham WHERE Masp like '%" + tim + "%' or Mamh like '%" + tim +
                    "%'or tensp like '%" + tim + "%' or soluong='" + tim + "' or giaban='" + tim + "%' or kichco like '%" + tim + "%'");
            c.moveToFirst();
            do {
                r = new SanPham();
                r.maSP = c.getString(0);
                r.maMH = c.getString(1);
                r.tenSP = c.getString(2);
                r.nhanHieu = c.getString(3);
                r.kichCo = c.getString(4);
                r.soluong = c.getInt(5);
                r.giaBan = c.getInt(6);
                arrsp.add(r);
            } while (c.moveToNext());
            adapter = new Adapter_SanPham(this, R.layout.item_sanpham, arrsp);
            lv.setAdapter(adapter);
        }
    }
}
