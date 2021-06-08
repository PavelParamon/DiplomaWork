package com.example.domofonmasterapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.domofonmasterapp.POJO.CityEntity;
import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.RetrofitServ;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityActivity extends AppCompatActivity {

    private RetrofitServ RS = new RetrofitServ();
    private ArrayList<CityEntity> cities = new ArrayList<>();
    private final ArrayList<String> citiesName = new ArrayList<>();
    ListView citiesList;
    ArrayAdapter<String> adapter;

    protected void ViewList() {
        for (CityEntity e : cities
        ) {
            citiesName.add(e.getName_city());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, citiesName);
        citiesList.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        citiesList = findViewById(R.id.citiesList);
        Call<Object> call = RS.req.GetCities();


        synchronized (citiesName) {
            call.enqueue(new Callback<Object>() {
                @SuppressLint("Assert")
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    assert response.body() != null;
                    ArrayList<LinkedTreeMap> cityEntityMap = RS.gson.fromJson(response.body().toString(), ArrayList.class);

                    for (LinkedTreeMap e : cityEntityMap) {
                        CityEntity city = new CityEntity();
                        city.setId_city((long) (double) e.get("id_city"));
                        city.setName_city(Objects.requireNonNull(e.get("name_city")).toString());
                        cities.add(city);
                    }

                    ViewList();
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    System.out.println("Err");
                }
            });
        }

        citiesList.setOnItemClickListener((parent, v, position, id) -> {
            String city = adapter.getItem(position);
            for (CityEntity e: cities) {
                if(e.getName_city().equals(city)){
                    Intent i = new Intent(CityActivity.this, StreetActivity.class);
                    long id_city = e.getId_city();
                    i.putExtra("id_city", id_city);
                    startActivity(i);
                }
            }
        });

    }
}
