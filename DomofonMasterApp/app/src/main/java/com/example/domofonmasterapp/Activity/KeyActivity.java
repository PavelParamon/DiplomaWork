package com.example.domofonmasterapp.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.domofonmasterapp.POJO.KeyEntity;
import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.ArduinoServ;
import com.example.domofonmasterapp.Service.RetrofitServ;
import com.example.domofonmasterapp.Service.StringUtils;
import com.google.gson.internal.LinkedTreeMap;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeyActivity extends AppCompatActivity {
    private final ArrayList<String> keysCode = new ArrayList<>();
    ListView keysList;
    ArrayAdapter<String> adapter;
    private RetrofitServ RS = new RetrofitServ();
    private ArrayList<KeyEntity> keys = new ArrayList<>();
    private TextView tv;
    private Button btnAdd;
    final Handler h = new Handler();
    String type;

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
        setContentView(R.layout.activity_key);
        keysList = findViewById(R.id.keysList);
        tv = findViewById(R.id.textKey);
        btnAdd = findViewById(R.id.btnAddKey);
        Call<Object> call;
        long id_porch = getIntent().getLongExtra("id_porch", -1);
        long id_intercom = getIntent().getLongExtra("id_intercom", -1);
        if(id_porch != -1)
            call = RS.req.GetKeysViaPorch(id_porch);
        else
            call = RS.req.GetKeysViaIntercom(id_intercom);

        synchronized (keysCode) {
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (!response.body().toString().equals("[]")) {
                        ArrayList<LinkedTreeMap> keysEntityMap = RS.gson.fromJson(response.body().toString(), ArrayList.class);
                        for (Map e : keysEntityMap) {
                            KeyEntity key = new KeyEntity();
                            key.setId_key((long) (double) e.get("id_key"));
                            key.setCode(Long.parseLong((String) Objects.requireNonNull(((LinkedTreeMap) ((ArrayList) response.body()).get(keysEntityMap.indexOf(e))).get("code"))));
                            key.setData_start(Objects.requireNonNull(e.get("data_start")).toString());
                            if (e.containsKey("data_end"))
                                key.setData_end(Objects.requireNonNull(e.get("data_end")).toString());
                            else key.setData_end("");
                            //key.setType((boolean) e.get("type"));
                            key.setPorch_id((long) (double) e.get("porch_id"));
                            key.setIntercom_id((long) (double) e.get("intercom_id"));
                            keys.add(key);
                        }
                        ViewList();
                    } else
                        tv.setText("В данный момент информации нет");
                }
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    tv.setText("Произошла ошибка");
                }
            });

        }

        keysList.setOnItemClickListener((parent, v, position, id) -> {
            String key = adapter.getItem(position);
            //String type;
            //long code = Long.parseLong(key.replaceAll("[^0,1]", ""));
            long code = Long.parseLong(key, 16);
            //type = key.replaceAll("[0,1]|\\s","");
            //boolean typeB = type.equals("iButton");

            for (KeyEntity e : keys) {
                if (e.getCode() == code) {
                    Intent i = new Intent(KeyActivity.this, DetailKeyActivity.class);
                    i.putExtra("key", e);
                    i.putExtra("favor", false);
                    startActivity(i);
                    break;
                }
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KeyActivity.this, AddKeyActivity.class);
                i.putExtra("id_porch", id_porch);
                i.putExtra("id_intercom", id_intercom);
                startActivity(i);
            }
        });
    }
}


