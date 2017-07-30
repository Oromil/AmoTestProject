package com.oromil.amotestproject;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.Cookie;

/**
 * Created by Oromil on 27.07.2017.
 */

public class AmoTestPreferences {

    private final String PREFERENCES = "preferences";
    private final String LOGIN = "login";
    private final String PASSWORD = "password";
    private final String DOMAIN = "domain";

    private static AmoTestPreferences instance;
    private SharedPreferences mPreferences;

    AmoTestPreferences(Context context){
        mPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void createInstance(Context context){
        instance = new AmoTestPreferences(context);
    }

    public static AmoTestPreferences getInstance(){
        return instance;
    }

    public void saveLogin(String login){
        mPreferences.edit().putString(LOGIN, login).apply();
    }

    public void savePassword(String password){
        mPreferences.edit().putString(PASSWORD, password).apply();
    }


    public String getLogin(){
        return mPreferences.getString(LOGIN, null);
    }

    public String getPassword(){
        return mPreferences.getString(PASSWORD, null);
    }

    public void resetPreferences(){
        mPreferences.edit().clear().apply();
    }
}
