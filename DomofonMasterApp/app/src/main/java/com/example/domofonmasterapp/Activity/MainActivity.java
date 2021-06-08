package com.example.domofonmasterapp.Activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.domofonmasterapp.R;
import com.example.domofonmasterapp.Service.ArduinoServ;
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnGetIntercom, btnFavouritesKey, btnGetCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetIntercom = findViewById(R.id.btnGetIntercom);
        btnFavouritesKey = findViewById(R.id.btnFavouritesKey);
        btnGetCity = findViewById(R.id.btnGetCity);
        UsbManager usbManager = (UsbManager) getSystemService(this.USB_SERVICE);
        ArduinoServ.setUsbManager(usbManager);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ArduinoServ.ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(ArduinoServ.broadcastReceiver, filter);


        btnGetIntercom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, IntercomActivity.class);
                startActivity(i);
            }
        });

        btnGetCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CityActivity.class);
                startActivity(i);
            }
        });

        btnFavouritesKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(i);
            }
        });
    }
}

