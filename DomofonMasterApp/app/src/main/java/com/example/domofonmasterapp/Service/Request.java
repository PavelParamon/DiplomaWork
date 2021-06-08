package com.example.domofonmasterapp.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Request {
    @GET("city")
    Call<Object> GetCities();

    @GET("intercom")
    Call<Object> GetIntercoms();

    @GET("streets")
    Call<Object> GetStreets(@Query("id_city") long id_city);

    @GET("houses")
    Call<Object> GetHouses(@Query("id_street") long id_street);

    @GET("porches")
    Call<Object> GetPorches(@Query("id_house") long id_house);

    @GET("keys")
    Call<Object> GetKeysViaPorch(@Query("id_porch") long id_porch);

    @GET("keys")
    Call<Object> GetKeysViaIntercom(@Query("id_intercom") long id_intercom);

    @POST("keys")
    Call<Object> AddKeysViaPorch(@Query("id_porch") long id_porch, @Query("code") long code);

    @POST("keys")
    Call<Object> AddKeysViaIntercom(@Query("id_intercom") long id_intercom, @Query("code") long code);

}
