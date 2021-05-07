package com.example.qlbhkk.Thongke.tkncc;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

import java.util.ArrayList;

public class tkncc extends AppCompatActivity {
    ListView lv;
    TextView tvma, tvten, tvsoluong;
    //String tg, thang, nam;
    DatabaseHandler db = new DatabaseHandler(this);
    private Adapter_ncc adap;
    private ArrayList<ncc> arr = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongkencc);
        anhxa();
        db2ListView();
    }
    public void anhxa() {
        tvma = findViewById(R.id.tvmaspbc);
        tvten = findViewById(R.id.tvtenspbc);
        tvsoluong = findViewById(R.id.tvslb);
        lv=findViewById(R.id.lvspbc);
    }

    public void db2ListView() {
        arr = new ArrayList<ncc>();
        ncc row;
        Cursor cursor = db.getCursor("select  cc.Mancc,tenncc,sum(c.soluong) as [số lượng bán] from nhacungcap cc inner join hoadonnhap h on h.Mancc=cc.Mancc inner join cthoadonnhap c on c.Mahdn=h.Mahdn \n" +
                " group by cc.Mancc,tenncc\n" +
                "order by sum(c.soluong) desc\n" +
                "LIMIT 5");
        cursor.moveToFirst();
        if(cursor.getCount()==0){}
        else {
            while (!cursor.isAfterLast()) {
                String ma = cursor.getString(0);
                String ten = cursor.getString(1);
                int sl = cursor.getInt(2);

                row = new ncc(ma, ten, sl);
                arr.add(row);
                cursor.moveToNext();
                adap = new Adapter_ncc(this, R.layout.item_spbc, arr);
                lv.setAdapter(adap);
                adap.notifyDataSetChanged();
            }
        }
        cursor.close();
    }
}
