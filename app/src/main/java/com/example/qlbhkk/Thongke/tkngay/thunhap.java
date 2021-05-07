package com.example.qlbhkk.Thongke.tkngay;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

public class thunhap extends AppCompatActivity {
    EditText nbd;
    TextView dt, cp, ln;
    Button xn;
    String ngay;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongke);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Anhxa();
        xn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Set() {
        dt.setText(String.valueOf(DoanhThu()));
        ln.setText(String.valueOf(DoanhThu() - ChiPhi()));
        cp.setText(String.valueOf(ChiPhi()));

    }

    public void Anhxa() {
        ln = (TextView) findViewById(R.id.tvLoiNhuan);
        cp = (TextView) findViewById(R.id.tvChiPhi);
        dt = (TextView) findViewById(R.id.tvDoanhThu);
        nbd = (EditText) findViewById(R.id.edtngay);
        xn = (Button) findViewById(R.id.btxacnhan);
    }

    public int DoanhThu() {
        ngay = nbd.getText().toString();
        int gia = 1, Thu = 0;
        if (ngay == "") Toast.makeText(this, "Bạn chưa nhập ngày", Toast.LENGTH_SHORT).show();
        else {

            db.copyDB2SDCard();
            Cursor c = db.getCursor("select ifnull(soluong,0),ifnull(masp,0) from cthoadonxuat c inner join hoadonxuat h on h.Mahdx=c.Mahdx where ngayban like '" + ngay + "'");
            c.moveToFirst();
            if (c.getCount() == 0) {
            } else {
                do {
                    int slg = c.getInt(0);
                    String maqa = c.getString(1);
                    gia = Getgia(maqa);
                    Thu = (slg * gia) + Thu;
                } while (c.moveToNext());
            }
        }
        return Thu;
    }

    // Lấy giá từ QA với mã sẵn
    public int Getgia(String ma) {
        db.copyDB2SDCard();
        Cursor c = db.getCursor("SELECT giaban FROM sanpham where Masp='" + ma + "' ");
        c.moveToFirst();
        int GiaQA = c.getInt(0);
        return GiaQA;
    }

    // Tính Chi phí
    public int ChiPhi() {
        ngay = nbd.getText().toString();
        int Chi = 0;
        if (ngay == "") Toast.makeText(this, "Bạn chưa nhập ngày", Toast.LENGTH_SHORT).show();
        else {
            db.copyDB2SDCard();

            Cursor c = db.getCursor("select ifnull(sum( soluong*gianhap ),0) from cthoadonnhap c inner join hoadonnhap h on h.mahdn=c.Mahdn where ngaynhap like'" +
                    ngay + "'");
            c.moveToFirst();
            do {
                int t = c.getInt(0);
                Chi = t + Chi;
            } while (c.moveToNext());
        }
        return Chi;
    }
}
