package com.example.smtrick.electionappuser.Repositories;

import com.example.smtrick.electionappuser.Callback.CallBack;

import java.util.Map;

public interface LeedRepository {


    void deleteLeed(final String leedId, final CallBack callback);

    void updateLeed(final String leedId, final Map leedsMap, CallBack callBack);

    void updateUser(final String userid, final Map leedsMap, CallBack callBack);


    void readRequestUser(final String userId, CallBack callback);

    void readActiveUser(final String userId, CallBack callback);

    void updateRelative(final String leedId, final Map leedsMap, final CallBack callBack);

    void readPostsByCategory(final String category, final CallBack callBack);

    void updatePost(final String ostId, final Map leedsMap, CallBack callBack);

}
