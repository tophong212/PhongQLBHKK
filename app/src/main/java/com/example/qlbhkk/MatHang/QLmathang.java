package com.example.qlbhkk.MatHang;

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

public class QLmathang extends AppCompatActivity {
    // biến toàn cục
    ArrayList<MatHang> arrmh = null;
    Adapter_Mathang adapter = null;
    MatHang mh = new MatHang();
    ListView lv;
    ImageButton them, trangchu, timkiem;
    EditText tk;
    int ps;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlmathang);
        db.copyDB2SDCard();
        //anh xa
        anhxa();
        ////////////////////////////////
        db2ListView();

        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Timkiem();
            }
        });

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(QLmathang.this, Themmh.class);
                startActivity(in);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent(QLmathang.this, CTmathang.class);
                intent.putExtra("Mamh", arrmh.get(i).getMaMH().toString());
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
                Intent intent = new Intent(QLmathang.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhxa() {
        lv = findViewById(R.id.lv_MH);
        them = (ImageButton) findViewById(R.id.imbt_ThemMh);
        trangchu = (ImageButton) findViewById(R.id.imbt_BackMH);
        timkiem = (ImageButton) findViewById(R.id.imbt_TimMH);
        tk = (EditText) findViewById(R.id.edt_timMH);
    }

    //Phương thức: Load dữ liệu lên ListView
    public void db2ListView() {
        arrmh = new ArrayList<MatHang>();
        MatHang row;
        Cursor cursor = db.getCursor("SELECT * FROM mathang");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new MatHang();
            row.maMH = cursor.getString(0);
            row.tenMH = cursor.getString(1);
            row.moTa = cursor.getString(2);

            arrmh.add(row);
            cursor.moveToNext();
        }
        adapter = new Adapter_Mathang(this, R.layout.item_mh, arrmh);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    Dialog dialog1;

    public void sua_xoa() {
        dialog1 = new Dialog(QLmathang.this);
        dialog1.setContentView(R.layout.sua_xoamh);
        dialog1.show();
        final MatHang mh = arrmh.get(ps);

        final EditText tenmh, mota;
        final TextView mamh;
        final Button btnSua, btnXoa;
        mamh = (TextView) dialog1.findViewById(R.id.tvmamh);
        tenmh = (EditText) dialog1.findViewById(R.id.edttenmh);
        mota = (EditText) dialog1.findViewById(R.id.edtmota);

        btnSua = (Button) dialog1.findViewById(R.id.bt_suamh);
        btnXoa = (Button) dialog1.findViewById(R.id.bt_xoamh);
        tenmh.setText(mh.getTenMH());
        mota.setText(mh.getMoTa());
        mamh.setText(mh.getMaMH());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = mamh.getText().toString();
                String ten = tenmh.getText().toString();
                String mt = mota.getText().toString();
                db.executeSQL("UPDATE mathang SET tenmh='" + ten + "',mota='" + mt + "' WHERE  mamh='" + ma + "'");
                db2ListView();
                adapter.notifyDataSetChanged();
                dialog1.cancel();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xoa(mh.maMH);
                dialog1.cancel();
            }
        });
    }

    public void Xoa(final String Ma) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QLmathang.this);
        builder.setTitle("Xóa !");
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrmh.remove(mh);
                adapter.notifyDataSetChanged();
                Toast.makeText(QLmathang.this, "Dữ liệu đã bị xóa", Toast.LENGTH_LONG).show();
                db.executeSQL("Delete FROM mathang where Mamh ='" + Ma + "'");
                db2ListView();

            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(QLmathang.this, "Dữ liệu không bị xóa", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    public void Timkiem() {
        MatHang matHang;
        String tim = tk.getText().toString();
        if (db.GetCount("SELECT * FROM mathang WHERE Mamh like '%" + tim + "%' or Tenmh like '%" + tim +
                "%' or mota like '%" + tim + "%'") == 0) {
            Toast.makeText(this, "Thông tin chưa tồn tại", Toast.LENGTH_LONG).show();
        } else {
            arrmh = new ArrayList<MatHang>();
            Cursor cursor = db.getCursor("SELECT * FROM mathang WHERE Mamh like '%" + tim + "%' or tenmh like '%" + tim +
                    "%' or mota like '%" + tim + "%'");
            cursor.moveToFirst();
            do {
                matHang = new MatHang();
                matHang.maMH = cursor.getString(0);
                matHang.tenMH = cursor.getString(1);
                matHang.moTa = cursor.getString(2);
                arrmh.add(matHang);
            } while (cursor.moveToNext());
            adapter = new Adapter_Mathang(this, R.layout.item_mh, arrmh);
            lv.setAdapter(adapter);
        }
    }
}
