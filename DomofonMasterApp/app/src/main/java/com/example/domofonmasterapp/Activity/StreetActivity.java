package com.example.domofonmasterapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.domofonmasterapp.POJO.StreetEntity;
import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.RetrofitServ;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StreetActivity extends AppCompatActivity {
    private final ArrayList<String> streetsName = new ArrayList<>();
    ListView streetsList;
    ArrayAdapter<String> adapter;
    private RetrofitServ RS = new RetrofitServ();
    private ArrayList<StreetEntity> streets = new ArrayList<>();
    private TextView tv;

    protected void ViewList() {
        for (StreetEntity e : streets
        ) {
            streetsName.add(e.getName_street());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, streetsName);
        streetsList.setAdapter(adapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.street_activity);
        streetsList = findViewById(R.id.streetsList);
        tv = findViewById(R.id.textStreet);
        long id_city = getIntent().getLongExtra("id_city", 2);
        Call<Object> call = RS.req.GetStreets(id_city);

        synchronized (streetsName) {
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    assert response.body() != null;
                    ArrayList<LinkedTreeMap> streetsEntityMap = RS.gson.fromJson(response.body().toString(), ArrayList.class);

                    for (LinkedTreeMap e : streetsEntityMap) {
                        StreetEntity street = new StreetEntity();
                        street.setCity_id((long) (double) e.get("city_id"));
                        street.setName_street(Objects.requireNonNull(e.get("name_street")).toString());
                        street.setId_street((long) (double) e.get("id_street"));
                        streets.add(street);
                    }
                    ViewList();
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    tv.setText("Произошла ошибка");
                }
            });

        }
        streetsList.setOnItemClickListener((parent, v, position, id) -> {
            String street = adapter.getItem(position);
            for (StreetEntity e: streets) {
                if(e.getName_street().equals(street)){
                    Intent i = new Intent(StreetActivity.this, HouseActivity.class);
                    long id_street = e.getId_street();
                    i.putExtra("id_street", id_street);
                    startActivity(i);
                }
            }
        });

    }


}
