package com.example.domofonmasterapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.domofonmasterapp.POJO.PorchEntity;
import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.RetrofitServ;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PorchActivity extends AppCompatActivity {
    private final ArrayList<Integer> porchesNumber = new ArrayList<>();
    ListView porchesList;
    ArrayAdapter<Integer> adapter;
    private RetrofitServ RS = new RetrofitServ();
    private ArrayList<PorchEntity> porches = new ArrayList<>();
    private TextView tv;

    protected void ViewList() {
        for (PorchEntity e : porches
        ) {
            porchesNumber.add(e.getNumber());
        }
        adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, porchesNumber);
        porchesList.setAdapter(adapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.porch_activity);

        porchesList = findViewById(R.id.porchesList);
        tv = findViewById(R.id.textPorch);
        long id_house = getIntent().getLongExtra("id_house", 1);
        Call<Object> call = RS.req.GetPorches(id_house);

        synchronized (porchesNumber) {
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (!response.body().toString().equals("[]")) {
                        ArrayList<LinkedTreeMap> porchesEntityMap = RS.gson.fromJson(response.body().toString(), ArrayList.class);
                        for (LinkedTreeMap e : porchesEntityMap) {
                            PorchEntity porch = new PorchEntity();
                            porch.setId_porch((long) (double) e.get("id_porch"));
                            porch.setNumber((int) (double) e.get("number"));
                            porch.setHouse_id((long) (double) e.get("house_id"));
                            porches.add(porch);
                        }
                        ViewList();
                    }
                    else
                        tv.setText("В данный момент информации нет");
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    tv.setText("Произошла ошибка");
                }
            });

        }

        porchesList.setOnItemClickListener((parent, v, position, id) -> {
            int porchNumber = adapter.getItem(position);
            for (PorchEntity e : porches) {
                if (e.getNumber() == porchNumber) {
                    Intent i = new Intent(PorchActivity.this, KeyActivity.class);
                    long id_porch = e.getId_porch();
                    i.putExtra("id_porch", id_porch);
                    startActivity(i);
                }
            }
        });
    }
}
