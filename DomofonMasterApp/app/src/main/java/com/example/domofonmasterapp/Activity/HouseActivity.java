package com.example.domofonmasterapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.domofonmasterapp.POJO.HouseEntity;
import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.RetrofitServ;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseActivity extends AppCompatActivity {
    private final ArrayList<String> houseNumber = new ArrayList<>();
    ListView housesList;
    ArrayAdapter<String> adapter;
    private RetrofitServ RS = new RetrofitServ();
    private ArrayList<HouseEntity> houses = new ArrayList<>();
    private TextView tv;

    protected void ViewList() {
        for (HouseEntity e : houses
        ) {
            houseNumber.add(e.getNumber() + e.getNotes());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, houseNumber);
        housesList.setAdapter(adapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_activity);
        housesList = findViewById(R.id.houseList);
        tv = findViewById(R.id.textHouse);
        long id_street = getIntent().getLongExtra("id_street", 1);
        Call<Object> call = RS.req.GetHouses(id_street);

        synchronized (houseNumber) {
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    assert response.body() != null;
                    ArrayList<LinkedTreeMap> housesEntityMap = RS.gson.fromJson(response.body().toString(), ArrayList.class);
                    for (LinkedTreeMap e : housesEntityMap) {
                        HouseEntity house = new HouseEntity();
                        house.setId_house((long) (double) e.get("id_house"));
                        house.setNumber((int) (double) e.get("number"));
                        if (e.containsKey("notes"))
                            house.setNotes(Objects.requireNonNull(e.get("notes")).toString());
                        else house.setNotes("");
                        house.setStreet_id((long) (double) e.get("street_id"));
                        houses.add(house);
                    }
                    ViewList();
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    tv.setText("Произошла ошибка");
                }
            });

        }

        housesList.setOnItemClickListener((parent, v, position, id) -> {
            String house = adapter.getItem(position);
            int houseNumber = Integer.parseInt(house.replaceAll("[^0-9]\\d+|[^0-9]", ""));
            String notes = house.replaceAll("^[0-9]{1,3}", "");
            for (HouseEntity e : houses) {

                if (e.getNumber() == houseNumber && e.getNotes().equals(notes)) {
                    Intent i = new Intent(HouseActivity.this, PorchActivity.class);
                    long id_house = e.getId_house();
                    i.putExtra("id_house", id_house);
                    startActivity(i);
                }
            }


        });
    }
}

