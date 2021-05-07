package com.example.qlbhkk.SanPham;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

public class CTsanpham extends AppCompatActivity {
    String masp;
    DatabaseHandler db = new DatabaseHandler(this);
    TextView tmasp, ttensp, tmamh, tnhanhieu, tkichco, tsoluong, tgiaban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttsanpham);

        tmasp = (TextView) findViewById(R.id.ttmsp);
        tmamh = (TextView) findViewById(R.id.ttmmh);
        ttensp = (TextView) findViewById(R.id.ttsp);
        tkichco = (TextView) findViewById(R.id.ttsize);
        tnhanhieu = (TextView) findViewById(R.id.ttnhanhieu);
        tsoluong = (TextView) findViewById(R.id.ttsoluong);
        tgiaban = (TextView) findViewById(R.id.ttgiaban);

        //back tren ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        masp = bundle.getString("Masp");
        Cursor cursor = db.getCursor("SELECT * FROM SanPham where Masp ='" + masp + "'");

        cursor.moveToFirst();
        SanPham s = new SanPham();
        s.setMaSP(cursor.getString(0));
        s.setMaMH(cursor.getString(1));
        s.setTenSP(cursor.getString(2));
        s.setNhanHieu(cursor.getString(3));
        s.setKichCo(cursor.getString(4));
        s.setSoluong(cursor.getInt(5));
        s.setGiaBan(cursor.getInt(6));

        tmasp.setText("" + s.getMaSP());
        tmamh.setText("" + s.getMaMH());
        ttensp.setText("" + s.getTenSP());
        tnhanhieu.setText("" + s.getNhanHieu());
        tkichco.setText("" + s.getKichCo());
        tsoluong.setText("" + s.getSoluong());
        tgiaban.setText("" + s.getGiaBan());
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
