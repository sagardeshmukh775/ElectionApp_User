package com.example.smtrick.electionappuser.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Users implements Serializable {

    public String name, email, userid, language, userId, regId, password;
    ;

    public Users() {

    }

    Users(String name, String email, String userid, String language, String userId, String regId, String password) {
        this.name = name;
        this.email = email;
        this.userid = userid;
        this.language = language;
        this.userId = userId;
        this.regId = regId;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("name", getName());
        leedMap.put("email", getEmail());
        leedMap.put("userid", getUserid());
        leedMap.put("language", getLanguage());
        leedMap.put("userId", getUserid());
        leedMap.put("regId", getRegId());
        leedMap.put("password", getPassword());

        return leedMap;

    }
}
