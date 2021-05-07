package com.example.qlbhkk.Thongke.tkslhang_nhap_xuat;

import android.database.Cursor;
import android.os.Bundle;
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

public class tksl_nhapxuat extends AppCompatActivity {
    EditText nam;
    Button xn;
    TextView sln,slb;
    ListView lv;
    private Adapter_hang adap;
    private ArrayList<hang> arr = null;
    DatabaseHandler db=new DatabaseHandler(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongke_slhang);
        anhxa();
        xn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ht();
            }
        });

    }
    public void anhxa(){
        nam=findViewById(R.id.edtnam);
        xn=findViewById(R.id.btxn);
        sln=findViewById(R.id.tvtongsln);
        slb=findViewById(R.id.tvtongslb);
        lv=findViewById(R.id.lvslh);
    }
    String s;
    public int Soluongban(int n) {
        //ok=Integer.valueOf(nam.getText().toString());
        s=nam.getText().toString();
        int sl=0;
        if(s=="") Toast.makeText(this,"Chưa nhập năm",Toast.LENGTH_SHORT).show();
        else {
            db.copyDB2SDCard();

            Cursor c = db.getCursor("select sum(ifnull(soluong,0)) from cthoadonxuat c inner join hoadonxuat h on h.mahdx=c.Mahdx where ngayban like '%__/" + n + "/" + s + "' or ngayban like '%__/0"+n+"/"+s+"'");
            c.moveToFirst();
            if (c.getCount() == 0) {
            } else {

                    int slg = c.getInt(0);
                    sl=sl+slg;

            }
            c.close();
        }
        return sl;
    }
    public int Soluongnhap(int n) {
        //ok=Integer.valueOf(nam.getText().toString());
        s=nam.getText().toString();
        int t=0;
        if(s=="") Toast.makeText(this,"Chưa nhập năm",Toast.LENGTH_SHORT).show();
        else {
            db.copyDB2SDCard();
            Cursor c = db.getCursor("select sum(ifnull(soluong,0)) from cthoadonnhap c inner join hoadonnhap h on h.mahdn=c.Mahdn where ngaynhap like'%_/" +
                    n + "/" + s + "'");
            c.moveToFirst();
            t = c.getInt(0);
        }
        return t;
    }
    public int tongslnhap() {
        //ok=Integer.valueOf(nam.getText().toString());
        s=nam.getText().toString();
        int t=0;
        if(s=="") Toast.makeText(this,"Chưa nhập năm",Toast.LENGTH_SHORT).show();
        else {
            db.copyDB2SDCard();
            Cursor c = db.getCursor("select sum(ifnull(soluong,0)) from cthoadonnhap c inner join hoadonnhap h on h.mahdn=c.Mahdn where ngaynhap like'%___" +
                    "/" + s + "'");
            c.moveToFirst();
            t = c.getInt(0);
        }
        return t;
    }
    public int tongslban() {
        //ok=Integer.valueOf(nam.getText().toString());
        s=nam.getText().toString();
        int t=0;
        if(s=="") Toast.makeText(this,"Chưa nhập năm",Toast.LENGTH_SHORT).show();
        else {
            db.copyDB2SDCard();
            Cursor c = db.getCursor("select sum(ifnull(c.soluong,0)) from cthoadonxuat c inner join hoadonxuat h on h.mahdx=c.Mahdx where ngayban like'%____" +
                    "/" + s + "'");
            c.moveToFirst();
            t = c.getInt(0);
        }
        return t;
    }
    private void ht() {
        arr=new ArrayList<hang>();
        for (int i = 1; i <= 12; i++) {
            arr.add(new hang(i, Soluongnhap(i), Soluongban(i)));
            adap= new Adapter_hang(this, R.layout.item_slnhapxuat, arr);
            lv.setAdapter(adap);

        }
        slb.setText(String.valueOf(tongslban()));
        sln.setText(String.valueOf(tongslnhap()));
    }
}
