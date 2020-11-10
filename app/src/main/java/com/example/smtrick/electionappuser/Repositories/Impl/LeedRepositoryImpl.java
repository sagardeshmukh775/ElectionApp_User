package com.example.smtrick.electionappuser.Repositories.Impl;

import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Constants.Constants;
import com.example.smtrick.electionappuser.Repositories.FirebaseTemplateRepository;
import com.example.smtrick.electionappuser.Repositories.LeedRepository;
import com.example.smtrick.electionappuser.Models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Map;

public class LeedRepositoryImpl extends FirebaseTemplateRepository implements LeedRepository {
//    @Override
//    public void readAllLeeds(final CallBack callBack) {
//        final Query query = Constants.LEEDS_TABLE_REF;
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            LeedsModel leedsModel = suggestionSnapshot.getValue(LeedsModel.class);
//                            leedsModelArrayList.add(leedsModel);
//                        }
//                        callBack.onSuccess(leedsModelArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//    @Override
//    public void readLeedsByUserIdReport(final Context context, String userId, final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF.orderByChild("createdBy").equalTo(userId);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
//                        int colorCount = 0;
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            LeedsModel leedsModel = suggestionSnapshot.getValue(LeedsModel.class);
//                            if (colorCount % 5 == 0)
//                                colorCount = 0;
//                           // setColor(context, leedsModel, colorCount);
//                            colorCount++;
//                            leedsModelArrayList.add(leedsModel);
//                        }
//                        callBack.onSuccess(leedsModelArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//
//    @Override
//    public void createLeed(LeedsModel leedsModel, final CallBack callBack) {
//        DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedsModel.getLeedId());
//        fireBaseCreate(databaseReference, leedsModel, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                callBack.onSuccess(object);
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//
//
//    @Override
//    public void deleteLeed(String leedId, CallBack callback) {
//        DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedId);
//        fireBaseDelete(databaseReference, callback);
//    }
//
//    @Override
//    public void updateLeed(String leedId, Map leedMap, final CallBack callBack) {
//        final DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedId);
//        fireBaseUpdateChildren(databaseReference, leedMap, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                callBack.onSuccess(object);
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//    public void readLeedsByStatus(String status, final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF.orderByChild("status").equalTo(status);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<Invoice> leedsModelArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            Invoice leedsModel = suggestionSnapshot.getValue(Invoice.class);
//                            leedsModelArrayList.add(leedsModel);
//                        }
//                        callBack.onSuccess(leedsModelArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//    @Override
//    public void readLeedByLeedId(String leedId, final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF.child(leedId);
//        fireBaseReadData(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        DataSnapshot child = dataSnapshot.getChildren().iterator().next();
//                        LeedsModel leedsModel = child.getValue(LeedsModel.class);
//                        callBack.onSuccess(leedsModel);
//                    } else
//                        callBack.onSuccess(null);
//                } else
//                    callBack.onSuccess(null);
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//    @Override
//    public void updateLeedDocuments(String leedId, Map leedMap, CallBack callback) {
//
//    }
//
//    @Override
//    public void updateLeedHistory(String leedId, Map leedMap, CallBack callback) {
//
//    }

    @Override
    public void deleteLeed(String leedId, CallBack callback) {
        DatabaseReference databaseReference = Constants.LEEDS_TABLE_REF.child(leedId);
        fireBaseDelete(databaseReference, callback);
    }

    @Override
    public void updateLeed(String leedId, Map leedsMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constants.MEMBERS_TABLE_REF.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedsMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                callBack.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void updateRelative(String leedId, Map leedsMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constants.RELATIVES_TABLE_REF.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedsMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                callBack.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void updateUser(String leedId, Map leedsMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constants.USER_TABLE_REF.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedsMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                callBack.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }


    @Override
    public void readRequestUser(String name, final CallBack callBack) {
        final Query query = Constants.USER_TABLE_REF.orderByChild("status").equalTo(name);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<Users> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            Users leedsModel = suggestionSnapshot.getValue(Users.class);

                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }


    @Override
    public void readActiveUser(String name, final CallBack callBack) {
        final Query query = Constants.USER_TABLE_REF.orderByChild("status").equalTo(name);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<Users> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            Users leedsModel = suggestionSnapshot.getValue(Users.class);

                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

}
