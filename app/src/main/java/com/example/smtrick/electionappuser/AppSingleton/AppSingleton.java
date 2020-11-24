package com.example.smtrick.electionappuser.AppSingleton;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;


import androidx.core.app.NotificationCompat;

import com.example.smtrick.electionappuser.R;
import com.example.smtrick.electionappuser.Utils.ExceptionUtil;
import com.google.firebase.database.FirebaseDatabase;


public class AppSingleton {
    private static AppSingleton appSingleton;
    public int notificationId = 100;
    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    private static Context context;

    //private constructor.
    private AppSingleton(Context context) {
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



    public String[] getBalanceTransferType() {
        return new String[]{"Select Balance Transfer Type", "HL", "LAP"};
    }

    public String[] getEmployeeType() {
        return new String[]{"Select Occupation", "Salaried", "Businessman"};
    }

    public void setNotificationManager() {
        try {
            mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle(context.getString(R.string.file_uploading_progress))
                    .setContentText(context.getString(R.string.notification_message))
                    .setSmallIcon(R.drawable.baseline_cloud_upload_black_24)
                    .setOngoing(true);
            mBuilder.setProgress(0, 0, true);
            // Issues the notification
            mNotifyManager.notify(notificationId, mBuilder.build());
        } catch (Exception e) {
            ExceptionUtil.logException(e);
        }
    }

    public void updateProgress(int uploaded, int total, int percent) {
        try {
            if (mNotifyManager != null && mBuilder != null) {
                if (total == uploaded) {
                    mBuilder.setContentText(context.getString(R.string.file_uploading_complited))
                            // Removes the progress bar
                            .setProgress(0, 0, false)
                            .setContentTitle(context.getString(R.string.file_uploading_complited_message) + " " + uploaded + "/" + total)
                            .setOngoing(false);
                    mNotifyManager.notify(0, mBuilder.build());
                    mNotifyManager.cancel(notificationId);
                    mNotifyManager = null;
                    mBuilder = null;
                } else {
                    mBuilder.setProgress(total, uploaded, false);
                    mBuilder.setContentTitle(context.getString(R.string.file_uploading_progress) + " (" + Integer.toString(percent) + "%)" + " " + uploaded + "/" + total);
                    // Issues the notification
                    mNotifyManager.notify(notificationId, mBuilder.build());
                }
            }
        } catch (Exception e) {
            ExceptionUtil.logException(e);
        }
    }
}