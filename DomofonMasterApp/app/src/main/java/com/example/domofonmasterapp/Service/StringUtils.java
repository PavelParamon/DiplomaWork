package com.example.domofonmasterapp.Service;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class StringUtils {
    public static String removeFirstZero(String str){
        int i = 0;
        while (str.charAt(i) == '0' && i < str.length() - 1)
            i++;
        return str.substring(i);
    }

    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
