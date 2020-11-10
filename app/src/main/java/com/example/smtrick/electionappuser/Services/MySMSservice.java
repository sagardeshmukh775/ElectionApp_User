package com.example.smtrick.electionappuser.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.smtrick.electionappuser.Models.MemberVO;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MySMSservice extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SMS = "com.geniobits.autosmssender.action.SMS";
    private static final String ACTION_WHATSAPP = "com.geniobits.autosmssender.action.WHATSAPP";

    // TODO: Rename parameters
    private static final String MESSAGE = "com.geniobits.autosmssender.extra.PARAM1";
    private static final String COUNT = "com.geniobits.autosmssender.extra.PARAM2";
    private static final String MOBILE_NUMBER = "com.geniobits.autosmssender.extra.PARAM3";
    private static final String IS_EACH_WORD = "com.geniobits.autosmssender.extra.PARAM4";

    public MySMSservice() {
        super("MySMSservice");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSMS(Context context, String message, String count, List<MemberVO> mobile_numbers) {

        List<String> numbers =new ArrayList<String>();
        for(int i = 0;i<mobile_numbers.size();i++){
            numbers.add(mobile_numbers.get(i).getMembercontact());
        }
        String[] numbersArray = numbers.toArray(new String[0]);

        Intent intent = new Intent(context, MySMSservice.class);
        intent.setAction(ACTION_SMS);
        intent.putExtra(MESSAGE, message);
        intent.putExtra(COUNT, count);
        intent.putExtra(MOBILE_NUMBER,numbersArray);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionWHATSAPP(Context context, String message, String count, List<MemberVO> mobile_numbers, Boolean isEachWord) {

        List<String> numbers =new ArrayList<String>();
        for(int i = 0;i<mobile_numbers.size();i++){
            numbers.add(mobile_numbers.get(i).getMembercontact());
        }
        String[] numbersArray = numbers.toArray(new String[0]);

        Intent intent = new Intent(context, MySMSservice.class);
        intent.setAction(ACTION_WHATSAPP);
        intent.putExtra(MESSAGE, message);
        intent.putExtra(COUNT, count);
        intent.putExtra(MOBILE_NUMBER,numbersArray);
        intent.putExtra(IS_EACH_WORD,isEachWord);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SMS.equals(action)) {
                final String message = intent.getStringExtra(MESSAGE);
                final String count = intent.getStringExtra(COUNT);
                final String[] mobile_number = intent.getStringArrayExtra(MOBILE_NUMBER);
                handleActionSMS(message, count,mobile_number);
            } else if (ACTION_WHATSAPP.equals(action)) {
                final String message = intent.getStringExtra(MESSAGE);
                final String count = intent.getStringExtra(COUNT);
                final String[] mobile_number = intent.getStringArrayExtra(MOBILE_NUMBER);
                final boolean isEachWord = intent.getBooleanExtra(IS_EACH_WORD,false);
                handleActionWHATSAPP(message, count,mobile_number,isEachWord);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSMS(String message, String count, String[] mobile_number) {
        // TODO: Handle action Foo

        try {
            if(mobile_number.length!=0) {
                for(int j=0;j<mobile_number.length;j++) {

                    for (int i = 0; i < Integer.parseInt(count.toString()); i++) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mobile_number[j], null, message, null, null);
                        sendBroadcastMessage("Result:"+ (i+1) + " "+ mobile_number[j]);
                    }

                }
            }
        }catch(Exception e){

        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionWHATSAPP(String message, String count, String[] mobile_number, boolean isEachWord) {

        if(isEachWord){
            try {
                PackageManager packageManager = getApplicationContext().getPackageManager();
                if (mobile_number.length != 0) {
                    for (int j = 0; j < mobile_number.length; j++) {

                        for (int i = 0; i < Integer.parseInt(count.toString()); i++) {
                            String[] words = message.split(" ");
                            String number = mobile_number[j];
                            for(int k=0;k<words.length;k++) {
                                String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(words[k] + "   ", "UTF-8");
                                Intent whatappIntent = new Intent(Intent.ACTION_VIEW);
                                whatappIntent.setPackage("com.whatsapp");
                                whatappIntent.setData(Uri.parse(url));
                                whatappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (whatappIntent.resolveActivity(packageManager) != null) {
                                    getApplicationContext().startActivity(whatappIntent);
                                    Thread.sleep(10000);
                                    sendBroadcastMessage("Result: " + k);
                                } else {
                                    sendBroadcastMessage("Result: WhatsApp Not installed");
                                }
                            }
                        }

                    }
                }
            } catch (Exception e) {
                sendBroadcastMessage("Result: " + e.toString());
            }
        }
        else {
            try {
                PackageManager packageManager = getApplicationContext().getPackageManager();
                if (mobile_number.length != 0) {
                    for (int j = 0; j < mobile_number.length; j++) {

                        for (int i = 0; i < Integer.parseInt(count.toString()); i++) {
                            String number = mobile_number[j];
                            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(message + "   ", "UTF-8");
                            Intent whatappIntent = new Intent(Intent.ACTION_VIEW);
                            whatappIntent.setPackage("com.whatsapp");
                            whatappIntent.setData(Uri.parse(url));
                            whatappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (whatappIntent.resolveActivity(packageManager) != null) {
                                getApplicationContext().startActivity(whatappIntent);
                                Thread.sleep(10000);
                                sendBroadcastMessage("Result: " + number);
                            } else {
                                sendBroadcastMessage("Result: WhatsApp Not installed");
                            }
                        }

                    }
                }
            } catch (Exception e) {
                sendBroadcastMessage("Result: " + e.toString());
            }
        }


    }



    private void sendBroadcastMessage(String message){
        Intent localIntent = new Intent("my.own.broadcast");
        localIntent.putExtra("result",message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}