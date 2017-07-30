package com.oromil.amotestproject;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.log.AndroidLogger;
import io.realm.log.RealmLog;

/**
 * Created by Oromil on 26.07.2017.
 */

public class AmoTestApplication extends Application {



    private static AmoTestApplication ourInstance;

    public static Context getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;

        AmoTestPreferences.createInstance(ourInstance);

        createRealm();
    }

    private void createRealm(){
        Realm.init(getApplicationContext());
        RealmLog.add(new AndroidLogger(Log.WARN));
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("AmoTestDb.realm")
                .migration((realm, oldVersion, newVersion) -> realm.deleteAll())
                .build();
        Realm.compactRealm(configuration);
        Realm.removeDefaultConfiguration();
        Realm.setDefaultConfiguration(configuration);
    }
}
