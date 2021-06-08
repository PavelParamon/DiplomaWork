package com.example.domofonmasterapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.domofonmasterapp.POJO.KeyEntity;
import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.ArduinoServ;
import com.example.domofonmasterapp.Service.DBHelper;
import com.example.domofonmasterapp.Service.StringUtils;

public class DetailKeyActivity extends AppCompatActivity {

    Button btnReadKey, btnAddFav;
    KeyEntity KE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean favor = getIntent().getBooleanExtra("favor", false);
        if(!favor){
            setContentView(R.layout.activity_detail_key);
            btnAddFav = findViewById(R.id.btnAddFav);
        }
        else setContentView(R.layout.activity_detail_key_from_favorites);
        btnReadKey = findViewById(R.id.btnReadKey);


        DBHelper dbHelper = new DBHelper(this);

        KE = (KeyEntity) getIntent().getSerializableExtra("key");

        btnReadKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArduinoServ.clearReadMsg();
                if(ArduinoServ.getReadMsg().isEmpty()){
                    String code = StringUtils.removeFirstZero(Long.toHexString(KE.getCode())).substring(1);
                    code = code.substring(0,code.length() - 2);
                    ArduinoServ.getSerialPort().write(code.getBytes());
                }
                String message = "Передача Arduino кода " + StringUtils.removeFirstZero(Long.toHexString(KE.getCode()));
                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(DetailKeyActivity.this, WriteKeyActivity.class);
                i.putExtra("key", KE);
                i.putExtra("favor", favor);
                startActivity(i);
            }
        });

        if(!favor){
        btnAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_CODE, KE.getCode());
                //contentValues.put(DBHelper.KEY_TYPE, KE.isType() ? 1 : 0);
                contentValues.put(DBHelper.KEY_DATA_START, KE.getData_start());
                contentValues.put(DBHelper.KEY_DATA_END, KE.getData_end());
                contentValues.put(DBHelper.KEY_PORCH, KE.getPorch_id());

                database.insert(DBHelper.TABLE_KEYS_FAV, null, contentValues);


                String message = "Ключ добавлен в избранное";
                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();

            }
        });
        }


    }
}
