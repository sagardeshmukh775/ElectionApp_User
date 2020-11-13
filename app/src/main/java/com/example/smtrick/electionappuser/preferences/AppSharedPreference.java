package com.example.smtrick.electionappuser.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.smtrick.electionappuser.Models.Users;

public class AppSharedPreference {
    private SharedPreferences sharedPref;
    private Context context;
    private SharedPreferences.Editor editor;
    private String USERNAME = "USERNAME";
    private String USERID = "USERID";
    private String EMAIL_ID = "EMAIL_ID";
    private String LANGUAGE = "LANGUAGE";
    private String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";




    public AppSharedPreference(Context context) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void addUserDetails(Users user) {
        editor = sharedPref.edit();
        if (user != null) {
            if (user.getName() != null)
                editor.putString(USERNAME, (user.getName()));
            if (user.getEmail() != null)
                editor.putString(EMAIL_ID, (user.getEmail()));
            if (user.getUserid() != null)
                editor.putString(USERID, (user.getUserid()));
            if (user.getLanguage() != null)
                editor.putString(LANGUAGE, (user.getLanguage()));

        }
        editor.apply();
    }

    public void createUserLoginSession() {
        editor = sharedPref.edit();
        editor.putBoolean(IS_USER_LOGGED_IN, true);
        editor.apply();
    }

    //User
    public String getUserName() {
        return (sharedPref.getString(USERNAME, ""));
    }

    public String getuserId() {
        return (sharedPref.getString(USERID, ""));
    }

    public boolean getUserLoginStatus() {
        return sharedPref.getBoolean(IS_USER_LOGGED_IN, (false));
    }

    public String getEmaiId() {
        return (sharedPref.getString(EMAIL_ID, ""));
    }

    public String getLanguage() {
        return (sharedPref.getString(LANGUAGE, ""));
    }






    public void setUserProfileImages(String imagePath) {
        editor = sharedPref.edit();
        if (imagePath != null) {
//            editor.putString(PROFILE_SMALL_IMAGE, imagePath);
//            editor.putString(PROFILE_LARGE_IMAGE, imagePath);
        }
        editor.apply();
    }
    public void setUserCoverImages(String imagePath) {
        editor = sharedPref.edit();
        if (imagePath != null) {
//            editor.putString(COVER_LARGE_IMAGE, imagePath);

        }
        editor.apply();
    }



    public void clear() {
        editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }
}
