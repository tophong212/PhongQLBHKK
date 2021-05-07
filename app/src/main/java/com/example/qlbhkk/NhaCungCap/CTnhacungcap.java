package com.example.qlbhkk.NhaCungCap;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

public class CTnhacungcap extends AppCompatActivity {
    String mancc;

    DatabaseHandler db = new DatabaseHandler(this);
    /////
    TextView tmancc, ttenncc, tdc, tdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttnhacungcap);
        tmancc = (TextView) findViewById(R.id.htmancc);
        ttenncc = (TextView) findViewById(R.id.httenncc);
        tdc = (TextView) findViewById(R.id.htdiachincc);
        tdt = (TextView) findViewById(R.id.htsdtncc);
        //back tren ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        mancc = bundle.getString("Mancc");
        Cursor cursor = db.getCursor("SELECT * FROM nhacungcap where Mancc ='" + mancc + "'");
        cursor.moveToFirst();
        NhaCungCap s = new NhaCungCap();
        s.setMaNCC(cursor.getString(0));
        s.setTenNCC(cursor.getString(1));
        s.setDiaChi(cursor.getString(2));
        s.setSoDT(cursor.getString(3));

        tmancc.setText("" + s.getMaNCC());
        ttenncc.setText("" + s.getTenNCC());
        tdc.setText("" + s.getDiaChi());
        tdt.setText("" + s.getSoDT());


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
