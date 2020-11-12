package com.example.smtrick.electionappuser.Constants;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by Belal on 2/23/2017.
 */

public class Constants {

    public static final String STORAGE_PATH_UPLOADS = "NewImage/";
    public static final String DATABASE_PATH_UPLOADS = "NewImage";
    public static final String DATABASE_PATH_PATIENTS = "Patients";
    public static final String CHANNEL_ID = "samar app";
    public static final String CHANNEL_NAME = "samr app";
    public static final String CHANNEL_DESC = "samar app notification";

    public static final String GLOBAL_DATE_FORMATE = "dd MMM yyyy hh:mm a";
    public static final String CALANDER_DATE_FORMATE = "dd/MM/yy";
    public static final String LEED_DATE_FORMATE = "dd MMM, yyyy";
    public static final String DAY_DATE_FORMATE = "EEEE";
    public static final String TIME_DATE_FORMATE = "hh:mm a";
    public static final String BIRTH_DATE_FORMATE = "dd/MM";
    public static final int REQUEST_CODE = 101;
    public static final int RESULT_CODE = 201;

    public static final Calendar cal = Calendar.getInstance();
    public static final int DAY = cal.get(Calendar.DAY_OF_MONTH);
    public static final int MONTH = cal.get(Calendar.MONTH);
    public static final int YEAR = cal.get(Calendar.YEAR);

    public static String TWO_DIGIT_LIMIT = "%02d";
    public static String FOUR_DIGIT_LIMIT = "%04d";

    public static final String SUCCESS = "Success";
    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    public static final DatabaseReference LEEDS_TABLE_REF = DATABASE.getReference("leeds");

    public static final DatabaseReference PATIENTS_TABLE_REF = DATABASE.getReference("Patients");
    public static final DatabaseReference USER_TABLE_REF = DATABASE.getReference("users");

    public static final String ACTIVE_USER = "EEEE";
    public static final String DEACTIVE_USER = "REQUEST";


    public static final String AGENT_PREFIX = "MEM- ";
    public static final String RELATION_PREFIX = "REL- ";
    public static final DatabaseReference MEMBERS_TABLE_REF = DATABASE.getReference("Members");
    public static final DatabaseReference RELATIVES_TABLE_REF = DATABASE.getReference("Relations");
    public static final DatabaseReference POST_TABLE_REF = DATABASE.getReference("Posts");

    public static final String CAT_FESTIVAL = "Festival";
    public static final String CAT_POLYTICAL = "Polytical";
    public static final String CAT_BIRTHDAY = "Birthday";
    public static final String CAT_BUSINESS = "Business";
    public static final String CAT_EDUCATIONAL = "Educational";
    public static final String CAT_SOCIAL = "Social";
    public static final String CAT_AGRICULTURE = "Agricultural";

}
