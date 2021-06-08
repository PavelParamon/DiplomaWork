package com.example.domofonmasterapp.Service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ArduinoServ {
    static public final String ACTION_USB_PERMISSION = "com.hariharan.arduinousb.USB_PERMISSION";
    static private UsbManager usbManager;
    static private UsbSerialDevice serialPort;
    static private UsbDevice device;
    static private UsbDeviceConnection connection;
    static private String readMsg = "";

    public static UsbSerialDevice getSerialPort() {
        return serialPort;
    }
    static public void setUsbManager(UsbManager usbManager) {
        ArduinoServ.usbManager = usbManager;
    }
    static public String getReadMsg() {
        return readMsg;
    }

    static public void clearReadMsg(){
        readMsg = "";
    }

    static UsbSerialInterface.UsbReadCallback mCallback = arg0 -> {
        String partMsg = new String(arg0, StandardCharsets.UTF_8);
        readMsg += partMsg;

    };

    static public final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), ACTION_USB_PERMISSION)) {
                boolean granted = Objects.requireNonNull(intent.getExtras()).getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) {
                    connection = usbManager.openDevice(device);
                    serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
                    if (serialPort != null) {
                        if (serialPort.open()) {
                            serialPort.setBaudRate(115200);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialPort.read(mCallback);

                        } else {
                            Log.d("SERIAL", "PORT NOT OPEN");
                        }
                    } else {
                        Log.d("SERIAL", "PORT IS NULL");
                    }
                } else {
                    Log.d("SERIAL", "PERM NOT GRANTED");
                }
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                Toast.makeText(context, "Arduino is attached", Toast.LENGTH_SHORT).show();
                start(context);
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                Toast.makeText(context, "Arduino is detached", Toast.LENGTH_SHORT).show();
            }
        }
    };

    static private void start(Context context) {
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                if (deviceVID == 0x1A86)
                {
                    PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    usbManager.requestPermission(device, pi);
                    keep = false;
                } else {
                    connection = null;
                    device = null;
                }
                if (!keep)
                    break;
            }
        }
    }

}
