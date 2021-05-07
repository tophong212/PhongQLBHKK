package com.example.qlbhkk.NhaCungCap;

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

public class QLnhacungcap extends AppCompatActivity {
    ArrayList<NhaCungCap> arrncc = null;
    Adapter_NCC adapter = null;
    NhaCungCap ncc = new NhaCungCap();
    ListView lv;
    ImageButton them, trangchu, timkiem;
    int ps;
    DatabaseHandler db = new DatabaseHandler(this);
    EditText tk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlncc);
        db.copyDB2SDCard();
        //anh xa
        lv = findViewById(R.id.lv_NCC);
        them = (ImageButton) findViewById(R.id.imbt_ThemNCC);
        trangchu = (ImageButton) findViewById(R.id.imbt_BackNCC);
        timkiem = (ImageButton) findViewById(R.id.imbt_TimNCC);
        tk = findViewById(R.id.edt_timNCC);
        ////////////////////////////////

        db2ListView();
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(QLnhacungcap.this, Themncc.class);
                startActivity(in);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent(QLnhacungcap.this, CTnhacungcap.class);
                intent.putExtra("Mancc", arrncc.get(i).getMaNCC().toString());
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
                Intent intent = new Intent(QLnhacungcap.this, MainActivity.class);
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

    public void db2ListView() {
        arrncc = new ArrayList<NhaCungCap>();
        NhaCungCap row;
        Cursor cursor = db.getCursor("SELECT * FROM nhacungcap");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new NhaCungCap();
            row.maNCC = cursor.getString(0);
            row.tenNCC = cursor.getString(1);
            row.diaChi = cursor.getString(2);
            row.soDT = cursor.getString(3);
            arrncc.add(row);
            cursor.moveToNext();
        }
        adapter = new Adapter_NCC(this, R.layout.item_ncc, arrncc);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    Dialog dialog1;

    public void sua_xoa() {
        dialog1 = new Dialog(QLnhacungcap.this);
        dialog1.setContentView(R.layout.sua_xoancc);
        dialog1.show();
        final NhaCungCap ncc = arrncc.get(ps);
        final TextView mancc;
        final EditText tenncc, diachi, sdt;
        final Button btnSua, btnXoa;
        mancc = dialog1.findViewById(R.id.tvmancc);
        tenncc = dialog1.findViewById(R.id.edttenncc);
        diachi = dialog1.findViewById(R.id.edtdcncc);
        sdt = dialog1.findViewById(R.id.edtsdtncc);

        btnSua = (Button) dialog1.findViewById(R.id.bt_suancc);
        btnXoa = (Button) dialog1.findViewById(R.id.bt_xoancc);
        tenncc.setText(ncc.getTenNCC());
        mancc.setText(ncc.getMaNCC());
        diachi.setText(ncc.getDiaChi());
        sdt.setText(ncc.getSoDT());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = mancc.getText().toString();
                String ten = tenncc.getText().toString();
                String dc = diachi.getText().toString();
                String dt = sdt.getText().toString();
                db.executeSQL("UPDATE nhacungcap SET tenncc='" + ten + "',diachi='" + dc + "',sdt='" + dt
                        + "' WHERE  mancc='" + ma + "'");
                db2ListView();
                adapter.notifyDataSetChanged();
                dialog1.cancel();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Xoa(ncc.maNCC);
                dialog1.cancel();
            }
        });
    }

    public void Xoa(final String Ma) {

        AlertDialog.Builder builder = new AlertDialog.Builder(QLnhacungcap.this);
        builder.setTitle("Xóa !");
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrncc.remove(ncc);
                adapter.notifyDataSetChanged();
                Toast.makeText(QLnhacungcap.this, "Dữ liệu đã bị xóa", Toast.LENGTH_LONG).show();
                db.executeSQL("Delete FROM nhacungcap where Mancc ='" + Ma + "'");
                db2ListView();

            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(QLnhacungcap.this, "Dữ liệu không bị xóa", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    public void Timkiem() {
        NhaCungCap r;
        String tim = tk.getText().toString();
        if (db.GetCount("SELECT * FROM nhacungcap WHERE Mancc like '%" + tim + "%' or Tenncc like '%" + tim +
                "%'or sdt like '%" + tim + "%' or diachi like '%" + tim + "%'") == 0) {
            Toast.makeText(this, "Thông tin chưa tồn tại", Toast.LENGTH_LONG).show();
        } else {
            arrncc = new ArrayList<NhaCungCap>();
            Cursor c = db.getCursor("SELECT * FROM nhacungcap WHERE Mancc like '%" + tim + "%' or tenncc like '%" + tim +
                    "%'or sdt like '%" + tim + "%' or diachi like '%" + tim + "%'");
            c.moveToFirst();
            do {
                r = new NhaCungCap();
                r.maNCC = c.getString(0);
                r.tenNCC = c.getString(1);
                r.diaChi = c.getString(2);
                r.soDT = c.getString(3);
                arrncc.add(r);
            } while (c.moveToNext());
            adapter = new Adapter_NCC(this, R.layout.item_ncc, arrncc);
            lv.setAdapter(adapter);
        }
    }
}
