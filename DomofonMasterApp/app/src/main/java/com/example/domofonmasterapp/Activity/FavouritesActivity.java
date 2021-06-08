package com.example.domofonmasterapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.domofonmasterapp.POJO.KeyEntity;
import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.DBHelper;
import com.example.domofonmasterapp.Service.StringUtils;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    private final ArrayList<String> keysCode = new ArrayList<>();
    ListView keysList;
    ArrayAdapter<String> adapter;
    private ArrayList<KeyEntity> keys = new ArrayList<>();
    //String type;
    Button btnClear;

    protected void ViewList() {
        for (KeyEntity e : keys
        ) {
            String code = Long.toHexString(e.getCode());
            keysCode.add(StringUtils.removeFirstZero(code));
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, keysCode);
        keysList.setAdapter(adapter);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        TextView tv = findViewById(R.id.textKey);
        keysList = findViewById(R.id.keysList);
        btnClear = findViewById(R.id.btnClear);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_KEYS_FAV, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int code = cursor.getColumnIndex(DBHelper.KEY_CODE);
            int type = cursor.getColumnIndex(DBHelper.KEY_TYPE);
            do {
                KeyEntity e = new KeyEntity();
                e.setCode(cursor.getLong(code));
                //e.setType(cursor.getInt(type) == 1);
                keys.add(e);
            } while (cursor.moveToNext());
        }
        else tv.setText("Пусто");
        cursor.close();

        ViewList();

        keysList.setOnItemClickListener((parent, v, position, id) -> {
            String key = adapter.getItem(position);
            //String type;
            //long code = Long.parseLong(key.replaceAll("[^0,1]", ""));
            long code = Long.parseLong(key, 16);
            //type = key.replaceAll("[0,1]|\\s","");
            //boolean typeB = type.equals("iButton");

            for (KeyEntity e : keys) {
                if (e.getCode() == code) {
                    Intent i = new Intent(FavouritesActivity.this, DetailKeyActivity.class);
                    i.putExtra("key", e);
                    i.putExtra("favor", true);
                    startActivity(i);
                    break;
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().deleteDatabase(DBHelper.DATABASE_NAME);
                String message = "Очищено";
                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
