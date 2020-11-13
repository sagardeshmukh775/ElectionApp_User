package com.example.smtrick.electionappuser.Repositories.Impl;

import android.app.Activity;


import androidx.annotation.NonNull;

import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Constants.Constants;
import com.example.smtrick.electionappuser.Models.Users;
import com.example.smtrick.electionappuser.Repositories.FirebaseTemplateRepository;
import com.example.smtrick.electionappuser.Repositories.UserRepository;
import com.example.smtrick.electionappuser.Utils.ExceptionUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;


public class UserRepositoryImpl extends FirebaseTemplateRepository implements UserRepository {
    private Activity _activity;

    public UserRepositoryImpl(final Activity activity) {
        _activity = activity;
    }

    public UserRepositoryImpl() {

    }
    /********************************************** Firebase Call ***************************************************/
    /**
     * @param mobileNumber
     * @param callBack
     */


    /**
     * @param userId
     * @param callback
     */
    @Override
    public void readUser(final String userId, final CallBack callback) {
        final Query query = Constants.USER_TABLE_REF.orderByChild("userid").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null && dataSnapshot.hasChildren()) {
                    try {
                        final DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                        callback.onSuccess(firstChild.getValue(Users.class));
                    } catch (Exception e) {
                        callback.onError(e);
                        ExceptionUtil.logException(e);
                    }
                } else
                    callback.onError(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    @Override
    public void readLoggedInUser(final CallBack callback) {
        final FirebaseUser firebaseUser = Constants.AUTH.getCurrentUser();
        if (firebaseUser == null) {
            return;
        }
        final String userId = firebaseUser.getUid();
        readUser(userId, callback);
    }

    @Override
    public void readUserByUserId(String userId, final CallBack callBack) {
        final Query query = Constants.USER_TABLE_REF.child(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            if (dataSnapshot.hasChildren()) {
                                callBack.onSuccess(dataSnapshot.getValue(Users.class));
                            } else {
                                callBack.onError(null);
                            }
                        } catch (Exception e) {
                            ExceptionUtil.logException(e);
                        }
                    } else
                        callBack.onError(null);
                } else
                    callBack.onError(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError(databaseError);
            }
        });
    }



}