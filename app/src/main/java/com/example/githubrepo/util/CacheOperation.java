package com.example.githubrepo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.githubrepo.Constants;

public class CacheOperation  {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public CacheOperation(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void PutIntToPreference(String key, int value){
        editor.putInt(key,value);
        editor.apply();
    }
    public int getIntFromPreference(String key, int defaultValue){
        return sharedPreferences.getInt(key,defaultValue);
    }

    public void PutStringToPreference(String key, String value){
        editor.putString(key,value);
        editor.apply();
    }
    public String getStringFromPreference(String key, String defaultValue){
        return sharedPreferences.getString(key,defaultValue);
    }

}
