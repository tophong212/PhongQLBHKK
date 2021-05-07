package com.example.qlbhkk.MatHang;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbhkk.DatabaseHandler;
import com.example.qlbhkk.R;

import java.util.ArrayList;

public class CTmathang extends AppCompatActivity {
    String mamh;
    ArrayList<MatHang> arrmh = new ArrayList<>();
    //ArrayList<String> cayItems = new ArrayList<String>();
    DatabaseHandler db = new DatabaseHandler( this );
   /////
    TextView tma, tten, tmota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.ttmathang );

        tma = (TextView) findViewById( R.id.htmamh );
        tten = (TextView) findViewById( R.id.httenmh );
        tmota = (TextView) findViewById( R.id.htmota );

        //////////////////////


        //back tren ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }

        Bundle bundle = getIntent().getExtras();
        mamh = bundle.getString( "Mamh" );
        Cursor cursor = db.getCursor( "SELECT * FROM mathang where Mamh ='" + mamh + "'" );

        cursor.moveToFirst();
        MatHang c = new MatHang();

        c.setMaMH( cursor.getString( 0 ) );
        c.setTenMH( cursor.getString( 1 ) );
        c.setMoTa( cursor.getString( 2 ) );


        tma.setText( "" + c.getMaMH() );
        tten.setText( "" + c.getTenMH() );
        tmota.setText( "" + c.getMoTa() );


    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //back tren ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );
    }
}
