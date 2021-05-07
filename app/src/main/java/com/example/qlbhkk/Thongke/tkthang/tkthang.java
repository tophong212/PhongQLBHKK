package com.example.qlbhkk.Thongke.tkthang;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

import java.util.ArrayList;

public class tkthang extends AppCompatActivity {
    EditText nam;
    ListView lv;
    TextView thang, cp, ln, dt, tongcp, tongdt, tongln;
    DatabaseHandler db = new DatabaseHandler(this);
    private Adapter_dt adap;
    int ok;
    String kk;
    private ArrayList<dt> arr = null;
    Button xn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dtthang);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Anhxa();
        xn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ht();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Anhxa() {
        nam = (EditText) findViewById(R.id.edtnam);
        lv = (ListView) findViewById(R.id.lvdtthang);
        thang = (TextView) findViewById(R.id.tvThang);
        ln = (TextView) findViewById(R.id.tvLoiNhuan);
        cp = (TextView) findViewById(R.id.tvChiPhi);
        dt = (TextView) findViewById(R.id.tvDoanhThu);
        xn = (Button) findViewById(R.id.btxn);
        tongcp = (TextView) findViewById(R.id.tvtongcp);
        tongdt = (TextView) findViewById(R.id.tvtongdt);
        tongln = (TextView) findViewById(R.id.tvtongln);
    }

    public int DoanhThu(int n) {
        //ok=Integer.valueOf(nam.getText().toString());
        kk = nam.getText().toString();
        int gia, Thu = 0;
        if (kk == "") Toast.makeText(this, "Chưa nhập năm", Toast.LENGTH_SHORT).show();
        else {
            db.copyDB2SDCard();

            Cursor c = db.getCursor("select ifnull(soluong,0),ifnull(masp,0) from cthoadonxuat c inner join hoadonxuat h on h.mahdx=c.Mahdx where ngayban like '%_/" + n + "/" + kk + "' or ngayban like '%__/0" + n + "/n" + kk + "'");
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
            c.close();
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
    public int ChiPhi(int n) {
        //ok=Integer.valueOf(nam.getText().toString());
        kk = nam.getText().toString();
        int t = 0;
        if (kk == "") Toast.makeText(this, "Chưa nhập năm", Toast.LENGTH_SHORT).show();
        else {
            db.copyDB2SDCard();
            Cursor c = db.getCursor("select ifnull(sum(soluong*gianhap),0) from cthoadonnhap c inner join hoadonnhap h on h.mahdn=c.Mahdn where ngaynhap like'%_/" +
                    n + "/" + kk + "' or ngaynhap like '%__/0" + n + "/" + kk + "'");
            c.moveToFirst();
            t = c.getInt(0);
        }
        return t;
    }

    public int ChiPhinam() {
        //ok=Integer.valueOf(nam.getText().toString());
        kk = nam.getText().toString();
        int t = 0;
        if (kk == "") Toast.makeText(this, "Chưa nhập năm", Toast.LENGTH_SHORT).show();
        else {
            db.copyDB2SDCard();
            Cursor c = db.getCursor("select ifnull(sum(soluong*gianhap),0) from chitiethdn c inner join hoadonnhap h on h.mahdn=c.Mahdn where ngaynhap like'%____" +
                    "/" + kk + "'");
            c.moveToFirst();
            t = c.getInt(0);
        }
        return t;
    }

    public int dtnam() {
        //ok=Integer.valueOf(nam.getText().toString());
        kk = nam.getText().toString();
        int t = 0;
        if (kk == "") Toast.makeText(this, "Chưa nhập năm", Toast.LENGTH_SHORT).show();
        else {
            db.copyDB2SDCard();
            Cursor c = db.getCursor("select ifnull(sum(c.soluong*giaban),0) from cthoadonxuat c inner join hoadonxuat h on h.mahdx=c.Mahdx inner join sanpham s on s.masp=c.masp where ngayban like'%____" +
                    "/" + kk + "'");
            c.moveToFirst();
            t = c.getInt(0);
        }
        return t;
    }

    private void ht() {
        arr = new ArrayList<dt>();
        //int dt=0,cp=0,ln=0;
        for (int i = 1; i <= 12; i++) {

            arr.add(new dt(i, ChiPhi(i), DoanhThu(i), DoanhThu(i) - ChiPhi(i)));
            adap = new Adapter_dt(this, R.layout.item_dtthang, arr);
            lv.setAdapter(adap);
        }
        tongln.setText(String.valueOf(dtnam() - ChiPhinam()));
        tongdt.setText(String.valueOf(dtnam()));
        tongcp.setText(String.valueOf(ChiPhinam()));
    }
}
