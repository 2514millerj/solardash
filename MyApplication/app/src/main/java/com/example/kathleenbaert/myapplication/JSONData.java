package com.example.kathleenbaert.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kathleenbaert on 11/29/17.
 */

public class JSONData {
    public static JSONData dataInstance = new JSONData();
    static String TAG = "JSONData";


    public static int fArrayCounter = 0;
    public static int cArrayCounter = 0;
    public static int voltArrayCounter = 0;
    public static int ampArrayCounter = 0;

    public static double tempF;
    public static double tempC;
    public static double [] tempFArray = new double [50];
    public static double [] tempCArray = new double [50];

    public static double voltage;
    public static double [] voltageArray = new double [50];

    public static double amps;
    public static double [] ampsArray = new double [50];

    public static void setTempF(double temp){
        tempF = temp;
    }
    public static double getTempF(){
        return tempF;
    }
    public static void setTempC(double temp){
        tempC = temp;
    }
    public static double getTempC(){
        return tempC;
    }
    public static void addToTempFArray(Double temp){
        tempFArray[fArrayCounter%50] = temp;
        fArrayCounter++;
        if(fArrayCounter > 50){
            fArrayCounter = 0;
        }
    }
    public static double [] getTempFArray(){
        return tempFArray;
    }
    public static void addToTempCArray(Double temp){
        tempCArray[cArrayCounter%50] = temp;
        cArrayCounter++;
        if(cArrayCounter > 50){
            cArrayCounter = 0;
        }
    }
    public static double [] getTempCArray(){
        return tempCArray;
    }
    public static void setVoltage(double volt){
        Log.e(TAG, "setting volt " + volt);
        voltage = volt;
    }
    public static double getVoltage(){
        return voltage;
    }
    public static void addToVoltArray(Double volt){
        voltageArray[voltArrayCounter%50] = volt;
        voltArrayCounter++;
        if(voltArrayCounter > 50){
            voltArrayCounter = 0;
        }
    }
    public static double [] getVoltageArray(){
        return voltageArray;
    }
    public static void setAmps(double amp){
        Log.e(TAG, "setting amp " + amp);

        amps = amp;
    }
    public static double getAmps(){
        return amps;
    }
    public static void addToAmpArray(Double amp){
        ampsArray[ampArrayCounter%50] = amp;
        ampArrayCounter++;
        if(ampArrayCounter > 50){
            ampArrayCounter = 0;
        }
    }
    public static double [] getAmpsArray(){
        return ampsArray;
    }
}
