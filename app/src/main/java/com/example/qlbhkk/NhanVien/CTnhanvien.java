package com.example.qlbhkk.NhanVien;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

public class CTnhanvien extends AppCompatActivity {
    String manv;
   // ArrayList<NhanVien> arrsp = new ArrayList<>();
    //ArrayList<String> cayItems = new ArrayList<String>();
    DatabaseHandler db = new DatabaseHandler(this);
    /////
    TextView tmanv, ttennv, tgt, tns, tdc, tdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttnhanvien);
        tmanv = (TextView) findViewById(R.id.htmanv);
        ttennv = (TextView) findViewById(R.id.httennv);
        tgt = (TextView) findViewById(R.id.htgioitinh);
        tdc = (TextView) findViewById(R.id.htdiachinv);
        tns=(TextView) findViewById(R.id.htnamsinhnv);
        tdt=(TextView) findViewById(R.id.htsdtnv);
        //back tren ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        manv = bundle.getString("Manv");
        Cursor cursor = db.getCursor("SELECT * FROM nhanvien where Manv ='" + manv + "'");

        cursor.moveToFirst();
            NhanVien s = new NhanVien();
            s.setMaNV(cursor.getString(0));
            s.setTenNV(cursor.getString(1));
            s.setGioiTinh(cursor.getString(2));
            s.setNamSinh(cursor.getInt(3));
            s.setDiaChi(cursor.getString(4));
            s.setSoDT(cursor.getString(5));

            tmanv.setText("" + s.getMaNV());
            ttennv.setText(""+s.getTenNV());
            tgt.setText("" + s.getGioiTinh());
            tns.setText("" + s.getNamSinh());
            tdc.setText(""+s.getDiaChi());
            tdt.setText(""+s.getSoDT());


    }
    ////////////////////////////////////////////////////////////////////////////////////////
    //back tren ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
