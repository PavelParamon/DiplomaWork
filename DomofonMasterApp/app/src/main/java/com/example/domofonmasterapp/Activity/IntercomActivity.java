package com.example.domofonmasterapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.domofonmasterapp.POJO.CityEntity;
import com.example.domofonmasterapp.POJO.IntercomEntity;
import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.RetrofitServ;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntercomActivity extends AppCompatActivity {
    private RetrofitServ RS = new RetrofitServ();
    private ArrayList<IntercomEntity> intercoms = new ArrayList<>();
    private final ArrayList<String> intercomsName = new ArrayList<>();
    ListView intercomsList;
    ArrayAdapter<String> adapter;

    protected void ViewList() {
        for (IntercomEntity e : intercoms
        ) {
            intercomsName.add(e.getManufacturer());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, intercomsName);
        intercomsList.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercom);
        intercomsList = findViewById(R.id.intercomsList);
        Call<Object> call = RS.req.GetIntercoms();


        synchronized (intercomsName) {
            call.enqueue(new Callback<Object>() {
                @SuppressLint("Assert")
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    assert response.body() != null;
                    ArrayList<LinkedTreeMap> intercomsEntityMap = RS.gson.fromJson(response.body().toString(), ArrayList.class);

                    for (LinkedTreeMap e : intercomsEntityMap) {
                        IntercomEntity intercom = new IntercomEntity();
                        intercom.setId_intercom((long) (double) e.get("id_intercom"));
                        intercom.setManufacturer(Objects.requireNonNull(e.get("manufacturer")).toString());
                        intercoms.add(intercom);
                    }

                    ViewList();
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    System.out.println("Err");
                }
            });
        }

        intercomsList.setOnItemClickListener((parent, v, position, id) -> {
            String intercom = adapter.getItem(position);
            for (IntercomEntity e: intercoms) {
                if(e.getManufacturer().equals(intercom)){
                    Intent i = new Intent(IntercomActivity.this, KeyActivity.class);
                    long id_intercom = e.getId_intercom();
                    i.putExtra("id_intercom", id_intercom);
                    startActivity(i);
                }
            }
        });
    }

}
