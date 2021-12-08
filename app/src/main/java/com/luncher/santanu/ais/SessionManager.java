package com.luncher.santanu.ais;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_TOKEN = "firebase_token";
    public static final String KEY_CC = "country_code";

    // Email address (make variable public to access from outside)
    public static final String KEY_PHONE = "phone";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String phone){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        //editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_PHONE, phone);

        // commit changes
        editor.commit();
    }

    public void addToken(String token){

        // Storing name in pref
        editor.putString(KEY_TOKEN, token);

        // commit changes
        editor.commit();
    }

    public void addCountryCode(String code){

            // Storing name in pref
            editor.putString(KEY_CC, code);

            // commit changes
            editor.commit();
        }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user phone no
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_CC, pref.getString(KEY_CC, null));
        // return user
        return user;
    }

}

