package com.Jsu.JFramework;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by su on 16-6-22.
 */
public class Test {

    public static String ptrString(String s){
        System.out.println(s);
        Log.e("static test", "static test");
        return null;
    }

    public static final String test = ptrString("static test");

    public static void main(String[] args){
        int[] arr = new int[]{8,2,1,0,3};
        int[] index = new int[]{2,0,3,2,4,0,1,3,2,3,3};
        String tel = "";
        for(int i: index){
            tel += arr[i];
        }
        System.out.println(tel);

        String sdf = new SimpleDateFormat("EEEE").format(new Date(System.currentTimeMillis()));
    }
}