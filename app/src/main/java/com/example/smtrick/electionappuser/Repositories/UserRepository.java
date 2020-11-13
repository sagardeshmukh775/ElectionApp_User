package com.example.smtrick.electionappuser.Repositories;



import com.example.smtrick.electionappuser.Callback.CallBack;

import java.util.Map;

public interface UserRepository {

    void readLoggedInUser(final CallBack callback);

    void readUser(final String userId, final CallBack callback);


    void readUserByUserId(final String regId, final CallBack callBack);


}