package com.example.smtrick.electionappuser.AppSingleton;

import android.content.Context;
import android.os.AsyncTask;

import com.example.smtrick.electionappuser.Utils.ExceptionUtil;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.smtrick.electionappuser.Constants.Constants.CAT_BIRTHDAY;
import static com.example.smtrick.electionappuser.Constants.Constants.CAT_BUSINESS;
import static com.example.smtrick.electionappuser.Constants.Constants.CAT_EDUCATIONAL;
import static com.example.smtrick.electionappuser.Constants.Constants.CAT_FESTIVAL;
import static com.example.smtrick.electionappuser.Constants.Constants.CAT_POLYTICAL;
import static com.example.smtrick.electionappuser.Constants.Constants.CAT_SOCIAL;


public class AppSingleton {

    private static Context context;
    private static AppSingleton appSingleton;

    public AppSingleton(Context appContext) {
        initFirebasePersistence();
    }

    private void initFirebasePersistence() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                    FirebaseDatabase.getInstance().goOnline();
                } catch (Exception e) {
                    ExceptionUtil.logException(e);
                }
            }
        });
    }

    public static AppSingleton getInstance(Context appContext) {
        //if there is no instance available... create new one
        context = appContext;
        if (appSingleton == null) {
            appSingleton = new AppSingleton(appContext);
        }
        return appSingleton;
    }

    public String[] getCategories() {
        return new String[]{"Select Category",CAT_SOCIAL, CAT_FESTIVAL, CAT_POLYTICAL, CAT_BIRTHDAY,CAT_BUSINESS,CAT_EDUCATIONAL};
    }
}
