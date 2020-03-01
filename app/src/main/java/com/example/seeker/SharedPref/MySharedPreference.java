package com.example.seeker.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MySharedPreference {

    private static SharedPreferences prf;

    private MySharedPreference() {
    }//End constructor

    public static SharedPreferences getInstance(Context context) {
        if (prf == null) {
            prf = context.getSharedPreferences(Constants.Keys.USER_DETAILS, MODE_PRIVATE);
        }//End if

        return prf;
    }//End getInstance()


    /**
     * Clear Data
     */
    public static void clearData(Context context) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.clear();
        editor.commit();
    }//End clearData()

    public static void clearValue(Context context, String key) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.remove(key);
        editor.commit();

    }//End clearValue()


    /**
     * Put Data
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(key, value);
        editor.commit();
    }//End putString()

    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }//End putInt()

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }//End putBoolean()

    public static void putFloat(Context context, String key, float value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putFloat(key, value);
        editor.commit();
    }//End putFloat()

    public static void putLong(Context context, String key, long value){
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }//End putLong()


    /**
     * Get Data
     */
    public static String getString(Context context, String key, String valueDefault) {
        return getInstance(context).getString(key, valueDefault);
    }//End getString()

    public static int getInt(Context context, String key, int valueDefault) {
        return getInstance(context).getInt(key, valueDefault);
    }//End getInt()

    public static boolean getBoolean(Context context, String key, boolean valueDefault) {
        return getInstance(context).getBoolean(key, valueDefault);
    }//End getBoolean()

    public static float getFloat(Context context, String key, float valueDefault) {
        return getInstance(context).getFloat(key, valueDefault);
    }//End getFloat()

    public static long getLong(Context context, String key, long valueDefault){
        return getInstance(context).getLong(key, valueDefault);
    }//End getLong()

}//End class