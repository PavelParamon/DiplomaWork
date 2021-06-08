package com.example.domofonmasterapp.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServ {
    private final String server = "http://192.168.0.104:8080/DomofonMaster_exploded_war/";
    private OkHttpClient httpClient = new OkHttpClient.Builder().build();
    public Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(server)
            .client(httpClient).build();
    public Request req = retrofit.create(Request.class);
}
