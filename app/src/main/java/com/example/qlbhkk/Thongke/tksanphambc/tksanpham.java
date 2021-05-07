package com.example.qlbhkk.Thongke.tksanphambc;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class tksanpham extends AppCompatActivity {
    ListView lv;
    TextView tvma, tvten, tvsoluong;
    String tg, thang, nam;
    DatabaseHandler db = new DatabaseHandler(this);
    private Adapter_spbc adap;
    private ArrayList<spbc> arr = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongke_spbc);
        TG();
        anhxa();
        db2ListView(thang,nam);
        //ht();
    }

    public void TG() {
        // Lấy ngày hiện tại
        DateFormat dateFormatter1 = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        tg = dateFormatter1.format(today);
        String[] a = tg.split("/");
        thang = a[1];
        nam = a[2];
    }

    public void anhxa() {
        tvma = findViewById(R.id.tvmaspbc);
        tvten = findViewById(R.id.tvtenspbc);
        tvsoluong = findViewById(R.id.tvslb);
        lv=findViewById(R.id.lvspbc);
    }

    public void db2ListView(String t, String n) {
        arr = new ArrayList<>();
        db.copyDB2SDCard();
        spbc row;
        Cursor cursor = db.getCursor("select  ifnull(c.masp,0),tensp,sum(c.Soluong) as [số lượng bán] from sanpham s inner join cthoadonxuat c on c.masp=s.masp INNER JOIN hoadonxuat h on h.mahdx=c.mahdx\n" +
                "WHERE ngayban like '%__/" + t + "/" + n +"' group by c.masp,tensp\n" +
                "order by sum(c.soluong) desc\n" +
                "LIMIT 5");
        cursor.moveToFirst();
        if(cursor.getCount()==0){
            Toast.makeText(this,"Chưa có sp nào",Toast.LENGTH_SHORT).show();}
        else {
            while (!cursor.isAfterLast()) {
                String ma = cursor.getString(0);
                String ten = cursor.getString(1);
                int sl = cursor.getInt(2);

                row = new spbc(ma, ten, sl);
                arr.add(row);
                cursor.moveToNext();
                adap = new Adapter_spbc(this, R.layout.item_spbc, arr);
                lv.setAdapter(adap);
                adap.notifyDataSetChanged();
            }
        }
   }
}
