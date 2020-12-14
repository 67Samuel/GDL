package com.example.gdl.createeventpg;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class CreateEventSelectMembersSharedPref {

    private static SharedPreferences mSharedPref;
    public static final String SELECTED_MEMBERS_ID_SET_KEY = "Selected Members Ids Set Key";

    private CreateEventSelectMembersSharedPref() {

    }

    public static void createSharedPref(Context context) {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static void write(String key, Set<String> value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putStringSet(key, value);
        prefsEditor.apply();
    }

    public static Set<String> readStringSet(String key, Set<String> defValue) {
        return mSharedPref.getStringSet(key, defValue);
    }

    public static void remove(String key) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.remove(key).apply();
    }
}
