package com.example.domofonmasterapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.ArduinoServ;
import com.example.domofonmasterapp.Service.RetrofitServ;

import androidx.appcompat.app.AppCompatActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddKeyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_key);
        Button buttonReadKey = findViewById(R.id.buttonReadKey);
        RetrofitServ RS = new RetrofitServ();
        long id_porch = getIntent().getLongExtra("id_porch", -1);
        long id_intercom = getIntent().getLongExtra("id_intercom", -1);
        Intent i = new Intent(AddKeyActivity.this, MainActivity.class);

        buttonReadKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeStr = null;
                ArduinoServ.clearReadMsg();
                while(ArduinoServ.getReadMsg().isEmpty()){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(!ArduinoServ.getReadMsg().isEmpty()){
                        codeStr = ArduinoServ.getReadMsg();
                    }
                }
                String message = "Arduino read code ";
                Toast.makeText(v.getContext(), message + codeStr, Toast.LENGTH_SHORT).show();
                long code = Long.parseLong(codeStr, 16);
                Call<Object> call;
                //boolean type = true;
                if(id_porch != -1) {
                    call = RS.req.AddKeysViaPorch(id_porch, code);
                }
                else {
                    call = RS.req.AddKeysViaIntercom(id_intercom, code);
                }

                synchronized (call) {
                    call.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (response.body() != null) {
                                boolean flag = RS.gson.fromJson(response.body().toString(), Boolean.class);
                                if (flag) {
                                    String message = "Код добавлен в базу данных";
                                    Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(v.getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(v.getContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                startActivity(i);
            }
        });
    }
}
