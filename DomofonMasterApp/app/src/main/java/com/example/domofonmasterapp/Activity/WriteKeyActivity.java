package com.example.domofonmasterapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.domofonmasterapp.POJO.KeyEntity;
import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.ArduinoServ;
import com.example.domofonmasterapp.Service.RetrofitServ;

public class WriteKeyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_key);
        Button buttonWriteKey = findViewById(R.id.buttonWriteKey);
        boolean favor = getIntent().getBooleanExtra("favor", false);
        KeyEntity KE = (KeyEntity) getIntent().getSerializableExtra("key");
        Intent i = new Intent(WriteKeyActivity.this, DetailKeyActivity.class);
        i.putExtra("key", KE);
        i.putExtra("favor", favor);

        buttonWriteKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                if(!ArduinoServ.getReadMsg().isEmpty())
                    ArduinoServ.clearReadMsg();
                while(!ArduinoServ.getReadMsg().equals("true")){
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(ArduinoServ.getReadMsg().contains("true")){
                        message = "Ключ успешно записался";
                        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else {
                        message = "Запись не удалась\nПопробуйте снова";
                        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }
                startActivity(i);
            }
        });
    }
}
