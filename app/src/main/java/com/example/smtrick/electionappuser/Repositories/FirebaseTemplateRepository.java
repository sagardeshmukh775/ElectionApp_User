package com.example.smtrick.electionappuser.Repositories;

import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Constants.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public abstract class FirebaseTemplateRepository {

    /**
     * Insert data on FireBase
     *
     * @param databaseReference
     * @param model
     * @param callback
     * @throws Exception
     */
    protected final void fireBaseCreate(final DatabaseReference databaseReference, final Object model, final CallBack callback) {
        databaseReference.keepSynced(true);
        databaseReference.setValue(model, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null) {
                    // null parameter is not passed in call so we use string for callback
                    callback.onSuccess(Constants.SUCCESS);
                } else {
                    callback.onError(databaseError);
                }
            }


        });
    }

    /**
     * Getting data from FireBase
     *
     * @param query
     * @param callback
     * @throws Exception
     */
    protected final void fireBaseReadData(final Query query, final CallBack callback) {
        query.keepSynced(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }


    protected final void addChatChild(final Query query, final CallBack callBack) {
        query.keepSynced(true);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                callBack.onSuccess(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError(databaseError);
            }
        });
    }

    /**
     * Update data to FireBase
     *
     * @param databaseReference
     * @param map
     * @param callback
     */
    protected final void fireBaseUpdateChildren(final DatabaseReference databaseReference, final Map map, final CallBack callback) {
        databaseReference.keepSynced(true);
        databaseReference.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess(databaseError);
                } else {
                    callback.onError(databaseError);
                }
            }
        });
    }

    /**
     * Delete data from firebase
     *
     * @param databaseReference
     * @param callback
     * @throws Exception
     */
    protected final void fireBaseDelete(final DatabaseReference databaseReference, final CallBack callback) {
        databaseReference.keepSynced(true);
        databaseReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(databaseError);
                }
            }
        });
    }

    /**
     * Notify data change in FireBase
     *
     * @param query
     * @param callback
     * @throws Exception
     */
    protected final void fireBaseNotifyChange(final Query query, final CallBack callback) {
        query.keepSynced(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    /**
     * Insert offline data on FireBase
     *
     * @param databaseReference
     * @param model
     * @throws Exception
     */
    protected final void fireBaseOfflineCreate(final DatabaseReference databaseReference, final Object model) {
        try {
            databaseReference.keepSynced(true);
            databaseReference.setValue(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * update offline data on FireBase
     *
     * @param databaseReference
     *  @param pushKey
     * @param model
     * @throws Exception
     */
    protected final void fireBaseOfflineUpdate(final DatabaseReference databaseReference, final String pushKey, final Object model) {
        try {
            databaseReference.keepSynced(true);
            Map<String, Object> stringObjectMap = new HashMap();
            stringObjectMap.put(pushKey, model);
            databaseReference.updateChildren(stringObjectMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * check connectivity with FireBase
     *
     * @param databaseReference
     * @param callback
     * @throws Exception
     */
    protected final void fireBaseCheckConnection(final DatabaseReference databaseReference, final CallBack callback) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
               callback.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onError(error);
            }
        });
    }

}
